package com.yoho.anaithumfinal.Model;

import java.util.List;

public class ViewAddressModel {
    private String code;

    private List<ViewAddressDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<ViewAddressDatum> getData ()
    {
        return data;
    }

    public void setData (List<ViewAddressDatum> data)
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
