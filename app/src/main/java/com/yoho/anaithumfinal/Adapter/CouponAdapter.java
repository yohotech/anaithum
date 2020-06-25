package com.yoho.anaithumfinal.Adapter;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Model.CouponDatum;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.ui.Fragment.CouponFragment;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private Context context;
    private List<CouponDatum> cData;
    private RecyclerClickListener recyclerClickListener;

    public CouponAdapter(Context context, List<CouponDatum> cData) {
        this.context = context;
        this.cData = cData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.coupon_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cName.setText(cData.get(position).getCoName());
        holder.cCode.setText(cData.get(position).getCoCode());
        holder.cValidity.setText("Valid till: "+cData.get(position).getCoTo());
        holder.cCode.setOnClickListener(v -> {
            recyclerClickListener.OnItemClick("copy",position);
        });


    }

    @Override
    public int getItemCount() {
        return cData.size();
    }

    public void setOnClickListener(RecyclerClickListener couponFragment) {
        this.recyclerClickListener=couponFragment;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cName,cCode,cValidity;
        private String copy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cName=itemView.findViewById(R.id.coupon_name);
            cCode=itemView.findViewById(R.id.coupon_code);
            cValidity=itemView.findViewById(R.id.coupon_validity);
            copyCoupon(getAdapterPosition());
        }

        private void copyCoupon(int adapterPosition) {


        }
    }
}
