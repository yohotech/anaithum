package com.yoho.anaithumfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.yoho.anaithumfinal.Util.Utils;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        CubeGrid cubeGrid = new CubeGrid();
        progressBar.setIndeterminateDrawable(cubeGrid);
        Handler handler=new Handler();
        try {
            handler.postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            },2000);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
