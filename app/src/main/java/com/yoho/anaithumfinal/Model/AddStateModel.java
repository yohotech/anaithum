package com.yoho.anaithumfinal.Model;

import java.util.List;

public class AddStateModel {
    private String code;

    private List<AddStateDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<AddStateDatum> getData ()
    {
        return data;
    }

    public void setData (List<AddStateDatum> data)
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
