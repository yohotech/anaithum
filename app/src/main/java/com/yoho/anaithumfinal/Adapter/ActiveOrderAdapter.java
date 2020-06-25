package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Model.ActiveOrderDatum;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.ui.Fragment.OrdersFragment;

import java.util.List;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.ViewHolder> {
    private Context context;
    private List<ActiveOrderDatum> actData;
    private RecyclerClickListener recyclerClickListener;

    public ActiveOrderAdapter(Context context, List<ActiveOrderDatum> actData) {
        this.context = context;
        this.actData = actData;
    }

    @NonNull
    @Override
    public ActiveOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.active_order_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveOrderAdapter.ViewHolder holder, int position) {
        holder.AoId.setText(actData.get(position).getMOrderId());
        holder.AoTime.setText(actData.get(position).getMOrderTime());
        holder.Amount.setText("₹"+actData.get(position).getMGrandtotal());
        holder.AoDate.setText(actData.get(position).getMOrderDate());
        holder.Track.setOnClickListener(v -> recyclerClickListener.OnItemClick("track",position));
        holder.View.setOnClickListener(v -> recyclerClickListener.OnItemClick("active",position));


    }

    @Override
    public int getItemCount() {
        return actData.size();
    }

    public void setOnClickListener(RecyclerClickListener ordersFragment) {
        this.recyclerClickListener=ordersFragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView AoDate,AoTime,AoId,Amount;
        private Button View,Track;
        private String oId,mId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AoDate=itemView.findViewById(R.id.order_date1);
            AoTime=itemView.findViewById(R.id.order_time);
            AoId=itemView.findViewById(R.id.oId);
            Amount=itemView.findViewById(R.id.aoAMount);
            View=itemView.findViewById(R.id.btnViewdetails);
            Track=itemView.findViewById(R.id.btn_trackOrder);



        }
    }
}
