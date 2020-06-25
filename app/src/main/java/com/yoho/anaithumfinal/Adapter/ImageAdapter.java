package com.yoho.anaithumfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yoho.anaithumfinal.Model.ProductImageDatum;
import com.yoho.anaithumfinal.ProductImagePojo;
import com.yoho.anaithumfinal.R;

import java.util.List;

public class ImageAdapter extends SliderViewAdapter<SliderAdapterVH>{
    Context context;
    List<ProductImageDatum> productImagePojos;

    public ImageAdapter() {
    }

    public ImageAdapter(Context context, List<ProductImageDatum> productImagePojos) {
        this.context = context;
        this.productImagePojos = productImagePojos;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_layout,parent,false);
        return new SliderAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Glide.with(context)
                .load(productImagePojos.get(position).getPiImage())
                .fitCenter()
                .into(viewHolder.imgSlider);
    }

    @Override
    public int getCount() {
        return productImagePojos.size();
    }
}
class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
    ImageView imgSlider;


    public SliderAdapterVH(View itemView) {
        super(itemView);
        imgSlider=itemView.findViewById(R.id.pImage_slide_view);
    }
}