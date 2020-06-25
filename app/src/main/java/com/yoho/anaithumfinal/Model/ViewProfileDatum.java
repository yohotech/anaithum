package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewProfileDatum {

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
    @SerializedName("r_address")
    @Expose
    private String rAddress;
    @SerializedName("r_area")
    @Expose
    private String rArea;
    @SerializedName("r_city")
    @Expose
    private String rCity;
    @SerializedName("r_landmark")
    @Expose
    private String rLandmark;
    @SerializedName("r_state")
    @Expose
    private String rState;
    @SerializedName("r_pincode")
    @Expose
    private String rPincode;
    @SerializedName("r_otp")
    @Expose
    private String rOtp;
    @SerializedName("r_status")
    @Expose
    private String rStatus;
    @SerializedName("r_coupon")
    @Expose
    private String rCoupon;

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

    public String getRAddress() {
        return rAddress;
    }

    public void setRAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public String getRArea() {
        return rArea;
    }

    public void setRArea(String rArea) {
        this.rArea = rArea;
    }

    public String getRCity() {
        return rCity;
    }

    public void setRCity(String rCity) {
        this.rCity = rCity;
    }

    public String getRLandmark() {
        return rLandmark;
    }

    public void setRLandmark(String rLandmark) {
        this.rLandmark = rLandmark;
    }

    public String getRState() {
        return rState;
    }

    public void setRState(String rState) {
        this.rState = rState;
    }

    public String getRPincode() {
        return rPincode;
    }

    public void setRPincode(String rPincode) {
        this.rPincode = rPincode;
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

    public String getRCoupon() {
        return rCoupon;
    }

    public void setRCoupon(String rCoupon) {
        this.rCoupon = rCoupon;
    }
}