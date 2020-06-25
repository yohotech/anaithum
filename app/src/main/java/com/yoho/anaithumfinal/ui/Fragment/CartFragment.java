package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Adapter.CartListAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Interface.RefreshCart;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.MainActivity;
import com.yoho.anaithumfinal.Model.CartListData;
import com.yoho.anaithumfinal.Model.CartListModel;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.Product;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements RefreshCart, RecyclerClickListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView rCart;
    private CartListAdapter caAdapter;
    private String checkoutTotal, Id;
    private Integer Total;
    private TextView total, grandTotal, checkTotal, Cart_none;
    private CardView summary, checkout;
    private LinearLayout lCheckout;
    private List<Product> pData;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    Addtocartcount count;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
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
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), "Cart", null);
        progressDialog.startProgress();
        Id = sharedPreference.getString(getActivity(), "Id");
        /*try {
            if(sharedPreference.getString(getActivity(),"Name").equals("")){
                Intent i=new Intent(getActivity(),LoginActivity.class);
                *//*i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);*//*
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        rCart = view.findViewById(R.id.rCart);
        count.addcount();
        InitCart(Id);
        total = view.findViewById(R.id.txt_total_amount_cart);
        grandTotal = view.findViewById(R.id.txt_grand_total_amount_cart);
        checkTotal = view.findViewById(R.id.total);
        summary = view.findViewById(R.id.card_view1);
        lCheckout = view.findViewById(R.id.lCheckout);
        Cart_none = view.findViewById(R.id.Cart_none);
        checkout = view.findViewById(R.id.checkout_button);
        checkout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("check_total", checkoutTotal);
            sharedPreference.putString(getActivity(), "Checktotal", checkoutTotal);
            /*Navigation.findNavController(view).navigate(R.id.nav_checkout,bundle);*/
            Navigation.findNavController(view).navigate(R.id.nav_blank, bundle);
        });

        return view;
    }

    private void InitCart(String Id) {
        pData = new ArrayList<>();

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<CartListModel> call = service.getCartList(Id);

        call.enqueue(new Callback<CartListModel>() {
            @Override
            public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                progressDialog.DismissDialog();
                System.out.println("value " + response.body());
                if (response.body() != null) {
                    System.out.println("value " + response.body().getCode());
                    Cart_none.setVisibility(View.GONE);
                    rCart.setVisibility(View.VISIBLE);
                    summary.setVisibility(View.VISIBLE);
                    lCheckout.setVisibility(View.VISIBLE);
                    CartListModel cartListModel = response.body();
                    if (cartListModel.getData() != null) {
                        pData = cartListModel.getData().getProducts();
                        setAdapter(pData);
                        count.addcount();

                        Total = cartListModel.getData().getTotal();
                        total.setText(String.valueOf(Total));
                        grandTotal.setText(String.valueOf(Total));
                        checkTotal.setText(String.valueOf(Total));
                        checkoutTotal = String.valueOf(cartListModel.getData().getTotal());
                        System.out.println("onSuccess: " + checkoutTotal);


                    } else {
                        rCart.setVisibility(View.GONE);
                        pData.clear();
                        pData = new ArrayList<>();
                        summary.setVisibility(View.GONE);
                        lCheckout.setVisibility(View.GONE);
                        Cart_none.setVisibility(View.VISIBLE);
                        Total = 0;
                        total.setText(String.valueOf(Total));
                        grandTotal.setText(String.valueOf(Total));
                        checkTotal.setText(String.valueOf(Total));

                    }
                } else {
                    rCart.setVisibility(View.GONE);
                    pData.clear();
                    pData = new ArrayList<>();
                    summary.setVisibility(View.GONE);
                    lCheckout.setVisibility(View.GONE);
                    Cart_none.setVisibility(View.VISIBLE);
                    Total = 0;
                    total.setText(String.valueOf(Total));
                    grandTotal.setText(String.valueOf(Total));
                    checkTotal.setText(String.valueOf(Total));
                }

            }

            @Override
            public void onFailure(Call<CartListModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("Error msg    " + t.getMessage());
                pData.clear();
                pData = new ArrayList<>();
                rCart.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);
                lCheckout.setVisibility(View.GONE);
                Cart_none.setVisibility(View.VISIBLE);
                Total = 0;
                total.setText(String.valueOf(Total));
                grandTotal.setText(String.valueOf(Total));
                checkTotal.setText(String.valueOf(Total));

            }
        });
    }

    private void setAdapter(List<Product> pData) {
        rCart.setHasFixedSize(true);
        rCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        caAdapter = new CartListAdapter(getActivity(), pData);
        caAdapter.notifyDataSetChanged();
        caAdapter.setrefresh(this);
        rCart.setAdapter(caAdapter);
        caAdapter.setonClickListener(this);
    }


    @Override
    public void refresh() {
        InitCart(Id);
    }

    @Override
    public void OnItemClick(String v, int position) {
        if (v.equals("Add")) {
            progressDialog.startProgress();
            String status = "1";
            if (pData != null && pData.size() > 0) {
                System.out.println("size   " + pData.size());
                System.out.println("size   " + pData.get(position).getPId());

                if (pData.get(position).getPId() != null) {
                    String productId = pData.get(position).getPId();
                    aCart(status, Id, productId, position);
                } else {
                    aCart(status, Id, "", position);
                }
            }
        } /*else if (v.equals("Minus")) {
            progressDialog.startProgress();
            String status = "0";


            if (pData.get(position).getPId() != null && pData.get(position).getCaId() != null && pData.size()>0) {
                String product_id = pData.get(position).getPId();
                if (pData.get(position).getCaCount().equals("1")) {
                    dCart(status, Id, product_id, position);


                } else {
                    dCart(status, Id, product_id, position);
                }
            }

        }*/

    }

    private void dCart(String status, String id, String product_id, int position) {
        int pos = position;
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse> call = service.Cart(status, Id, product_id);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.body().getData() != null) {
                    Handler handler = new Handler();
                    try {
                        handler.postDelayed(() -> {
                            progressDialog.DismissDialog();
                            InitCart(Id);
//                        count.addcount();
                            if (pData != null && pData.size() > 1) {
                                String caCount = pData.get(pos).getCaCount();
                                String caId = pData.get(pos).getCaId();
                                caAdapter.notifyItemChanged(pos, caCount);
                                System.out.println("deeb   " + caCount);
                                System.out.println("deeb   " + caId);
                                if (caId != null && caCount.equals("0")) {
                                    deleteCart(caId);
                                    pData.remove(pos);
                                    caAdapter.notifyItemRemoved(pos);
                                }


                            }


                        }, 0500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void deleteCart(String caId) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.deleteCart(caId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("cartcountb   " + response.body());

                if (response.body() != null) {
                       /* CartResponse model=response.body();
                        if(model.getData()!=null){*/

                    Toast.makeText(getActivity(), "Product Has Been Deleted Successfully", Toast.LENGTH_SHORT).show();
//                            pData.remove(pos);
                    //  notifyDataSetChanged();
                    System.out.println("size   " + pData.size());
                    InitCart(Id);
                    //  count.addcount();
                    // notifyItemRemoved(pos);


                    // }

                }/*else {
                        pData.remove(pos);
                        notifyDataSetChanged();
                        System.out.println("size   "+pData.size());
                        refreshCart.refresh();
                    }*/


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("cartcountb   " + t.getMessage());


            }
        });
    }

    private void aCart(String status, String id, String productId, int position) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse> call = service.Cart(status, Id, productId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.body().getData() != null) {

                    progressDialog.DismissDialog();
                    InitCart(Id);
                    CartResponse cartResponse = response.body();

                    /*pData.get(getAdapterPosition()).setCaCount(String.valueOf(Integer.parseInt(pData.get(getAdapterPosition()).getCaCount())+1));*/
                    caAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                progressDialog.DismissDialog();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
