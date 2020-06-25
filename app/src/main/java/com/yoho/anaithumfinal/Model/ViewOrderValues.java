package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewOrderValues {
    @SerializedName("m_id")
    @Expose
    private String mId;
    @SerializedName("r_id")
    @Expose
    private String rId;
    @SerializedName("m_order_id")
    @Expose
    private String mOrderId;
    @SerializedName("m_address")
    @Expose
    private String mAddress;
    @SerializedName("m_deliverymode")
    @Expose
    private String mDeliverymode;
    @SerializedName("m_total")
    @Expose
    private String mTotal;
    @SerializedName("m_shipping_cost")
    @Expose
    private String mShippingCost;
    @SerializedName("m_tax")
    @Expose
    private String mTax;
    @SerializedName("m_ship_total")
    @Expose
    private String mShipTotal;
    @SerializedName("m_coupon")
    @Expose
    private String mCoupon;
    @SerializedName("m_grandtotal")
    @Expose
    private String mGrandtotal;
    @SerializedName("m_paymentmethod")
    @Expose
    private String mPaymentmethod;
    @SerializedName("m_order_date")
    @Expose
    private String mOrderDate;
    @SerializedName("m_order_time")
    @Expose
    private String mOrderTime;
    @SerializedName("m_delivery_status")
    @Expose
    private String mDeliveryStatus;

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public String getMOrderId() {
        return mOrderId;
    }

    public void setMOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getMAddress() {
        return mAddress;
    }

    public void setMAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getMDeliverymode() {
        return mDeliverymode;
    }

    public void setMDeliverymode(String mDeliverymode) {
        this.mDeliverymode = mDeliverymode;
    }

    public String getMTotal() {
        return mTotal;
    }

    public void setMTotal(String mTotal) {
        this.mTotal = mTotal;
    }

    public String getMShippingCost() {
        return mShippingCost;
    }

    public void setMShippingCost(String mShippingCost) {
        this.mShippingCost = mShippingCost;
    }

    public String getMTax() {
        return mTax;
    }

    public void setMTax(String mTax) {
        this.mTax = mTax;
    }

    public String getMShipTotal() {
        return mShipTotal;
    }

    public void setMShipTotal(String mShipTotal) {
        this.mShipTotal = mShipTotal;
    }

    public String getMCoupon() {
        return mCoupon;
    }

    public void setMCoupon(String mCoupon) {
        this.mCoupon = mCoupon;
    }

    public String getMGrandtotal() {
        return mGrandtotal;
    }

    public void setMGrandtotal(String mGrandtotal) {
        this.mGrandtotal = mGrandtotal;
    }

    public String getMPaymentmethod() {
        return mPaymentmethod;
    }

    public void setMPaymentmethod(String mPaymentmethod) {
        this.mPaymentmethod = mPaymentmethod;
    }

    public String getMOrderDate() {
        return mOrderDate;
    }

    public void setMOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }

    public String getMOrderTime() {
        return mOrderTime;
    }

    public void setMOrderTime(String mOrderTime) {
        this.mOrderTime = mOrderTime;
    }

    public String getMDeliveryStatus() {
        return mDeliveryStatus;
    }

    public void setMDeliveryStatus(String mDeliveryStatus) {
        this.mDeliveryStatus = mDeliveryStatus;
    }

}
