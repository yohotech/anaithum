package com.yoho.anaithumfinal.Model;

public class AddAreaDatum {
    private String d_area_name;

    public String getD_area_name ()
    {
        return d_area_name;
    }

    public void setD_area_name (String d_area_name)
    {
        this.d_area_name = d_area_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [d_area_name = "+d_area_name+"]";
    }
}
