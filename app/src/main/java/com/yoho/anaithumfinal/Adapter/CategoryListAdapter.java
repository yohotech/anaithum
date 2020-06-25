package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.yoho.anaithumfinal.Model.CategoryDatum;
import com.yoho.anaithumfinal.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.Holder> {
    private Context context;
    private List<CategoryDatum> categoryList;

    public CategoryListAdapter(Context context, List<CategoryDatum> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.category_list_layout,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txCategory.setText(categoryList.get(position).getCName());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView txCategory;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txCategory=itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cId=categoryList.get(getAdapterPosition()).getCId();
                    String cName=categoryList.get(getAdapterPosition()).getCName();
                    Bundle bundle=new Bundle();
                    bundle.putString("cId",cId);
                    bundle.putString("cName",cName);
                    Navigation.findNavController(itemView).navigate(R.id.nav_products,bundle);

                }
            });
        }
    }
}