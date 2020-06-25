package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("ca_id")
    @Expose
    private String caId;
    @SerializedName("r_id")
    @Expose
    private String rId;
    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("ca_count")
    @Expose
    private String caCount;
    @SerializedName("ca_status")
    @Expose
    private String caStatus;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_description")
    @Expose
    private String pDescription;
    @SerializedName("p_icon_img")
    @Expose
    private String pIconImg;
    @SerializedName("p_units")
    @Expose
    private String pUnits;
    @SerializedName("p_stock")
    @Expose
    private String pStock;
    @SerializedName("p_mrp")
    @Expose
    private String pMrp;
    @SerializedName("p_discount")
    @Expose
    private String pDiscount;
    @SerializedName("p_discount_price")
    @Expose
    private String pDiscountPrice;
    @SerializedName("p_gst")
    @Expose
    private String pGst;
    @SerializedName("p_orginal_price")
    @Expose
    private String pOrginalPrice;
    @SerializedName("p_status")
    @Expose
    private String pStatus;
    @SerializedName("p_viewstatus")
    @Expose
    private String pViewstatus;

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public String getRId() {
        return rId;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getCaCount() {
        return caCount;
    }

    public void setCaCount(String caCount) {
        this.caCount = caCount;
    }

    public String getCaStatus() {
        return caStatus;
    }

    public void setCaStatus(String caStatus) {
        this.caStatus = caStatus;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPDescription() {
        return pDescription;
    }

    public void setPDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getPIconImg() {
        return pIconImg;
    }

    public void setPIconImg(String pIconImg) {
        this.pIconImg = pIconImg;
    }

    public String getPUnits() {
        return pUnits;
    }

    public void setPUnits(String pUnits) {
        this.pUnits = pUnits;
    }

    public String getPStock() {
        return pStock;
    }

    public void setPStock(String pStock) {
        this.pStock = pStock;
    }

    public String getPMrp() {
        return pMrp;
    }

    public void setPMrp(String pMrp) {
        this.pMrp = pMrp;
    }

    public String getPDiscount() {
        return pDiscount;
    }

    public void setPDiscount(String pDiscount) {
        this.pDiscount = pDiscount;
    }

    public String getPDiscountPrice() {
        return pDiscountPrice;
    }

    public void setPDiscountPrice(String pDiscountPrice) {
        this.pDiscountPrice = pDiscountPrice;
    }

    public String getPGst() {
        return pGst;
    }

    public void setPGst(String pGst) {
        this.pGst = pGst;
    }

    public String getPOrginalPrice() {
        return pOrginalPrice;
    }

    public void setPOrginalPrice(String pOrginalPrice) {
        this.pOrginalPrice = pOrginalPrice;
    }

    public String getPStatus() {
        return pStatus;
    }

    public void setPStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getPViewstatus() {
        return pViewstatus;
    }

    public void setPViewstatus(String pViewstatus) {
        this.pViewstatus = pViewstatus;
    }

}
