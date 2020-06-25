package com.yoho.anaithumfinal.ui.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yoho.anaithumfinal.Adapter.CouponAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Model.CouponDatum;
import com.yoho.anaithumfinal.Model.CouponModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CouponFragment extends Fragment implements RecyclerClickListener {
    private RecyclerView rCoupon;
    private CouponAdapter couponAdapter;
    private String Id;
    private List<CouponDatum> cData;
    private String copy;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CouponFragment() {
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
    public static CouponFragment newInstance(String param1, String param2) {
        CouponFragment fragment = new CouponFragment();
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
        progressDialog=new ProgressDialog(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coupon, container, false);
        rCoupon=view.findViewById(R.id.rCoupon);
        progressDialog.startProgress();
        Id=sharedPreference.getString(getActivity(),"Id");
        cData=new ArrayList<>();
        InitCoupon(Id);
        return view;
    }

    private void InitCoupon(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CouponModel>call=service.getCoupon(id);
        call.enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(Call<CouponModel> call, Response<CouponModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    CouponModel model=response.body();
                    if(response.body().getData()!=null){
                        cData=model.getData();
                        setAdapter(cData);
                    }
                }

            }

            @Override
            public void onFailure(Call<CouponModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }

    private void setAdapter(List<CouponDatum> cData) {
        rCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        couponAdapter=new CouponAdapter(getActivity(),cData);
        rCoupon.setHasFixedSize(true);
        rCoupon.setAdapter(couponAdapter);
        couponAdapter.setOnClickListener(this);
    }

    @Override
    public void OnItemClick(String v, int position) {
        if(v.equals("copy")){
            ClipboardManager clipboard =  (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
            copy=cData.get(position).getCoCode();
            ClipData clipData=ClipData.newPlainText("Coupon Copied",copy);
            clipboard.setPrimaryClip(clipData);
            Toast.makeText(getActivity(),"Coupon is copied",Toast.LENGTH_SHORT).show();

        }

    }
}
