package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.WishlistDatum;
import com.yoho.anaithumfinal.Model.WishlistDeleteModel;

import com.yoho.anaithumfinal.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    private Context context;
    private List<WishlistDatum> wData;

    public WishlistAdapter(Context context, List<WishlistDatum> wData) {
        this.context = context;
        this.wData = wData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.wishlist_layout,parent,false);
        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(wData.get(position).getPIconImg())
                .fitCenter()
                .into(holder.wImg);

        holder.wName.setText(wData.get(position).getPName());
        holder.wUnits.setText(wData.get(position).getPUnits());
        holder.wMrp.setText(wData.get(position).getPMrp());
        holder.wDprice.setText(wData.get(position).getPDiscountPrice());
        holder.wOffer.setText(wData.get(position).getPDiscount());
        holder.w_id=wData.get(position).getWId();




    }

    @Override
    public int getItemCount() {
        return wData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView wImg;
        private TextView wName,wUnits,wMrp,wDprice,wOffer,wOOS;
        private ImageView imgDelete;
        private String w_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            wImg=itemView.findViewById(R.id.wishlist_image);
            wName=itemView.findViewById(R.id.wishlist_title);
            wUnits=itemView.findViewById(R.id.wishlist_qty);
            wMrp=itemView.findViewById(R.id.wishlist_price1);
            wDprice=itemView.findViewById(R.id.wishlist_discount_price);
            wOffer=itemView.findViewById(R.id.wishlist_offer);
            wOOS=itemView.findViewById(R.id.wishlist_OOS);
            imgDelete=itemView.findViewById(R.id.wishlist_delete);

            wImg.setOnClickListener(v -> {
                String pId=wData.get(getAdapterPosition()).getPId();
                String pName=wData.get(getAdapterPosition()).getPName();
                Bundle bundle=new Bundle();
                bundle.putString("pId",pId);
                bundle.putString("cName",pName);
                Navigation.findNavController(itemView).navigate(R.id.nav_products_detail,bundle);

            });
            imgDelete.setOnClickListener(v -> {

                DeleteWishlist();
            });
        }

        private void DeleteWishlist() {
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            Call<WishlistDeleteModel>call =service.deleteWishlist(w_id);
            call.enqueue(new Callback<WishlistDeleteModel>() {
                @Override
                public void onResponse(Call<WishlistDeleteModel> call, Response<WishlistDeleteModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context,"Item Removed from Wishlist",Toast.LENGTH_SHORT).show();
                        wData.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }

                }

                @Override
                public void onFailure(Call<WishlistDeleteModel> call, Throwable t) {

                }
            });

        }
    }
}
