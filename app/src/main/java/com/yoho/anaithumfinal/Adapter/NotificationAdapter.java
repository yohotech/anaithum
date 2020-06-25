package com.yoho.anaithumfinal.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yoho.anaithumfinal.Api.NotificationDatum;
import com.yoho.anaithumfinal.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context nCtx;
    private List<NotificationDatum> nData;

    public NotificationAdapter(Context nCtx, List<NotificationDatum> nData) {
        this.nCtx = nCtx;
        this.nData = nData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(nCtx).inflate(R.layout.notification_layout,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nText.setText(nData.get(position).getN_text());
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView notificationCard;
        private TextView nText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationCard=itemView.findViewById(R.id.notification_card);
            nText=itemView.findViewById(R.id.notification_text);
        }
    }
}
