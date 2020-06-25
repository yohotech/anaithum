package com.yoho.anaithumfinal.Model;

public class TrackOrderdatum {
    private String m_delivery_status;

    public String getM_delivery_status ()
    {
        return m_delivery_status;
    }

    public void setM_delivery_status (String m_delivery_status)
    {
        this.m_delivery_status = m_delivery_status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [m_delivery_status = "+m_delivery_status+"]";
    }
}
