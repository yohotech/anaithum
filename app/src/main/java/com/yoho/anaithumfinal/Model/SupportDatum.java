package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportDatum {
    @SerializedName("ss_reason")
    @Expose
    private String ssReason;

    public String getSsReason() {
        return ssReason;
    }

    public void setSsReason(String ssReason) {
        this.ssReason = ssReason;
    }

    public String toString(){
        return ssReason;
    }
}
