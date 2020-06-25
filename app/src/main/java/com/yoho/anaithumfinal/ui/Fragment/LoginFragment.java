package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.OtpModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    TextView mobile_no;
    Button btnSubmit;
    ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        mobile_no=view.findViewById(R.id.edtNumber);
        btnSubmit=view.findViewById(R.id.btnSend);
        progressDialog=new ProgressDialog(getActivity());
        sharedPreference=new SharedPreference();
        btnSubmit.setOnClickListener(v -> {
            String number=mobile_no.getText().toString().trim();
            if((number.equals(""))){
                Snackbar.make(getView(),"Enter your  number", BaseTransientBottomBar.LENGTH_SHORT).show();

            }
            else  if (number.length()<10){
                Snackbar.make(getView(),"Enter valid number", BaseTransientBottomBar.LENGTH_SHORT).show();

            }
            else{
                progressDialog.startProgress();
                Login(number);
            }

        });

        return view;
    }

    private void Login(final String number) {
        progressDialog.DismissDialog();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<OtpModel> call=service.getOtp(number);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                progressDialog.DismissDialog();
                System.out.println("response    " + response.body().getData());
                if(response.body()!=null){
                    OtpModel model=response.body();
                    if (response.body().getData() != null) {
                       /* String mobile_no=model.getData().get(0).getRMobile();
                        String Otp=model.getData().get(0).getROtp();
                        String name=model.getData().get(0).getRName();
                        String email=model.getData().get(0).getREmail();
                        String id=model.getData().get(0).getRId();
                        Bundle bundle=new Bundle();
                        bundle.putString("Otp",Otp);
                        bundle.putString("name",name);
                        bundle.putString("email",email);
                        bundle.putString("id",id);
                        sharedPreference.putString(getActivity(),"mobile",mobile_no);
                        Navigation.findNavController(getView()).navigate(R.id.nav_otp,bundle);*/
                    }
                    else{
                        System.out.println("data response    "+model.getCode());
                    }
                }else {
                    System.out.println("Error   response  "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {

            }
        });



    }
}
