package com.yoho.anaithumfinal.Api;

public class NotificationDatum {
    private String n_date;

    private String n_text;

    private String n_status;

    private String n_id;

    public String getN_date ()
    {
        return n_date;
    }

    public void setN_date (String n_date)
    {
        this.n_date = n_date;
    }

    public String getN_text ()
    {
        return n_text;
    }

    public void setN_text (String n_text)
    {
        this.n_text = n_text;
    }

    public String getN_status ()
    {
        return n_status;
    }

    public void setN_status (String n_status)
    {
        this.n_status = n_status;
    }

    public String getN_id ()
    {
        return n_id;
    }

    public void setN_id (String n_id)
    {
        this.n_id = n_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [n_date = "+n_date+", n_text = "+n_text+", n_status = "+n_status+", n_id = "+n_id+"]";
    }
}
