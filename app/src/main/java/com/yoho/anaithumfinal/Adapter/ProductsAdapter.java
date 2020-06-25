package com.yoho.anaithumfinal.Adapter;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.MainActivity;
import com.yoho.anaithumfinal.Model.ProductsDatum;
import com.yoho.anaithumfinal.Model.ProductsModel;
import com.yoho.anaithumfinal.Model.WishlistAddModel;
import com.yoho.anaithumfinal.Model.WishlistDeleteModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.SharedPreference;
import com.yoho.anaithumfinal.ui.Fragment.ProductsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{
    private Context mCtx;
    private List<ProductsDatum> productsData;
    private RecyclerClickListener recyclerClickListener;
    private SharedPreference sharedPreference=new SharedPreference();


    public ProductsAdapter(Context mCtx, List<ProductsDatum> productsData) {
        this.mCtx = mCtx;
        this.productsData = productsData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.products_layout1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pName.setText(productsData.get(position).getPName());
        holder.pUnits.setText(productsData.get(position).getPUnits());
        holder.pPrice.setText(productsData.get(position).getPMrp());
        holder.pDiscountPrice.setText(productsData.get(position).getPDiscountPrice());
        holder.pDiscount.setText(productsData.get(position).getPDiscount());
        Glide.with(mCtx)
                .load(productsData.get(position).getPIconImg())
                .fitCenter()
                .into(holder.pImage);
        String wStatus=productsData.get(position).getWishlist();


        if(wStatus.equals("0")){
            holder.pWishlist.setChecked(false);
        }
        else {
            holder.pWishlist.setChecked(true);
        }

        holder.pAdd.setOnClickListener(v ->
                recyclerClickListener.OnItemClick("add",position));
    }

    @Override
    public int getItemCount() {
        return productsData.size();
    }

    public void setonitemclicklistener(RecyclerClickListener productsModelCallback) {
        this.recyclerClickListener=productsModelCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pImage;
        TextView pName;
        TextView pUnits;
        TextView pPrice;
        TextView pDiscountPrice;
        TextView pDiscount;
        private Button pAdd;
        private CheckBox pWishlist;
        private String Id=sharedPreference.getString(mCtx,"Id");
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage=itemView.findViewById(R.id.product_image);
            pName=itemView.findViewById(R.id.product_title);
            pUnits=itemView.findViewById(R.id.product_qty);
            pPrice=itemView.findViewById(R.id.product_price);
            pDiscountPrice=itemView.findViewById(R.id.product_discount_price);
            pDiscount=itemView.findViewById(R.id.product_discount_offer);
            pWishlist=itemView.findViewById(R.id.product_wishlist);
            pAdd=itemView.findViewById(R.id.addTocart);



            if(pDiscount!=null){
                pPrice.setPaintFlags(pPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }

            pImage.setOnClickListener(v -> {
                String pId=productsData.get(getAdapterPosition()).getPId();
                String pName=productsData.get(getAdapterPosition()).getPName();
                Bundle bundle=new Bundle();
                bundle.putString("pId",pId);
                bundle.putString("cName",pName);
                Navigation.findNavController(itemView).navigate(R.id.nav_products_detail,bundle);

            });

            pWishlist.setOnClickListener(v -> {
                    String pId = productsData.get(getAdapterPosition()).getPId();
                    if (pWishlist.isChecked()) {
                        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
                        Call<WishlistAddModel> call = service.addWishlist(Id, pId);
                        call.enqueue(new Callback<WishlistAddModel>() {
                            @Override
                            public void onResponse(Call<WishlistAddModel> call, Response<WishlistAddModel> response) {
                                Toast.makeText(mCtx, "New Item added to Wishlist", LENGTH_SHORT).show();
                                if (response.body().getCode().equals("200")) {
                                    WishlistAddModel addModel = response.body();


                                }
                            }

                            @Override
                            public void onFailure(Call<WishlistAddModel> call, Throwable t) {

                            }
                        });
                    } else {
                        String wId = (String) productsData.get(getAdapterPosition()).getWId();
                        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
                        Call<WishlistDeleteModel> call = service.deleteWishlist(wId);
                        call.enqueue(new Callback<WishlistDeleteModel>() {
                            @Override
                            public void onResponse(Call<WishlistDeleteModel> call, Response<WishlistDeleteModel> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(mCtx, "Item Removed from Wishlist", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<WishlistDeleteModel> call, Throwable t) {

                            }
                        });

                    }

            });
        }

    }
}
