package com.yoho.anaithumfinal.Model;

import java.util.List;

public class ApplyCouponModel {
    private String code;

    private List<ApplyCouponDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<ApplyCouponDatum> getData ()
    {
        return data;
    }

    public void setData (List<ApplyCouponDatum> data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", data = "+data+", message = "+message+"]";
    }
}
