package com.yoho.anaithumfinal.Model;

public class ApplyCouponDatum {
    private String coupon_amount;

    private String tax_percent;

    private String total;

    private String shipping_cost;

    private String tax;

    private String grand_total;

    public String getCoupon_amount ()
    {
        return coupon_amount;
    }

    public void setCoupon_amount (String coupon_amount)
    {
        this.coupon_amount = coupon_amount;
    }

    public String getTax_percent ()
    {
        return tax_percent;
    }

    public void setTax_percent (String tax_percent)
    {
        this.tax_percent = tax_percent;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getShipping_cost ()
    {
        return shipping_cost;
    }

    public void setShipping_cost (String shipping_cost)
    {
        this.shipping_cost = shipping_cost;
    }

    public String getTax ()
    {
        return tax;
    }

    public void setTax (String tax)
    {
        this.tax = tax;
    }

    public String getGrand_total ()
    {
        return grand_total;
    }

    public void setGrand_total (String grand_total)
    {
        this.grand_total = grand_total;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [coupon_amount = "+coupon_amount+", tax_percent = "+tax_percent+", total = "+total+", shipping_cost = "+shipping_cost+", tax = "+tax+", grand_total = "+grand_total+"]";
    }
}
