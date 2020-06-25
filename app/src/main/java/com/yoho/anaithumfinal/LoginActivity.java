package com.yoho.anaithumfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.RefreshMain;
import com.yoho.anaithumfinal.Model.GetOtpModel;
import com.yoho.anaithumfinal.Model.OtpModel;
import com.yoho.anaithumfinal.Model.ProfileModel;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogin,imgOtp,imgClose;
    private EditText mobile_no,mOtp,mName,mEmail;
    private Button btnSubmit,btnVerify,BtnSubmit;
    private ProgressDialog progressDialog;
    private String Otp,Email,Name,number;
    private SharedPreference sharedPreference;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private  String Id;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imgLogin=findViewById(R.id.imageView);
        imgOtp=findViewById(R.id.imageView2);
        imgClose=findViewById(R.id.close);
        mobile_no=findViewById(R.id.edtNumber);
        btnSubmit=findViewById(R.id.btnSend);
        mOtp=findViewById(R.id.edtOtpNumber);
        mName=findViewById(R.id.edtName);
        mEmail=findViewById(R.id.edtEmail);
        BtnSubmit=findViewById(R.id.btnSubmit);

        btnVerify=findViewById(R.id.btnVerify);
        progressDialog=new ProgressDialog(LoginActivity.this);
        sharedPreference=new SharedPreference();
        btnSubmit.setOnClickListener(v -> {
            progressDialog.startProgress();
            number=mobile_no.getText().toString().trim();
            if((number.equals(""))){
                progressDialog.DismissDialog();
                Toast.makeText(this,"Enter your number",Toast.LENGTH_SHORT).show();


            }
            else  if (number.length()<10){
                progressDialog.DismissDialog();
                Toast.makeText(this,"Enter valid number",Toast.LENGTH_SHORT).show();

            }
            else{
                imgLogin.setVisibility(View.GONE);
                mobile_no.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                imgOtp.setVisibility(View.VISIBLE);
                mOtp.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.VISIBLE);
                Login(number);
            }

        });
        btnVerify.setOnClickListener(v -> {
            progressDialog.startProgress();
            String Otp=mOtp.getText().toString().trim();
            if(Otp.equals("")) {
                progressDialog.DismissDialog();
                Toast.makeText(this, "Enter your OTP", Toast.LENGTH_SHORT).show();
            }
            else if(Otp.length()<5){
                progressDialog.DismissDialog();
                Toast.makeText(this,"Enter valid OTP",Toast.LENGTH_SHORT).show();
            }else{
                verifyOtp(number,Otp);
                Toast.makeText(getApplicationContext(), "Login successful ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                finish();
                startActivity(intent);

            }
        });
        imgClose.setOnClickListener(v -> finish());

        BtnSubmit.setOnClickListener(v -> {
            progressDialog.startProgress();
          String Name=mName.getText().toString().trim();
          String Email=mEmail.getText().toString().trim();

          if(Name.length()==0){
              Toast.makeText(this,"Enter your Name",Toast.LENGTH_SHORT).show();
          }
          else if(Email.length()==0){
              Toast.makeText(this,"Enter your Email",Toast.LENGTH_SHORT).show();
          }else {
              if(Email.matches(emailPattern)){
                  sharedPreference.putString(LoginActivity.this,"Name",Name);
                  sharedPreference.putString(LoginActivity.this,"Email",Email);
                  PostDetails(Id,Name,Email);
                  finish();
              }
              else{
                  Toast.makeText(this,"Enter all details ",Toast.LENGTH_SHORT).show();
              }
          }
        });


    }

    private void verifyOtp(String number, String otp) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<GetOtpModel>call=service.verifyOtp(number, otp);
        call.enqueue(new Callback<GetOtpModel>() {
            @Override
            public void onResponse(Call<GetOtpModel> call, Response<GetOtpModel> response) {
                progressDialog.DismissDialog();
                try {
                    if(response.body()!=null){
                        System.out.println("onSuccess"+response.body().getData());
                        System.out.println("code: "+response.body().getCode());
                        if(response.body().getCode().equals("200")) {
                            Id = response.body().getData().getId();
                            Name= String.valueOf(response.body().getData().getName());
                            System.out.println("name"+Name);
                            sharedPreference.putString(getApplicationContext(),"Id",Id);
                            sharedPreference.putString(getApplicationContext(),"Name",Name);
                            System.out.println("shared preference"+sharedPreference.getString(getApplicationContext(),"Id"));
                            Toast.makeText(getApplicationContext(), "OTP verified successfully ", Toast.LENGTH_SHORT).show();
                            if (sharedPreference.getString(LoginActivity.this, "Name") != null) {
                                imgOtp.setVisibility(View.GONE);
                                mOtp.setVisibility(View.GONE);
                                btnVerify.setVisibility(View.GONE);
                                imgLogin.setVisibility(View.VISIBLE);
                                mName.setVisibility(View.VISIBLE);
                            }
                            mEmail.setVisibility(View.VISIBLE);
                            BtnSubmit.setVisibility(View.VISIBLE);
                        }
                        else{
                            System.out.println("response otp: "+response.body().getData());
                            Toast.makeText(getApplicationContext(), ""+response.body().getData(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetOtpModel> call, Throwable t) {
                progressDialog.DismissDialog();

            }
        });
    }

    private void PostDetails(String id, String name, String email) {
        progressDialog.DismissDialog();
        ApiInterface service=ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileModel>call=service.editProfile(id,name,email);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.body()!=null){
                    ProfileModel model=response.body();
                    if(model.getData()!=null){
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();


                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }


    private void Login(String number) {
        System.out.println("number    " + number);
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<OtpModel> call=service.getOtp(number);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                progressDialog.DismissDialog();
                System.out.println("loginresponse    " + response.body());
                try {
                    System.out.println("loginresponse    " + response.body().getData());
                    if(response.body()!=null){
                        OtpModel model=response.body();
                        if (response.body().getData() != null) {


                        }
                        else{
                            System.out.println("data response    "+model.getCode());
                        }
                    }else {
                        System.out.println("Error   response  "+response.errorBody());
                    }
                } catch (Exception e) {
                    System.out.println("Errormsg    " +e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("Errormsg    " +t.getMessage());

            }
        });
    }



}
