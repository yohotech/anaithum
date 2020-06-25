package com.yoho.anaithumfinal.Model;

import java.util.List;

public class AddAreaModel {
    private String code;

    private List<AddAreaDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<AddAreaDatum> getData ()
    {
        return data;
    }

    public void setData (List<AddAreaDatum> data)
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
