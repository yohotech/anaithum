package com.yoho.anaithumfinal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Interface.RefreshCart;
import com.yoho.anaithumfinal.Model.CartListModel;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.Product;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;
import com.yoho.anaithumfinal.ui.Fragment.CartFragment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private SharedPreference sharedPreference = new SharedPreference();
    private RecyclerClickListener recyclerClickListener;
    private Context mCtx;
    private List<Product> pData;
    private Addtocartcount count;
    private RefreshCart refreshCart;
    private String Id = "";
    private int pos;
    private Button btnMinus, btnPlus;


    public CartListAdapter(Context mCtx, List<Product> pData) {
        this.mCtx = mCtx;
        this.pData = pData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pos = position;
        Id = sharedPreference.getString(mCtx, "Id");
        Glide.with(mCtx)
                .load(pData.get(position).getPIconImg())
                .fitCenter()
                .into(holder.pImage);
        holder.pName.setText(pData.get(position).getPName());
        holder.pUnits.setText(pData.get(position).getPUnits());
        holder.pMrp.setText(pData.get(position).getPMrp());
        holder.pDiscount.setText(pData.get(position).getPDiscountPrice());
        holder.pOffer.setText(pData.get(position).getPDiscount());
        holder.caCount.setText(pData.get(position).getCaCount());
        btnPlus.setOnClickListener(v -> recyclerClickListener.OnItemClick("Add", position));

        btnMinus.setOnClickListener(v -> {
            /*recyclerClickListener.OnItemClick("Minus",position);*/
            String status = "0";
            /*String id="1"*/
            ;

            if (position == 0) {
                btnMinus.setEnabled(false);
            } else {
                btnMinus.setEnabled(true);
            }
            if (position > -1) {
                btnMinus.setEnabled(false);
                String product_id = pData.get(position).getPId();
                if (pData.get(position).getCaCount().equals("1")) {
                    dCart(status, Id, product_id);


                } else {
                    dCart(status, Id, product_id);
                }
            }


        });


    }

    @Override
    public int getItemCount() {
        return pData.size();
    }

    public void setrefresh(RefreshCart cartFragment) {
        this.refreshCart = cartFragment;
    }

    public void setonClickListener(RecyclerClickListener cartFragment) {
        this.recyclerClickListener = cartFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImage;
        private TextView pName, pUnits, pMrp, pDiscount, pOffer, caCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.cart_image);
            pName = itemView.findViewById(R.id.cart_title);
            pUnits = itemView.findViewById(R.id.cart_qty);
            pMrp = itemView.findViewById(R.id.cart_price1);
            pDiscount = itemView.findViewById(R.id.cart_discount_price);
            pOffer = itemView.findViewById(R.id.cart_offer);
            caCount = itemView.findViewById(R.id.cart_count);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);

            pImage.setOnClickListener(v -> {
                String pId = pData.get(getAdapterPosition()).getPId();
                String pName = pData.get(getAdapterPosition()).getPName();
                Bundle bundle = new Bundle();
                bundle.putString("pId", pId);
                bundle.putString("cName", pName);
                Navigation.findNavController(itemView).navigate(R.id.nav_products_detail, bundle);

            });

            if (pDiscount != null) {
                pMrp.setPaintFlags(pMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            /*btnPlus.setOnClickListener(v -> {
                String status="1";
                *//*String id="1";*//*
                String product_id=pData.get(getAdapterPosition()).getPId();
                aCart(status,Id,product_id);
            });*/


        }

        /*private void aCart(String status, String Id, String product_id) {
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            Call<CartResponse>call=service.Cart(status,Id,product_id);
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if(response.body().getData()!=null){
                        refreshCart.refresh();
                        CartResponse cartResponse=response.body();
                        Toast.makeText(mCtx,cartResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        *//*pData.get(getAdapterPosition()).setCaCount(String.valueOf(Integer.parseInt(pData.get(getAdapterPosition()).getCaCount())+1));*//*
                        notifyItemChanged(getAdapterPosition(),caCount);




                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    Toast.makeText(mCtx,t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

        }*/

    }

    private void dCart(String status, String Id, String product_id) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse> call = service.Cart(status, Id, product_id);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.body().getData() != null) {

                    refreshCart.refresh();
//                        count.addcount();
                    if (pData != null && pData.size() > 0 && pos > -1) {
                        System.out.println("size  " + pData.size());
                        System.out.println("size   1    " + pos);
                        String caCount = pData.get(pos).getCaCount();
                        String caId = pData.get(pos).getCaId();
                        CartResponse cartResponse = response.body();
                        Toast.makeText(mCtx, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        notifyItemChanged(pos);
                        /*pData.get(getAdapterPosition()).setCaCount(String.valueOf(Integer.parseInt(pData.get(getAdapterPosition()).getCaCount())-1));*/
                        notifyItemChanged(pos, caCount);

                        System.out.println("cartcount   " + caCount);
                        int cartcount = Integer.parseInt(pData.get(pos).getCaCount());
                        System.out.println("cartcounta   " + cartcount);
                        if (cartcount > 1) {
                            System.out.println("cartcountb   " + cartcount);

                        } else {
                            System.out.println("cartcountc   " + cartcount);
                            refreshCart.refresh();
                            deleteCart(caId);
                            pData.remove(pos);
                            notifyItemRemoved(pos);
                        }
                    }
                    btnMinus.setEnabled(true);


                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(mCtx, t.getMessage(), Toast.LENGTH_SHORT).show();
                btnMinus.setEnabled(true);

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

                    Toast.makeText(mCtx, "Product Has Been Deleted Successfully", Toast.LENGTH_SHORT).show();
//                            pData.remove(pos);
                    //  notifyDataSetChanged();
                    System.out.println("size   " + pData.size());
                    refreshCart.refresh();
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

}
