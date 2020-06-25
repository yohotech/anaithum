package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.yoho.anaithumfinal.Interface.RefreshCart;
import com.yoho.anaithumfinal.Model.ApplyCouponModel;
import com.yoho.anaithumfinal.Model.CheckResponse;
import com.yoho.anaithumfinal.Model.FetchDeliveryModel;
import com.yoho.anaithumfinal.Model.OrderPlacedResponse;
import com.yoho.anaithumfinal.Model.ViewAddressModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    private Addtocartcount count;
    private Button Add,btnApply,btnPlaceOrder;
    private RadioGroup rGroup,rPayment;
    private EditText edtCoupon;
    private RadioButton Express,Standard,Online,Cash;
    private SharedPreference sharedPreference;
    private TextView sQuote,ePrice,sPrice,eQuote,cAddress,cArea,cCity,cState,cPincode,cLandmark,tvCoupon,tvTotal,tvShippingCost,tCoupon,tvGrandTotal;
    private String Id,Total,ShippingCharge,Coupon,GrandTotal,DeliveryMode,PaymentMethod,coupon_amount,tax,pTax;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutFragment() {
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
    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
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
        View view= inflater.inflate(R.layout.fragment_checkout, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Checkout",null);
        progressDialog.startProgress();
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
        tvCoupon=view.findViewById(R.id.tv_coupon);
        tvTotal=view.findViewById(R.id.txt_total_amount_checkout);
        tvShippingCost=view.findViewById(R.id.txt_shipping_cost);
        tCoupon=view.findViewById(R.id.txt_coupon_amount);
        tvGrandTotal=view.findViewById(R.id.txt_grand_total_amount_checkout);
        rPayment=view.findViewById(R.id.rPayment);
        Online=view.findViewById(R.id.Online);
        Cash=view.findViewById(R.id.COD);
        btnPlaceOrder=view.findViewById(R.id.btnPlaceOrder);

        Add=view.findViewById(R.id.addAddress);
        Add.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.nav_add_address));
        InitAddress(Id);
        InitCheckout(Id,DeliveryMode);
        InitShipping(Id);
        InitPaymentMethod();


        try{
            Total=getArguments().getString("check_total");
        }catch (Exception e){
            Total="0";
        }

        ShippingCharge="0";
        coupon_amount="0";
        GrandTotal=""+(Integer.parseInt(Total)+Integer.parseInt(ShippingCharge)-Integer.parseInt(coupon_amount));
        tvTotal.setText("₹ "+Total);
        tvShippingCost.setText("₹ "+ShippingCharge);
        tCoupon.setText("₹ 0");
        tvGrandTotal.setText("₹ "+GrandTotal);

        btnApply.setOnClickListener(v -> {
            progressDialog.startProgress();
            if(edtCoupon.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Enter your coupon",Toast.LENGTH_SHORT).show();
            }
            else {
                Coupon=edtCoupon.getText().toString();
                ApplyCoupon(Id,Total,Coupon,DeliveryMode);
            }
        });

        /*btnApply.setOnClickListener(v -> {
            Coupon=edtCoupon.getText().toString().trim();
            if(edtCoupon.getText().toString()!=null) {
                InitCheckout(Id,DeliveryMode);
                Toast.makeText(getActivity(),"Enter your coupon",Toast.LENGTH_SHORT).show();

            }else{
                ApplyCoupon(Id,Total,Coupon,DeliveryMode);

                *//*tvTotal.setText("₹ "+Total);
                tvShippingCost.setText("₹ "+ShippingCharge);
                tCoupon.setText("₹ 0");
                tvGrandTotal.setText("₹ "+GrandTotal);*//*
            }
        });*/
        btnPlaceOrder.setOnClickListener(v -> {
            progressDialog.startProgress();
            if(DeliveryMode!=null&&ShippingCharge!=null&&PaymentMethod!=null) {

                PlaceOrder(Id, DeliveryMode, coupon_amount, Total, ShippingCharge, tax, GrandTotal, PaymentMethod);
            }
            else{
                Toast.makeText(getActivity(),"Please select all details",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }



    private void InitCheckout(String id, String deliveryMode) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CheckResponse>call=service.getCheckDetails(id, deliveryMode);
        call.enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    if(response.body().getData()!=null){
                        tvTotal.setText("₹ "+response.body().getData().get(0).getTotal());
                        tvShippingCost.setText("₹ "+response.body().getData().get(0).getShippingCost());
                        tCoupon.setText("₹ 0");
                        tvGrandTotal.setText("₹ "+response.body().getData().get(0).getGrandTotal());
                        System.out.println("success: "+response.body().getData().get(0).getGrandTotal());
                        pTax= String.valueOf(response.body().getData().get(0).getTax());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                progressDialog.DismissDialog();
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

    private void PlaceOrder(String id, String deliveryMode, String coupon_amount, String total, String shippingCharge, String tax, String grandTotal, String paymentMethod) {
        
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<OrderPlacedResponse>call=service.placeOrder(id, deliveryMode, coupon_amount, total, shippingCharge, pTax, grandTotal, paymentMethod);
        call.enqueue(new Callback<OrderPlacedResponse>() {
            @Override
            public void onResponse(Call<OrderPlacedResponse> call, Response<OrderPlacedResponse> response) {
                progressDialog.DismissDialog();
                if(response.isSuccessful()){
                    if(response.body()!=null) {

                        count.addcount();

                        Toast.makeText(getActivity(), "Your Order has been placed successfully", Toast.LENGTH_SHORT).show();
                        NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_checkout,true).build();
                        Navigation.findNavController(getView()).navigate(R.id.nav_home,null,options);
                    }

                }
                else {
                    System.out.println("onError: "+response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<OrderPlacedResponse> call, Throwable t) {
                progressDialog.DismissDialog();

            }
        });
    }


    private void InitPaymentMethod() {
        rPayment.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.Online:
                    PaymentMethod="online";
                    break;
                case R.id.COD :
                    PaymentMethod="cash";
            }
        });
    }

    private void ApplyCoupon(String id, String total, String coupon, String deliveryMode) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ApplyCouponModel>call=service.applyCoupon(id, total, coupon, deliveryMode);
        call.enqueue(new Callback<ApplyCouponModel>() {
            @Override
            public void onResponse(Call<ApplyCouponModel> call, Response<ApplyCouponModel> response) {
                progressDialog.startProgress();
                if(response.body()!=null){
                    System.out.println("onSuccess:  "+response.body().getData());
                    if(response.body().getData()!=null){
                            System.out.println("success"+response.body().getCode());
                            /*GrandTotal=response.body().getData().get(0).getGrand_total();*/
                            edtCoupon.setVisibility(View.GONE);
                            tvCoupon.setVisibility(View.VISIBLE);
                            tvCoupon.setText("Coupon: "+Coupon+" has been applied");
                            tvCoupon.setTextColor(getResources().getColor(R.color.colorDiscount));
                            /*tvTotal.setText("₹ "+Total);*/
                            System.out.println("Total"+total);
                            System.out.println("Shipping chatrge: "+response.body().getData().get(0).getShipping_cost());
                            System.out.println("GrandTotal"+response.body().getData().get(0).getGrand_total());
                            /*tvShippingCost.setText("₹ "+response.body().getData().get(0).getShipping_cost());*/

                            tvGrandTotal.setText("₹ "+response.body().getData().get(0).getGrand_total());
                            coupon_amount=response.body().getData().get(0).getCoupon_amount();
                            /*tax=response.body().getData().get(0).getTax_percent();*/
                            if(response.body().getData().get(0).getCoupon_amount()!=null){
                                tCoupon.setText("₹ "+response.body().getData().get(0).getCoupon_amount());
                            }
                            else {
                                tCoupon.setText("₹ 0");
                            }

                        /*if(response.body().getCode().equals("201")){
                            edtCoupon.setVisibility(View.GONE);
                            tvCoupon.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            tvCoupon.setText("Coupon: "+Coupon+" is invalid");
                            System.out.println("code:  "+response.body().getCode());
                        }*/

                    }
                    else{
                        System.out.println("success 1: "+response.body().getCode());
                        System.out.println("Error1:"+response.errorBody());
                    }


                }
                else{
                    System.out.println("success 2: "+response.body().getCode());
                    System.out.println("Error2:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ApplyCouponModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }

    private void InitAddress(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ViewAddressModel>call=service.getAddress(id);
        call.enqueue(new Callback<ViewAddressModel>() {
            @Override
            public void onResponse(Call<ViewAddressModel> call, Response<ViewAddressModel> response) {
                progressDialog.DismissDialog();
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
                progressDialog.DismissDialog();
            }
        });
    }


    private void InitShipping(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<FetchDeliveryModel>call=service.fetchDeliveryMethod(id);
        call.enqueue(new Callback<FetchDeliveryModel>() {
            @Override
            public void onResponse(Call<FetchDeliveryModel> call, Response<FetchDeliveryModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    System.out.println("onSuccess:  "+response.body().getData().getPin_code());
                    if(response.body().getData()!=null){
                        ePrice.setText("₹"+response.body().getData().getPin_exp_price());
                        sPrice.setText("₹"+response.body().getData().getPin_std_price());


                        sQuote.setVisibility(View.GONE);
                        eQuote.setVisibility(View.GONE);
                        rGroup.setOnCheckedChangeListener((group, checkedId) -> {

                            switch (checkedId){
                                case R.id.express_delivery:
                                    DeliveryMode="express";
                                    ShippingCharge=response.body().getData().getPin_exp_price();
                                    eQuote.setText(response.body().getData().getPin_exp_content());
                                    eQuote.setVisibility(View.VISIBLE);
                                    sQuote.setVisibility(View.GONE);
                                    tvShippingCost.setText("₹"+ShippingCharge);
                                    InitCheckout(id,DeliveryMode);

                                    break;
                                case R.id.standard_delivery:
                                    DeliveryMode="standard";
                                    ShippingCharge=response.body().getData().getPin_std_price();
                                    sQuote.setVisibility(View.VISIBLE);
                                    eQuote.setVisibility(View.GONE);
                                    sQuote.setText(response.body().getData().getPin_std_content());
                                    tvShippingCost.setText("₹"+ShippingCharge);
                                    InitCheckout(id,DeliveryMode);
                                    break;

                            }


                        });

                    }
                    else{
                        System.out.println("Error  "+response.errorBody().toString() );
                    }
                }

            }

            @Override
            public void onFailure(Call<FetchDeliveryModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }
}
