package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Adapter.ImageAdapter;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.MainActivity;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.ProductImagesModel;
import com.yoho.anaithumfinal.Model.ProductsOverviewModel;
import com.yoho.anaithumfinal.Model.WishlistAddModel;
import com.yoho.anaithumfinal.Model.WishlistDeleteModel;
import com.yoho.anaithumfinal.ProductImagePojo;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsDetailed extends Fragment {

    private List<ProductImagePojo> productImagePojos;
    private ImageAdapter imageAdapter;
    private SliderView slider;
    private TextView pName,pUnit,pDesc,pMrp,pOprice,pDiscount,OOS,pSymbol,pOffer;
    private String pId,pWstatus,wId;
    private Button AddToCart;
    private SharedPreference sharedPreference;
    private CheckBox pdWishlist;
    public String Id;
    private Addtocartcount count;
    private Integer product_discount,product_oos;
    public static final String Email = "emailKey";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsDetailed() {
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
    public static ProductsDetailed newInstance(String param1, String param2) {
        ProductsDetailed fragment = new ProductsDetailed();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_products_detailed, container, false);
        pName=view.findViewById(R.id.pName);
        pUnit=view.findViewById(R.id.pquantity);
        pDesc=view.findViewById(R.id.pdescription);
        pMrp=view.findViewById(R.id.pPrice);
        pOprice=view.findViewById(R.id.pdiscount);
        pDiscount=view.findViewById(R.id.pOfferSymbol);
        OOS=view.findViewById(R.id.pOOS);
        AddToCart=view.findViewById(R.id.add_product);
        pdWishlist=view.findViewById(R.id.product_detailed_wishlist);
        pSymbol=view.findViewById(R.id.pSymbol);
        pOffer=view.findViewById(R.id.pOffer);
        sharedPreference=new SharedPreference();
        pId=getArguments().getString("pId");
        Id=sharedPreference.getString(getActivity(),"Id");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getArguments().getString("cName"));


        slider = view.findViewById(R.id.imageSlider_product);
        LoadImage();


        InitProductOverview();
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitAddToCart();


            }
        });




        return view;
    }



    private void LoadImage() {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ProductImagesModel>call=service.getPimages(pId);
        call.enqueue(new Callback<ProductImagesModel>() {
            @Override
            public void onResponse(Call<ProductImagesModel> call, Response<ProductImagesModel> response) {
                if(response.body()!=null){
                    ProductImagesModel pModel=response.body();
                    if(pModel.getData()!=null){

                        slider.setSliderAdapter(new ImageAdapter(getContext(),pModel.getData()));
                        slider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        slider.setIndicatorSelectedColor(Color.RED);
                        slider.setIndicatorUnselectedColor(Color.WHITE);

                    }
                }
            }

            @Override
            public void onFailure(Call<ProductImagesModel> call, Throwable t) {

            }
        });
    }

    private void InitAddToCart() {
        if(sharedPreference.getString(getActivity(),"Name").equals("")){
            Intent i=new Intent(getActivity(), LoginActivity.class);
            startActivity(i);

        }
        else{
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            Call<CartResponse>call=service.Cart("1",Id,pId);
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if(response.body()!=null){
                        Toast.makeText(getContext(),"Item is added to the cart",Toast.LENGTH_SHORT).show();
                        count.addcount();



                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    private void InitProductOverview() {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ProductsOverviewModel>call=service.getProductsOverview(pId,Id);
        call.enqueue(new Callback<ProductsOverviewModel>() {
            @Override
            public void onResponse(Call<ProductsOverviewModel> call, Response<ProductsOverviewModel> response) {
                try {
                    System.out.println("response" + response.body().getData());
                    if(response.body().getCode().equals("200")){
                        ProductsOverviewModel pModel=response.body();
                        if (response.body().getData() != null){
                            pName.setText(pModel.getData().get(0).getPName());
                            pUnit.setText(pModel.getData().get(0).getPUnits());
                            pDesc.setText(pModel.getData().get(0).getPDescription());
                            pMrp.setText(pModel.getData().get(0).getPMrp());
                            pDiscount.setText(pModel.getData().get(0).getPDiscount());
                            pOprice.setText(pModel.getData().get(0).getPDiscountPrice());
                            pWstatus=pModel.getData().get(0).getWStatus();
                            wId=pModel.getData().get(0).getWId();
                            pId=pModel.getData().get(0).getPId();
                            product_discount=Integer.parseInt(pModel.getData().get(0).getPDiscount());

                            pdWishlist.setOnClickListener(v -> {
                                if(pdWishlist.isChecked()){
                                    AddWishlist(Id,pId);
                                }
                                else{
                                    DeleteWishlist(wId);
                                }
                            });
                            discount(product_discount);


                            OutOfStock();
                            /*Wstatus*/
                            if(pModel.getData().get(0).getWStatus().equals("1")){
                                pdWishlist.setChecked(false);
                            }
                            else{
                                pdWishlist.setChecked(true);
                            }

                        }
                        else {
                            System.out.println("data response    " + pModel.getCode());
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
            public void onFailure(Call<ProductsOverviewModel> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteWishlist(String wId) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<WishlistDeleteModel>call =service.deleteWishlist(wId);
        call.enqueue(new Callback<WishlistDeleteModel>() {
            @Override
            public void onResponse(Call<WishlistDeleteModel> call, Response<WishlistDeleteModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(),"Item Removed from Wishlist",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<WishlistDeleteModel> call, Throwable t) {

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
        private void AddWishlist(String Id, String pId) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<WishlistAddModel> call = service.addWishlist(Id, pId);
        call.enqueue(new Callback<WishlistAddModel>() {
            @Override
            public void onResponse(Call<WishlistAddModel> call, Response<WishlistAddModel> response) {
                Toast.makeText(getActivity(), "New Item added to Wishlist", LENGTH_SHORT).show();
                if (response.body().getCode().equals("200")) {
                    WishlistAddModel addModel = response.body();


                }
            }

            @Override
            public void onFailure(Call<WishlistAddModel> call, Throwable t) {

            }
        });
    }

    private void OutOfStock() {
    }

    private void discount(Integer product_discount) {
        if(product_discount!=null&&product_discount>0){
            OOS.setVisibility(View.GONE);
            pOffer.setVisibility(View.VISIBLE);
            pMrp.setVisibility(View.VISIBLE);
            pMrp.setPaintFlags(pMrp.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            Toast.makeText(getActivity(),"Discount=0",LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getActivity(),"Discount= "+product_discount,LENGTH_SHORT).show();
            OOS.setVisibility(View.GONE);
            pMrp.setVisibility(View.VISIBLE);
            pDiscount.setVisibility(View.GONE);

            pOffer.setVisibility(View.GONE);


        }

    }
}
