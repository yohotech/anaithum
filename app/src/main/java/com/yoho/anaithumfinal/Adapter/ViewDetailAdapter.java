package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoho.anaithumfinal.Model.ViewOrderProduct;
import com.yoho.anaithumfinal.Model.ViewProfileDatum;
import com.yoho.anaithumfinal.R;

import java.util.List;

public class ViewDetailAdapter extends RecyclerView.Adapter<ViewDetailAdapter.ViewHolder> {
    private Context context;
    private List<ViewOrderProduct>vData;

    public ViewDetailAdapter(Context context, List<ViewOrderProduct> vData) {
        this.context = context;
        this.vData = vData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_details_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.oName.setText(vData.get(position).getI_name());
        holder.oUnits.setText(vData.get(position).getI_count());
        holder.oDprice.setText("₹ "+vData.get(position).getI_discount_price());
        holder.oTax.setText("₹ "+vData.get(position).getI_gst_price());
        holder.oSubTotal.setText("₹ "+vData.get(position).getI_total());

    }

    @Override
    public int getItemCount() {
        return vData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView oName,oUnits,oDprice,oTax,oSubTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            oName=itemView.findViewById(R.id.header_pname);
            oUnits=itemView.findViewById(R.id.pUnits);
            oDprice=itemView.findViewById(R.id.pDprice);
            oTax=itemView.findViewById(R.id.pTax);
            oSubTotal=itemView.findViewById(R.id.pSubTotal);

        }
    }
}
