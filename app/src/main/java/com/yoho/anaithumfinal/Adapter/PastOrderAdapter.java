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
import com.yoho.anaithumfinal.Model.PastOrderDatum;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.ui.Fragment.OrdersFragment;

import java.util.List;

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.ViewHolder> {
    private Context context;
    private List<PastOrderDatum>pastOrderData;
    private RecyclerClickListener recyclerClickListener;


    public PastOrderAdapter(Context context, List<PastOrderDatum> pastOrderData) {
        this.context = context;
        this.pastOrderData = pastOrderData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.past_orders_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.PoDate.setText(pastOrderData.get(position).getMOrderDate());
        holder.PoTime.setText(pastOrderData.get(position).getMOrderTime());
        holder.PoId.setText(pastOrderData.get(position).getMOrderId());
        holder.PoAmount.setText("₹"+pastOrderData.get(position).getMGrandtotal());
        holder.btnView.setOnClickListener(v -> recyclerClickListener.OnItemClick("past",position));





    }

    @Override
    public int getItemCount() {
        return pastOrderData.size();
    }

    public void setOnclickListener(RecyclerClickListener ordersFragment) {
        recyclerClickListener=ordersFragment;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView PoDate,PoId,PoTime,PoAmount,PoStatus;
        private Button btnView,btnReturn;
        private String pStatus,mId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PoDate=itemView.findViewById(R.id.order_date1);
            PoId=itemView.findViewById(R.id.PoId);
            PoTime=itemView.findViewById(R.id.PoTime);
            PoAmount=itemView.findViewById(R.id.PoAmount);
            PoStatus=itemView.findViewById(R.id.PoStatus);
            btnView=itemView.findViewById(R.id.btnViewPastdetails);
            btnReturn=itemView.findViewById(R.id.btnReturn);


        }
    }
}
