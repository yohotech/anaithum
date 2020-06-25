package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewDetailsDatum {
    private Values values;

    private List<ViewOrderProduct> products;

    public Values getValues ()
    {
        return values;
    }

    public void setValues (Values values)
    {
        this.values = values;
    }

    public List<ViewOrderProduct> getProducts ()
    {
        return products;
    }

    public void setProducts (List<ViewOrderProduct> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [values = "+values+", products = "+products+"]";
    }
}


