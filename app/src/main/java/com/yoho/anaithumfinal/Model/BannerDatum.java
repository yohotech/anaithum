package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDatum {
    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("s_name")
    @Expose
    private String sName;
    @SerializedName("s_image")
    @Expose
    private String sImage;
    @SerializedName("s_status")
    @Expose
    private String sStatus;

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getSImage() {
        return sImage;
    }

    public void setSImage(String sImage) {
        this.sImage = sImage;
    }

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }
}