package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends Fragment {
    private String name, email, otp, id;
    private EditText edtOtp;
    private Button btnVerifyOtp;
    private TextView tvResend;
    private ProgressDialog progressDialog;
    private SharedPreference sharedPreference;
    private String Id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtpFragment newInstance(String param1, String param2) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        name = getArguments().getString("name");
        email = getArguments().getString("email");
        otp = getArguments().getString("Otp");
        id = getArguments().getString("id");
        edtOtp = view.findViewById(R.id.edtOtpNumber);
        btnVerifyOtp = view.findViewById(R.id.btnSend);
        tvResend = view.findViewById(R.id.textView2);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.DismissDialog();
        btnVerifyOtp.setOnClickListener(v -> {
            progressDialog.startProgress();
            String otp1 = edtOtp.getText().toString().trim();
            verifyOtp(otp1);

        });

        return view;
    }

    private void verifyOtp(String otp1) {
        if (otp1.isEmpty()) {
            Snackbar.make(getView(), "Enter your OTP number", BaseTransientBottomBar.LENGTH_SHORT).show();
        } else if ((otp1.equals(otp))&& (!name.equals(""))) {
            Navigation.findNavController(getView()).navigate(R.id.cart);
        } else if (otp1.equals(otp)) {
            Navigation.findNavController(getView()).navigate(R.id.nav_add_detail);
        } else {
            progressDialog.DismissDialog();
            Snackbar.make(getView(), "Enter valid OTP", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

}

