package com.yoho.anaithumfinal.Model;

public class OrderSpinnerDatum {
    private String m_order_id;

    private String r_id;

    private String m_id;

    public String getM_order_id ()
    {
        return m_order_id;
    }

    public void setM_order_id (String m_order_id)
    {
        this.m_order_id = m_order_id;
    }

    public String getR_id ()
    {
        return r_id;
    }

    public void setR_id (String r_id)
    {
        this.r_id = r_id;
    }

    public String getM_id ()
    {
        return m_id;
    }

    public void setM_id (String m_id)
    {
        this.m_id = m_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [m_order_id = "+m_order_id+", r_id = "+r_id+", m_id = "+m_id+"]";
    }
}
