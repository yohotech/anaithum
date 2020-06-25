package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yoho.anaithumfinal.Adapter.ViewDetailAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.ViewDetailsModel;
import com.yoho.anaithumfinal.Model.ViewOrderProduct;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsFragment extends Fragment {
    private TextView oId,oStatus;
    private RecyclerView rOrderDetails;
    private ViewDetailAdapter vAdapter;
    private List<ViewOrderProduct> vData;
    private SharedPreference sharedPreference;
    private String Id,Status,coupon,mId;
    private TextView Coupon,Total,Payment_Type,Date,Time,Address,Total_savings;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderDetailsFragment() {
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
    public static OrderDetailsFragment newInstance(String param1, String param2) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_order_details, container, false);

        progressDialog.startProgress();
        rOrderDetails=view.findViewById(R.id.rOrderDetails);
        Coupon=view.findViewById(R.id.gst_amount);
        Total=view.findViewById(R.id.total_amount);
        Total_savings=view.findViewById(R.id.savings_amount);
        Payment_Type=view.findViewById(R.id.pay_method);
        Time=view.findViewById(R.id.Odate_time);
        Date=view.findViewById(R.id.date_time);
        Address=view.findViewById(R.id.Deliver_Address);

        oStatus=view.findViewById(R.id.order_status);
        oId=view.findViewById(R.id.o_no);
        mId=getArguments().getString("mId");
        System.out.println("mId:  "+mId);


        InitOrder();
        return view;
    }

    private void InitOrder() {
        vData=new ArrayList<>();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ViewDetailsModel>call=service.viewOrderDetails(mId);
        call.enqueue(new Callback<ViewDetailsModel>() {
            @Override
            public void onResponse(Call<ViewDetailsModel> call, Response<ViewDetailsModel> response) {
                progressDialog.DismissDialog();
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        Log.i("onSuccess ", response.body().toString());
                        if(response.body().getData()!=null){
                            ViewDetailsModel model=response.body();
                            vData=response.body().getData().getProducts();
                            System.out.println("value   "+vData.size());
                            setAdapter(vData);

                            Total.setText("₹"+model.getData().getValues().getM_grandtotal());
                            Payment_Type.setText(model.getData().getValues().getM_paymentmethod());
                            oId.setText(model.getData().getValues().getM_order_id());
                            Date.setText(model.getData().getValues().getM_order_date());
                            Time.setText(model.getData().getValues().getM_order_time());
                            Address.setText(model.getData().getValues().getM_address());
                            Status=model.getData().getValues().getM_delivery_status();
                            System.out.println("status"+Status);
                            coupon=model.getData().getValues().getM_coupon();
                            if(coupon.equals("")){
                                Coupon.setText("₹ 0");
                                Total_savings.setText("₹ 0");
                            }
                            else{
                                Coupon.setText("₹ "+coupon);
                                Total_savings.setText("₹ "+coupon);
                            }

                            if(Status.equals("1")){
                                oStatus.setText("Order Placed");
                            }
                            else if(Status.equals("2")){
                                oStatus.setText("In Progress");
                            }
                            else if(Status.equals("3")){
                                oStatus.setText("Shipped");
                            }
                            else{
                                oStatus.setText("Delivered");
                            }

                        }

                    }
                    else {
                        Log.i("Error Success   ", String.valueOf(response.errorBody().source()));
                    }
                }
                else {
                    Log.i("Error Success   1   ", String.valueOf(response.errorBody().source()));
                }
            }

            @Override
            public void onFailure(Call<ViewDetailsModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("onSuccess "+t.getMessage());

            }
        });
    }

    private void setAdapter(List<ViewOrderProduct> vData) {
        rOrderDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        rOrderDetails.setHasFixedSize(true);
        vAdapter=new ViewDetailAdapter(getActivity(),vData);
        rOrderDetails.setAdapter(vAdapter);
        vAdapter.notifyDataSetChanged();
    }
}
