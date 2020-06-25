package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponDatum {

    @SerializedName("co_id")
    @Expose
    private String coId;
    @SerializedName("co_name")
    @Expose
    private String coName;
    @SerializedName("co_code")
    @Expose
    private String coCode;
    @SerializedName("co_from")
    @Expose
    private String coFrom;
    @SerializedName("co_to")
    @Expose
    private String coTo;
    @SerializedName("co_value")
    @Expose
    private String coValue;
    @SerializedName("co_minimum_value")
    @Expose
    private String coMinimumValue;
    @SerializedName("co_user_restriction")
    @Expose
    private String coUserRestriction;
    @SerializedName("co_user_rest_count")
    @Expose
    private String coUserRestCount;
    @SerializedName("co_status")
    @Expose
    private String coStatus;
    @SerializedName("cu_id")
    @Expose
    private String cuId;
    @SerializedName("cu_user")
    @Expose
    private String cuUser;
    @SerializedName("user_values")
    @Expose
    private String userValues;
    @SerializedName("expiry")
    @Expose
    private String expiry;
    @SerializedName("restriction")
    @Expose
    private String restriction;

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getCoFrom() {
        return coFrom;
    }

    public void setCoFrom(String coFrom) {
        this.coFrom = coFrom;
    }

    public String getCoTo() {
        return coTo;
    }

    public void setCoTo(String coTo) {
        this.coTo = coTo;
    }

    public String getCoValue() {
        return coValue;
    }

    public void setCoValue(String coValue) {
        this.coValue = coValue;
    }

    public String getCoMinimumValue() {
        return coMinimumValue;
    }

    public void setCoMinimumValue(String coMinimumValue) {
        this.coMinimumValue = coMinimumValue;
    }

    public String getCoUserRestriction() {
        return coUserRestriction;
    }

    public void setCoUserRestriction(String coUserRestriction) {
        this.coUserRestriction = coUserRestriction;
    }

    public String getCoUserRestCount() {
        return coUserRestCount;
    }

    public void setCoUserRestCount(String coUserRestCount) {
        this.coUserRestCount = coUserRestCount;
    }

    public String getCoStatus() {
        return coStatus;
    }

    public void setCoStatus(String coStatus) {
        this.coStatus = coStatus;
    }

    public String getCuId() {
        return cuId;
    }

    public void setCuId(String cuId) {
        this.cuId = cuId;
    }

    public String getCuUser() {
        return cuUser;
    }

    public void setCuUser(String cuUser) {
        this.cuUser = cuUser;
    }

    public String getUserValues() {
        return userValues;
    }

    public void setUserValues(String userValues) {
        this.userValues = userValues;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }
}
