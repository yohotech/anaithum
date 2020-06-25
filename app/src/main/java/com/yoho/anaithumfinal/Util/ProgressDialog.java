package com.yoho.anaithumfinal.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;

import com.yoho.anaithumfinal.R;

public class ProgressDialog {
    private android.app.ProgressDialog progressDialog;
    private Activity activity;
    public ProgressDialog(Activity myActivity) {
        activity=myActivity;
    }

    public void startProgress(){
        progressDialog=new android.app.ProgressDialog(activity);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public void DismissDialog(){
        progressDialog.dismiss();
    }


    /*private AlertDialog alertDialog;
    private Activity activity;


    public ProgressDialog(Activity myActivity) {
        activity=myActivity;
    }

    public void StartLoadingProgress(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }
    public void DismissDialog(){
        alertDialog.dismiss();
    }*/
}
