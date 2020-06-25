package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpDatum {
    @SerializedName("r_id")
    @Expose
    private String rId;
    @SerializedName("r_mobile")
    @Expose
    private String rMobile;
    @SerializedName("r_name")
    @Expose
    private String rName;
    @SerializedName("r_email")
    @Expose
    private String rEmail;
    @SerializedName("r_otp")
    @Expose
    private String rOtp;
    @SerializedName("r_status")
    @Expose
    private String rStatus;

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public String getRMobile() {
        return rMobile;
    }

    public void setRMobile(String rMobile) {
        this.rMobile = rMobile;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getREmail() {
        return rEmail;
    }

    public void setREmail(String rEmail) {
        this.rEmail = rEmail;
    }

    public String getROtp() {
        return rOtp;
    }

    public void setROtp(String rOtp) {
        this.rOtp = rOtp;
    }

    public String getRStatus() {
        return rStatus;
    }

    public void setRStatus(String rStatus) {
        this.rStatus = rStatus;
    }
}
