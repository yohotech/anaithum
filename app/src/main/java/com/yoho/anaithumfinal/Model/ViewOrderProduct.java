package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewOrderProduct {
    private String i_id;

    private String i_gst_price;

    private String i_name;

    private String i_count;

    private String p_id;

    private String m_id;

    private String i_discount_price;

    private String i_total;

    public String getI_id ()
    {
        return i_id;
    }

    public void setI_id (String i_id)
    {
        this.i_id = i_id;
    }

    public String getI_gst_price ()
    {
        return i_gst_price;
    }

    public void setI_gst_price (String i_gst_price)
    {
        this.i_gst_price = i_gst_price;
    }

    public String getI_name ()
    {
        return i_name;
    }

    public void setI_name (String i_name)
    {
        this.i_name = i_name;
    }

    public String getI_count ()
    {
        return i_count;
    }

    public void setI_count (String i_count)
    {
        this.i_count = i_count;
    }

    public String getP_id ()
    {
        return p_id;
    }

    public void setP_id (String p_id)
    {
        this.p_id = p_id;
    }

    public String getM_id ()
    {
        return m_id;
    }

    public void setM_id (String m_id)
    {
        this.m_id = m_id;
    }

    public String getI_discount_price ()
    {
        return i_discount_price;
    }

    public void setI_discount_price (String i_discount_price)
    {
        this.i_discount_price = i_discount_price;
    }

    public String getI_total ()
    {
        return i_total;
    }

    public void setI_total (String i_total)
    {
        this.i_total = i_total;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [i_id = "+i_id+", i_gst_price = "+i_gst_price+", i_name = "+i_name+", i_count = "+i_count+", p_id = "+p_id+", m_id = "+m_id+", i_discount_price = "+i_discount_price+", i_total = "+i_total+"]";
    }
}
