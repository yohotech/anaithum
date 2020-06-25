package com.yoho.anaithumfinal.Model;

public class FetchDeliveryModel {
    private String code;

    private FetchDeliveryData data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public FetchDeliveryData getData () {
        return data;
    }

    public void setData (FetchDeliveryData data)
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
