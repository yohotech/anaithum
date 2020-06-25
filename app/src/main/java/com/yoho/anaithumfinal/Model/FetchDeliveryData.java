package com.yoho.anaithumfinal.Model;

public class FetchDeliveryData {
    private String pin_exp_content;

    private String pin_exp_price;

    private String pin_std_price;

    private String pin_std_content;

    private String pin_code;

    private String pin_id;

    private String pin_shipping;

    private String pin_status;

    public String getPin_exp_content ()
    {
        return pin_exp_content;
    }

    public void setPin_exp_content (String pin_exp_content)
    {
        this.pin_exp_content = pin_exp_content;
    }

    public String getPin_exp_price ()
    {
        return pin_exp_price;
    }

    public void setPin_exp_price (String pin_exp_price)
    {
        this.pin_exp_price = pin_exp_price;
    }

    public String getPin_std_price ()
    {
        return pin_std_price;
    }

    public void setPin_std_price (String pin_std_price)
    {
        this.pin_std_price = pin_std_price;
    }

    public String getPin_std_content ()
    {
        return pin_std_content;
    }

    public void setPin_std_content (String pin_std_content)
    {
        this.pin_std_content = pin_std_content;
    }

    public String getPin_code ()
    {
        return pin_code;
    }

    public void setPin_code (String pin_code)
    {
        this.pin_code = pin_code;
    }

    public String getPin_id ()
    {
        return pin_id;
    }

    public void setPin_id (String pin_id)
    {
        this.pin_id = pin_id;
    }

    public String getPin_shipping () {
        return pin_shipping;
    }

    public void setPin_shipping (String pin_shipping) {
        this.pin_shipping = pin_shipping;
    }

    public String getPin_status () {
        return pin_status;
    }

    public void setPin_status (String pin_status) {
        this.pin_status = pin_status;
    }

    @Override
    public String toString() {
        return "ClassPojo [pin_exp_content = "+pin_exp_content+", pin_exp_price = "+pin_exp_price+", pin_std_price = "+pin_std_price+", pin_std_content = "+pin_std_content+", pin_code = "+pin_code+", pin_id = "+pin_id+", pin_shipping = "+pin_shipping+", pin_status = "+pin_status+"]";
    }
}
