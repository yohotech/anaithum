package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Model.ApplyCouponModel;
import com.yoho.anaithumfinal.Model.CheckResponse;
import com.yoho.anaithumfinal.Model.FetchDeliveryModel;
import com.yoho.anaithumfinal.Model.OrderPlacedResponse;
import com.yoho.anaithumfinal.Model.ViewAddressModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    private Addtocartcount count;
    private Button Add,btnApply,btnPlaceOrder;
    private RadioGroup rGroup,rPayment;
    private EditText edtCoupon;
    private RadioButton Express,Standard,Online,Cash;
    private SharedPreference sharedPreference;
    private TextView sQuote,ePrice,sPrice,eQuote,cAddress,cArea,cCity,cState,cPincode,cLandmark,tvCouponAmount,tvTotal,tvShippingCost,tvGrandTotal,tvCouponStatus;
    private String Id,grand_total,total,shipping_cost,shipping_method,tax_percent,payment_method,coupon_rupees,coupon_code;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
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
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_blank, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Checkout",null);
        Id=sharedPreference.getString(getActivity(),"Id");
        rGroup=view.findViewById(R.id.rGroup);
        Express=view.findViewById(R.id.express_delivery);
        Standard=view.findViewById(R.id.standard_delivery);
        ePrice=view.findViewById(R.id.express_price);
        sPrice=view.findViewById(R.id.standard_delivery_price);
        sQuote=view.findViewById(R.id.sdelivery_quote);
        eQuote=view.findViewById(R.id.edelivery_quote);
        cAddress=view.findViewById(R.id.Address_line);
        cArea=view.findViewById(R.id.Address_area);
        cCity=view.findViewById(R.id.Address_city);
        cPincode=view.findViewById(R.id.Address_pincode);
        edtCoupon=view.findViewById(R.id.edt_coupon);
        btnApply=view.findViewById(R.id.btn_apply);
        cLandmark=view.findViewById(R.id.Address_landmark);
        tvCouponAmount=view.findViewById(R.id.txt_coupon_amount);
        tvCouponStatus=view.findViewById(R.id.tv_coupon);
        tvTotal=view.findViewById(R.id.txt_total_amount_checkout);
        tvShippingCost=view.findViewById(R.id.txt_shipping_cost);
        tvGrandTotal=view.findViewById(R.id.txt_grand_total_amount_checkout);
        rPayment=view.findViewById(R.id.rPayment);
        Online=view.findViewById(R.id.Online);
        Cash=view.findViewById(R.id.COD);
        btnPlaceOrder=view.findViewById(R.id.btnPlaceOrder);
        Add=view.findViewById(R.id.addAddress);
        Add.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.nav_add_address));

        total=getArguments().getString("check_total");
        tvTotal.setText("₹"+total);
        tvShippingCost.setText("₹0");
        tvCouponAmount.setText("₹0");
        tvGrandTotal.setText("₹"+total);

        InitAddress(Id);
        InitShipping(Id);
        InitPaymentMethod();


        btnApply.setOnClickListener(v -> {
            if(!edtCoupon.getText().toString().isEmpty()){
                coupon_code=edtCoupon.getText().toString().trim();
                ApplyCoupon(Id,total,coupon_code,shipping_method);
                /*Toast.makeText(getActivity(), ""+coupon_code, Toast.LENGTH_SHORT).show();*/
            }else {
                Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();

            }
        });

        btnPlaceOrder.setOnClickListener(v -> {
            System.out.println("status"+shipping_method);
            System.out.println("status"+payment_method);
            if(shipping_method!=null && payment_method!=null){
                if(!edtCoupon.getText().toString().isEmpty()&&coupon_rupees!=null){
                    PlaceOrder(Id, shipping_method,coupon_rupees,total, shipping_cost, tax_percent, grand_total, payment_method);
                }
                else {

                    InitEmptyCoupon(Id,shipping_method);
                    PlaceOrder(Id, shipping_method,coupon_rupees,total, shipping_cost, tax_percent, grand_total, payment_method);
                }
            }
            else {
                Toast.makeText(getActivity(), "Fill all fields", Toast.LENGTH_SHORT).show();
            }



        });
       return view;
    }

    private void PlaceOrder(String id, String shipping_method, String coupon_rupees, String total, String shipping_cost, String tax_percent, String grand_total, String payment_method) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<OrderPlacedResponse>call=service.placeOrder(id, shipping_method, coupon_rupees, total, shipping_cost, tax_percent, grand_total, payment_method);
        call.enqueue(new Callback<OrderPlacedResponse>() {
            @Override
            public void onResponse(Call<OrderPlacedResponse> call, Response<OrderPlacedResponse> response) {
                if(response.body()!=null){
                    if(response.body().getData()!=null){
                        System.out.println("id"+id);
                        System.out.println("shipping_method"+shipping_method);
                        System.out.println("total"+total);
                        System.out.println("grand_total"+grand_total);
                        System.out.println("shipping_cost"+shipping_cost);
                        System.out.println("payment_method"+payment_method);

                        count.addcount();
                        Toast.makeText(getActivity(), "Your Order has been placed successfully", Toast.LENGTH_SHORT).show();
                        NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_checkout,true).build();
                        Navigation.findNavController(getView()).navigate(R.id.nav_home,null,options);
                    }

                }else {
                    Toast.makeText(getActivity(),""+response.errorBody().source(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderPlacedResponse> call, Throwable t) {

            }
        });
    }

    private void InitPaymentMethod() {
        rPayment.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.Online:
                    payment_method="online";
                    break;
                case R.id.COD :
                    payment_method="cash";
                    break;
            }
        });
    }

    private void InitEmptyCoupon(String id, String shipping_method) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CheckResponse>call=service.getCheckDetails(id, shipping_method);
        call.enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                if(response.body()!=null){
                    if(response.body().getData()!=null){
                        coupon_rupees="0";
                        shipping_cost=response.body().getData().get(0).getShippingCost();
                        grand_total= String.valueOf(response.body().getData().get(0).getGrandTotal());
                        tax_percent= String.valueOf(response.body().getData().get(0).getTax());
                        tvShippingCost.setText("₹"+shipping_cost);
                        tvCouponAmount.setText("₹"+coupon_rupees);
                        tvGrandTotal.setText("₹"+grand_total);
                        System.out.println("status"+shipping_cost);
                        System.out.println("status"+coupon_rupees);
                        System.out.println("status"+grand_total);
                        System.out.println("status"+tax_percent);
                        System.out.println("status :"+response.body().getCode());

                    }
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {

            }
        });


    }

    private void ApplyCoupon(String id, String total, String coupon_code, String shipping_method) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ApplyCouponModel>call=service.applyCoupon(id, total, coupon_code, shipping_method);
        call.enqueue(new Callback<ApplyCouponModel>() {
            @Override
            public void onResponse(Call<ApplyCouponModel> call, Response<ApplyCouponModel> response) {
                if(response.body()!=null){
                    if(response.body().getData()!=null){

                        if(response.body().getCode().equals("200")){
                            shipping_cost=response.body().getData().get(0).getShipping_cost();
                            coupon_rupees=response.body().getData().get(0).getCoupon_amount();
                            grand_total=response.body().getData().get(0).getGrand_total();
                            tax_percent=response.body().getData().get(0).getTax();
                            tvShippingCost.setText("₹"+shipping_cost);
                            tvCouponAmount.setText("₹"+coupon_rupees);
                            tvGrandTotal.setText("₹"+grand_total);
                            edtCoupon.setVisibility(View.GONE);
                            tvCouponStatus.setVisibility(View.VISIBLE);
                            tvCouponStatus.setText(coupon_code+"is applied");
                            System.out.println("status"+shipping_cost);
                            System.out.println("status"+coupon_rupees);
                            System.out.println("status"+grand_total);
                            System.out.println("status"+tax_percent);
                            System.out.println("status :"+response.body().getCode());
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.body().getCode().equals("201")){
                            Toast.makeText(getActivity(),"Invalid Coupon",Toast.LENGTH_SHORT).show();
                            System.out.println("status :"+response.body().getCode());
                        }
                        else if(response.body().getCode().equals("203")){
                            Toast.makeText(getActivity(),"Coupon used",Toast.LENGTH_SHORT).show();
                            System.out.println("status :"+response.body().getCode());
                        }

                    }
                    else {
                        System.out.println("status"+response.errorBody().source());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApplyCouponModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Invalid Coupon",Toast.LENGTH_SHORT).show();
                System.out.println("status :"+t.getMessage());
                System.out.println("status"+id);
                System.out.println("status"+total);
                System.out.println("status"+coupon_code);
                System.out.println("status"+total);

            }
        });
    }

    private void InitShipping(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<FetchDeliveryModel>call=service.fetchDeliveryMethod(id);
        call.enqueue(new Callback<FetchDeliveryModel>() {
            @Override
            public void onResponse(Call<FetchDeliveryModel> call, Response<FetchDeliveryModel> response) {
                if(response.body()!=null){
                    ePrice.setText("₹"+response.body().getData().getPin_exp_price());
                    sPrice.setText("₹"+response.body().getData().getPin_std_price());
                    sQuote.setVisibility(View.GONE);
                    eQuote.setVisibility(View.GONE);
                    rGroup.setOnCheckedChangeListener((group, checkedId) -> {

                        switch (checkedId){
                            case R.id.express_delivery:
                                shipping_method="express";
                                shipping_cost=response.body().getData().getPin_exp_price();
                                eQuote.setText(response.body().getData().getPin_exp_content());
                                eQuote.setVisibility(View.VISIBLE);
                                sQuote.setVisibility(View.GONE);
                                InitEmptyCoupon(Id,shipping_method);
                                tvShippingCost.setText("₹"+shipping_cost);
                                tvGrandTotal.setText("₹"+(Integer.parseInt(total)+Integer.parseInt(shipping_cost)));


                                break;
                            case R.id.standard_delivery:
                                shipping_method="standard";
                                shipping_cost=response.body().getData().getPin_std_price();
                                sQuote.setVisibility(View.VISIBLE);
                                eQuote.setVisibility(View.GONE);
                                InitEmptyCoupon(Id,shipping_method);
                                sQuote.setText(response.body().getData().getPin_std_content());
                                tvShippingCost.setText("₹"+shipping_cost);
                                tvGrandTotal.setText("₹"+(Integer.parseInt(total)+Integer.parseInt(shipping_cost)));
                                break;

                        }


                    });

                }

            }

            @Override
            public void onFailure(Call<FetchDeliveryModel> call, Throwable t) {

            }
        });
    }

    private void InitAddress(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ViewAddressModel> call=service.getAddress(id);
        call.enqueue(new Callback<ViewAddressModel>() {
            @Override
            public void onResponse(Call<ViewAddressModel> call, Response<ViewAddressModel> response) {
                if(response.body()!=null){
                    System.out.println("onSuccess:  "+response.body().getData());
                    if(response.body().getData()!=null){
                        cAddress.setText(response.body().getData().get(0).getR_address());
                        cArea.setText(response.body().getData().get(0).getR_area());
                        cCity.setText(response.body().getData().get(0).getR_city());
                        cPincode.setText(response.body().getData().get(0).getR_pincode());
                        cLandmark.setText(response.body().getData().get(0).getR_landmark());
                        if(response.body().getData().get(0).getR_pincode().equals("")){
                            Navigation.findNavController(getView()).navigate(R.id.nav_add_address);
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<ViewAddressModel> call, Throwable t) {

            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            count = (Addtocartcount) context;
        } catch (Exception e) {
            System.out.println("exception   " + e);
        }
    }
}