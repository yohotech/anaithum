package com.yoho.anaithumfinal.Model;

public class AddPincodeModelDatum {

    private String pin_code;

    public String getPin_code ()
    {
        return pin_code;
    }

    public void setPin_code (String pin_code)
    {
        this.pin_code = pin_code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pin_code = "+pin_code+"]";
    }
}
