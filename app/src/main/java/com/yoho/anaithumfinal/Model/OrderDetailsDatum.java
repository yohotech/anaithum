package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsDatum {
    @SerializedName("i_id")
    @Expose
    private String iId;
    @SerializedName("m_id")
    @Expose
    private String mId;
    @SerializedName("i_name")
    @Expose
    private String iName;
    @SerializedName("i_count")
    @Expose
    private String iCount;
    @SerializedName("i_discount_price")
    @Expose
    private String iDiscountPrice;
    @SerializedName("i_gst_price")
    @Expose
    private String iGstPrice;
    @SerializedName("i_total")
    @Expose
    private String iTotal;

    public String getIId() {
        return iId;
    }

    public void setIId(String iId) {
        this.iId = iId;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getIName() {
        return iName;
    }

    public void setIName(String iName) {
        this.iName = iName;
    }

    public String getICount() {
        return iCount;
    }

    public void setICount(String iCount) {
        this.iCount = iCount;
    }

    public String getIDiscountPrice() {
        return iDiscountPrice;
    }

    public void setIDiscountPrice(String iDiscountPrice) {
        this.iDiscountPrice = iDiscountPrice;
    }

    public String getIGstPrice() {
        return iGstPrice;
    }

    public void setIGstPrice(String iGstPrice) {
        this.iGstPrice = iGstPrice;
    }

    public String getITotal() {
        return iTotal;
    }

    public void setITotal(String iTotal) {
        this.iTotal = iTotal;
    }
}
