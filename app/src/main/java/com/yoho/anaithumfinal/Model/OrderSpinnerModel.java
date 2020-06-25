package com.yoho.anaithumfinal.Model;

import java.util.List;

public class OrderSpinnerModel {
    private String code;

    private List<OrderSpinnerDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<OrderSpinnerDatum> getData ()
    {
        return data;
    }

    public void setData (List<OrderSpinnerDatum> data)
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
