package com.yoho.anaithumfinal.Model;

public class SupportModeldata {
    private String ss_id;

    private String ss_status;

    private String ss_reason;

    public String getSs_id ()
    {
        return ss_id;
    }

    public void setSs_id (String ss_id)
    {
        this.ss_id = ss_id;
    }

    public String getSs_status ()
    {
        return ss_status;
    }

    public void setSs_status (String ss_status)
    {
        this.ss_status = ss_status;
    }

    public String getSs_reason ()
    {
        return ss_reason;
    }

    public void setSs_reason (String ss_reason)
    {
        this.ss_reason = ss_reason;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ss_id = "+ss_id+", ss_status = "+ss_status+", ss_reason = "+ss_reason+"]";
    }
}
