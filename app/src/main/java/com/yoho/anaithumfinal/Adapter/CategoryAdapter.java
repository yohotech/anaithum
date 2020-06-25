package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Model.CategoryDatum;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyviewHolder> {
    private Context context;
    private ArrayList<CategoryDatum> mCategory;

    public CategoryAdapter(Context context, ArrayList<CategoryDatum> mCategory) {
        this.context = context;
        this.mCategory = mCategory;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.clayout,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.tv_name.setText(mCategory.get(position).getCName());
        /*holder.img_category.setImageResource(mCategory.get(position).getThumbnail());*/
        Glide
                .with(context)
                .load(mCategory.get(position).getCImage())
                .fitCenter()
                .into(holder.img_category);

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private ImageView img_category;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.txt_name);
            img_category=itemView.findViewById(R.id.img_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cId=mCategory.get(getAdapterPosition()).getCId();
                    String cName=mCategory.get(getAdapterPosition()).getCName();
                    Bundle bundle=new Bundle();
                    bundle.putString("cId",cId);
                    bundle.putString("cName",cName);
                    Navigation.findNavController(itemView).navigate(R.id.nav_products,bundle);

                }
            });
        }


    }

}

