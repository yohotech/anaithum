package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Adapter.CategoryAdapter;
import com.yoho.anaithumfinal.Adapter.CategoryListAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.CategoryModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    private RecyclerView rCategoryList;
    private CategoryListAdapter rAdapter;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
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
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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
        View view=inflater.inflate(R.layout.fragment_categories, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Categories",null);
        progressDialog.startProgress();
        rCategoryList=view.findViewById(R.id.recycle_category_list);
        InitCategoryList();
        return view;
    }

    private void InitCategoryList() {

        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryModel> call=service.getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.DismissDialog();
                try {
                    System.out.println("response    " + response.body().getData());
                    if (response.body() != null) {
                        CategoryModel categoryModel = response.body();
                        if (response.body().getData() != null) {
                            rAdapter=new CategoryListAdapter(getActivity(),categoryModel.getData());
                            rCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rCategoryList.setAdapter(rAdapter);

                        }
                        else {
                            System.out.println("data response    "+categoryModel.getCode());
                        }
                    }else{
                        System.out.println("Error   response  "+response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.DismissDialog();
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

    }
}
