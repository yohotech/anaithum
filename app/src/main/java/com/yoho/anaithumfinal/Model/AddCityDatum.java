package com.yoho.anaithumfinal.Model;

public class AddCityDatum {
    private String d_city;

    public String getD_city ()
    {
        return d_city;
    }

    public void setD_city (String d_city)
    {
        this.d_city = d_city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [d_city = "+d_city+"]";
    }
}
