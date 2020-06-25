package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImageDatum {
    @SerializedName("pi_image")
    @Expose
    private String piImage;

    public String getPiImage() {
        return piImage;
    }

    public void setPiImage(String piImage) {
        this.piImage = piImage;
    }
}
