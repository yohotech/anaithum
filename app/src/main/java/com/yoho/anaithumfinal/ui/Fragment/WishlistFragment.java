package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Adapter.WishlistAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.Model.ProductsModel;
import com.yoho.anaithumfinal.Model.WishlistDatum;
import com.yoho.anaithumfinal.Model.WishlistModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishlistFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView rWishlist;
    private WishlistAdapter wAdapter;
    private TextView wishlist_error;
    private String Id;
    private List<WishlistDatum> wishlistData;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WishlistFragment() {
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
    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
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
        View v=inflater.inflate(R.layout.fragment_wishlist, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Wishlist",null);
        progressDialog.startProgress();
        rWishlist=v.findViewById(R.id.recycleWishlist);
        wishlist_error=v.findViewById(R.id.wishlist_empty);
        Id=sharedPreference.getString(getActivity(),"Id");
        /*try {
            if(sharedPreference.getString(getActivity(),"Name").equals("")){
                Intent i=new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        wishlistData=new ArrayList<>();
        InitWishlist();
        return v;
    }

    private void InitWishlist() {

        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<WishlistModel> call=service.getWishlist(Id);
        call.enqueue(new Callback<WishlistModel>() {
            @Override
            public void onResponse(Call<WishlistModel> call, Response<WishlistModel> response) {
                progressDialog.DismissDialog();
                try {
                    if (response.body()!= null){
                        WishlistModel model=response.body();
                        if(model.getData()!=null){
                            wishlistData=model.getData();
                            setAdapter(wishlistData);

                        }
                        else{
                            System.out.println("data response    "+model.getCode());
                            rWishlist.setVisibility(View.GONE);
                            wishlist_error.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),"No Items Available",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        System.out.println("Error   response  "+response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WishlistModel> call, Throwable t) {
                progressDialog.DismissDialog();
                rWishlist.setVisibility(View.GONE);
                wishlist_error.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setAdapter(List<WishlistDatum> wishlistData) {
        wishlist_error.setVisibility(View.GONE);
        rWishlist.setVisibility(View.VISIBLE);
        rWishlist.setHasFixedSize(true);
        rWishlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        wAdapter=new WishlistAdapter(getActivity(),wishlistData);
        rWishlist.setAdapter(wAdapter);
    }
}
