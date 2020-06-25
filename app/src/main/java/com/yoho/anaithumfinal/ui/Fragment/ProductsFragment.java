package com.yoho.anaithumfinal.ui.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.resources.TextAppearance;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Adapter.ProductsAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.ProductsDatum;
import com.yoho.anaithumfinal.Model.ProductsModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment implements RecyclerClickListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView rProducts;
    private ProductsAdapter pAdapter;
    private String wStatus,pId,btnStatus,cId;
    private SharedPreference sharedPreference;
    private String Id;
    private List<ProductsDatum> productsData;
    private Context context;
    private ProgressDialog progressDialog;
    Addtocartcount count;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
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
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View v=inflater.inflate(R.layout.fragment_products, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Products",null);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getArguments().getString("cName"));

        progressDialog.startProgress();
        rProducts=v.findViewById(R.id.rProducts);
        cId=getArguments().getString("cId");
        if(sharedPreference.getString(getActivity(),"Id")!=null){
            Id=sharedPreference.getString(getActivity(),"Id");
            System.out.println("output  "+Id);
        }
        else{
            Id="0";
            System.out.println("output  "+Id);
        }



        productsData=new ArrayList<>();
        count.addcount();
        InitProducts();
        return v;
    }

    private void InitProducts() {
        productsData=new ArrayList<>();

        if(Id!=null){
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            Call<ProductsModel> call=service.getProducts(cId,Id);

            call.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {
                    progressDialog.DismissDialog();
                    if (response.body() != null) {
                        ProductsModel productsModel=response.body();
                        if (productsModel.getData()!=null) {
                            productsData=productsModel.getData();
                            setadapter(productsData);
                            count.addcount();
                        }else{
                            System.out.println("data response    "+productsModel.getCode());
                        }

                    }
                    else{
                        System.out.println("Error   response  "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ProductsModel> call, Throwable t) {
                    progressDialog.DismissDialog();
                }
            });
        }
        else{
            Id="0";
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            System.out.println("output"+Id);
            Call<ProductsModel> call=service.getProducts(cId,Id);
            call.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {
                    progressDialog.DismissDialog();
                    if (response.body() != null) {
                        ProductsModel productsModel=response.body();
                        if (productsModel.getData()!=null) {
                            productsData=productsModel.getData();
                            setadapter(productsData);
                            count.addcount();
                        }else{
                            System.out.println("data response    "+productsModel.getCode());
                        }

                    }
                    else{
                        System.out.println("Error   response  "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ProductsModel> call, Throwable t) {
                    progressDialog.DismissDialog();

                }
            });
        }

    }

    private void setadapter(List<ProductsDatum> data) {
        rProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        pAdapter=new ProductsAdapter(getActivity(),data);
        rProducts.setAdapter(pAdapter);
        pAdapter.notifyDataSetChanged();
        pAdapter.setonitemclicklistener(this);
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            count=(Addtocartcount) context;
        }catch (Exception e){
            System.out.println("exception   "+e);
        }


    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    @Override
    public void OnItemClick(String v, int position) {

        if(v.equals("add")) {
            String productId=productsData.get(position).getPId();
            btnStatus="1";

            if(Id.equals("")){
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
            else {
                InitAddtoCart(btnStatus,Id,productId);
                /*Navigation.findNavController(getView()).navigate(R.id.nav_cart);*/
            }
        }

    }

    private void InitAddtoCart(String btnStatus, String id, String productId) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse>call=service.Cart(btnStatus,id,productId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    Toast.makeText(getContext(),"Item is added to the cart",Toast.LENGTH_SHORT).show();
                    count.addcount();



                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                progressDialog.DismissDialog();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}
