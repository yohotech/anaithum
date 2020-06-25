package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckResponseDatum {
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("shipping_cost")
    @Expose
    private String shippingCost;
    @SerializedName("tax")
    @Expose
    private Float tax;
    @SerializedName("tax_percent")
    @Expose
    private String taxPercent;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public String getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }
}
