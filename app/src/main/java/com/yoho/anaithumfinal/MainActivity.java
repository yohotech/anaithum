package com.yoho.anaithumfinal;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RefreshMain;
import com.yoho.anaithumfinal.Model.CartCountModel;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;
import com.yoho.anaithumfinal.ui.Fragment.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Addtocartcount, RefreshMain {
    private FirebaseAnalytics mFirebaseAnalytics;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView cartBadge;
    private String mCartItemCount,Id,Name;
    private MenuItem menuItem;
    private Handler handler;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";
    View headerview;
    TextView title;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        progressDialog=new ProgressDialog(this);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.berkshire);
        ((TextView) toolbar.getChildAt(0)).setTypeface(typeface);

        sharedPreference=new SharedPreference();
        Id=sharedPreference.getString(getApplicationContext(),"Id");
        Name=sharedPreference.getString(getApplicationContext(),"Name");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(Id!=null) {
                    progressDialog.startProgress();
                    Logout();
                    Toast.makeText(MainActivity.this,"Logout Successfull",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        bottomNavigationView=findViewById(R.id.bottom_nav_view);
        if(Id.equals("")){
            bottomNavigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        }
        else {
            bottomNavigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);

        }
        headerview=navigationView.getHeaderView(0);
        title=headerview.findViewById(R.id.user);

        if(Id.equals("")&&Name.equals("")){
            title.setText("Guest");
        }
        else if(Id!=null&&Name!=null){
            title.setText(Name);
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if(sharedPreference.getString(getApplicationContext(),"Id")!=null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_profile, R.id.nav_categories, R.id.nav_orders, R.id.nav_wishlist,
                    R.id.nav_terms, R.id.nav_support, R.id.nav_cart, R.id.nav_coupon)
                    .setDrawerLayout(drawer)
                    .build();
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        NavigationUI.setupWithNavController(bottomNavigationView, navController);



    }

    private void Logout() {
        sharedPreference.removeString(MainActivity.this, "Id");
        sharedPreference.removeString(MainActivity.this, "Name");
        sharedPreference.removeString(MainActivity.this, "Email");
        refresh();
        progressDialog.DismissDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem=menu.findItem(R.id.cart);
        cartCount();

        if(mCartItemCount==null){
            menuItem.setActionView(null);
        }
        else{
            menuItem.setActionView(R.layout.badge_layout);
            View view=menuItem.getActionView();
            cartBadge=view.findViewById(R.id.tvCartCount);
            cartBadge.setText(mCartItemCount);

        }
        return true;
    }

    private void cartCount() {

        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CartCountModel>call=service.getCartCount(Id);
        call.enqueue(new Callback<CartCountModel>() {
            @Override
            public void onResponse(Call<CartCountModel> call, Response<CartCountModel> response) {
                if(response.body()!=null){
                    CartCountModel cCount=response.body();
                    if(cCount.getData()!=null) {
                        mCartItemCount = cCount.getData().get(0).getCount();
                        System.out.println("count   "+mCartItemCount);
                        menuItem.setActionView(R.layout.badge_layout);
                        View view=menuItem.getActionView();
                        cartBadge=view.findViewById(R.id.tvCartCount);
                        cartBadge.setText(mCartItemCount);
                        if(mCartItemCount.equals("0")){
                         //   menuItem.setVisible(false);
                            menuItem.setActionView(null);
                        }


                        view.setOnClickListener(v -> {
                            Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_cart);
                        });

                    }else{
                        menuItem.setActionView(null);
                      //  menuItem.setVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<CartCountModel> call, Throwable t) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wish_list:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_wishlist);
                break;
            case R.id.cart:
                if (sharedPreference.getString(getApplicationContext(), "Name") != null){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_cart);
                }
                else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                break;
            case R.id.nav_logout:
                sharedPreference.removeString(MainActivity.this,"Id");
                sharedPreference.removeString(MainActivity.this,"Name");
                sharedPreference.removeString(MainActivity.this,"Email");
                Toast.makeText(MainActivity.this,"Logout Successful",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    protected void onRestart() {

        super.onRestart();

        this.recreate();
    }
    private final Runnable m_Runnable = new Runnable()
    {

        public void run()


        {
            // Toast.makeText(HomeActivity.this,"in runnable",Toast.LENGTH_SHORT).show();

            MainActivity.this.handler.postDelayed(m_Runnable, 1000);
        }

    };//runnab

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void addcount() {

        cartCount();
    }

    @Override
    public void deletecount() {
        cartCount();
    }


    @Override
    public void refresh() {
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);

    }
}
