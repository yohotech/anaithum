package com.yoho.anaithumfinal.Api;

import java.util.List;

public class NotificationModel {
    private String code;

    private List<NotificationDatum> data;

    private String message;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public List<NotificationDatum> getData ()
    {
        return data;
    }

    public void setData (List<NotificationDatum> data)
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
