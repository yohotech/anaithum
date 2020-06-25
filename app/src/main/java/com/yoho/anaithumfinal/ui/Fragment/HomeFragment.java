package com.yoho.anaithumfinal.ui.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.yoho.anaithumfinal.Adapter.CategoryAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Adapter.ImageSliderAdapter;
import com.yoho.anaithumfinal.MainActivity;
import com.yoho.anaithumfinal.Model.BannerModel;
import com.yoho.anaithumfinal.Model.CategoryModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView rCategory;
    private CategoryAdapter mAdapter;
    private CardView search;
    private ArrayList mCategory;
    private SliderView sliderView;
    private ProgressDialog progressDialog;
    private SharedPreference sharedPreference;
    private String Id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Home",null);
        rCategory=view.findViewById(R.id.recycle_category);
        fetchCategory();

        sliderView = view.findViewById(R.id.imageSlider);
        DisplayImageSlider();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.startProgress();

        search=view.findViewById(R.id.Search_bar);
        search.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.nav_search));

        return view;

    }

    private void DisplayImageSlider() {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<BannerModel> call=service.getBanner();
        call.enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                try {
                    System.out.println("response    " + response.body().getData());
                    if (response.body() != null) {
                        progressDialog.DismissDialog();
                        BannerModel banner=response.body();
                        if(banner.getData()!=null){
                            sliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(),banner.getData()));
                            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView.setIndicatorSelectedColor(Color.RED);
                            sliderView.setIndicatorUnselectedColor(Color.WHITE);
                            sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                            sliderView.startAutoCycle();
                        }
                        else{
                            System.out.println("data response    "+banner.getCode());
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
            public void onFailure(Call<BannerModel> call, Throwable t) {
                try {
                    progressDialog.DismissDialog();
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void fetchCategory() {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryModel> call=service.getCategory();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                try {
                    progressDialog.DismissDialog();
                    System.out.println("response    " + response.body().getData());
                    if (response.body() != null) {
                        CategoryModel categoryModel = response.body();
                        if (response.body().getData() != null) {

                            mAdapter=new CategoryAdapter(getActivity(),new ArrayList<>(categoryModel.getData()));
                            rCategory.setLayoutManager(new GridLayoutManager(getActivity(),3));
                            rCategory.setAdapter(mAdapter);

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
                try {
                    progressDialog.DismissDialog();
                    Toast.makeText(getActivity(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
