package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
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
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Model.SearchDatum;
import com.yoho.anaithumfinal.Model.WishlistAddModel;
import com.yoho.anaithumfinal.Model.WishlistDeleteModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context sCtx;
    private List<SearchDatum> sData;
    private RecyclerClickListener recyclerClickListener;

    public SearchAdapter(Context sCtx, List<SearchDatum> sData) {
        this.sCtx = sCtx;
        this.sData = sData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(sCtx).inflate(R.layout.search_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.pName.setText(sData.get(position).getPName());
        holder.pUnits.setText(sData.get(position).getPUnits());
        holder.pPrice.setText(sData.get(position).getPMrp());
        holder.pDiscountPrice.setText(sData.get(position).getPDiscountPrice());
        holder.pDiscount.setText(sData.get(position).getPDiscount());
        Glide.with(sCtx)
                .load(sData.get(position).getPIconImg())
                .fitCenter()
                .into(holder.pImage);

        holder.pAdd.setOnClickListener(v ->recyclerClickListener.OnItemClick("add",position));
        holder.wishlistStatus=sData.get(position).getWishlist();
        if(holder.wishlistStatus.equals("0")){
            holder.sWishlist.setChecked(false);
        }
        else {
            holder.sWishlist.setChecked(true);
        }


        if(sData.get(position).getPDiscount().equals("0")||sData.get(position).getPDiscount().equals("")){
            holder.pDiscount.setVisibility(View.GONE);
            holder.pDiscountPrice.setVisibility(View.GONE);
        }
        else {
            holder.pPrice.setPaintFlags(holder.pPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if(sData.get(position).getPStock().equals("0")){
            holder.pAdd.setEnabled(false);
            Toast.makeText(sCtx,"Item is out of stock",LENGTH_SHORT).show();
        }





    }

    @Override
    public int getItemCount() {
        return sData.size();
    }

    public void setItemClickListener(RecyclerClickListener searchFragment) {
        this.recyclerClickListener=searchFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImage;
        private TextView pName;
        private TextView pUnits;
        private TextView pPrice;
        private TextView pDiscountPrice;
        private TextView pDiscount;
        private CheckBox sWishlist;
        private Button pAdd;
        private SharedPreference sp=new SharedPreference();
        private String Id=sp.getString(sCtx,"Id");
        private String wishlistStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage=itemView.findViewById(R.id.s_image);
            pName=itemView.findViewById(R.id.s_title);
            pUnits=itemView.findViewById(R.id.s_qty);
            pPrice=itemView.findViewById(R.id.s_price);
            pDiscountPrice=itemView.findViewById(R.id.s_discount_price);
            pDiscount=itemView.findViewById(R.id.s_discount_offer);
            sWishlist=itemView.findViewById(R.id.s_wishlist);
            pAdd=itemView.findViewById(R.id.saddTocart);
            /*InitStatus(wishlistStatus);*/


            pImage.setOnClickListener(v -> {
                String pId=sData.get(getAdapterPosition()).getPId();
                String pName=sData.get(getAdapterPosition()).getPName();
                Bundle bundle=new Bundle();
                bundle.putString("pId",pId);
                bundle.putString("cName",pName);
                Navigation.findNavController(itemView).navigate(R.id.nav_products_detail,bundle);

            });

            sWishlist.setOnClickListener(v -> {

                if(sWishlist.isChecked()){
                    String pId=sData.get(getAdapterPosition()).getPId();
                    ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
                    Call<WishlistAddModel> call = service.addWishlist(Id, pId);
                    call.enqueue(new Callback<WishlistAddModel>() {
                        @Override
                        public void onResponse(Call<WishlistAddModel> call, Response<WishlistAddModel> response) {
                            Toast.makeText(sCtx, "New Item added to Wishlist", LENGTH_SHORT).show();
                            if (response.body().getCode().equals("200")) {
                                WishlistAddModel addModel = response.body();




                            }
                        }

                        @Override
                        public void onFailure(Call<WishlistAddModel> call, Throwable t) {

                        }
                    });
                }
                else{
                    String wId=sData.get(getAdapterPosition()).getWId();
                    ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
                    Call<WishlistDeleteModel>call =service.deleteWishlist(wId);
                    call.enqueue(new Callback<WishlistDeleteModel>() {
                        @Override
                        public void onResponse(Call<WishlistDeleteModel> call, Response<WishlistDeleteModel> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(sCtx,"Item Removed from Wishlist",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<WishlistDeleteModel> call, Throwable t) {

                        }
                    });
                }
            });

        }

        /*private void InitStatus(String wishlistStatus) {
            if(wishlistStatus.equals("0")){
                sWishlist.setChecked(true);
            }
            else {
                sWishlist.setChecked(false);
            }
        }*/


    }
}
