package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yoho.anaithumfinal.Model.BannerDatum;
import com.yoho.anaithumfinal.R;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {
    Context context;
    List<BannerDatum> data;

    public ImageSliderAdapter(Context context, List<BannerDatum> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        /*viewHolder.SliderImageView.setImageResource(imageSliderPojoList.get(position).getImages());*/
        Glide.with(context)
                .load(data.get(position).getSImage())
                .optionalFitCenter()
                .into(viewHolder.SliderImageView);

    }

    @Override
    public int getCount() {
        return data.size();
    }
}
class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    ImageView SliderImageView;
    public SliderViewHolder(View itemView) {
        super(itemView);
        SliderImageView=itemView.findViewById(R.id.imgView);

    }
}
