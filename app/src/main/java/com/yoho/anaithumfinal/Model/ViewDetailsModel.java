package com.yoho.anaithumfinal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewDetailsModel {
    private String code;

    private ViewDetailsDatum data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public ViewDetailsDatum getData ()
    {
        return data;
    }

    public void setData (ViewDetailsDatum data)
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
