package commontestware.com.delieveryordermgmt.model;

import java.io.Serializable;

/**
 * Created by BLT0037 on 9/28/2015.
 */
public class ModelTakeAway implements Serializable {
    private String foodlist;
    private String foodquantity;
    private String foodrate;
    private String totalamount;
    private String delivery;
    private String assign;
    private String mobile;
    private String delivery_edt;
    private String delivery_name;
    private String delivery_number;
    private String[] ratearray;

    public ModelTakeAway() {
    }

    public String[] getRatearray() {
        return ratearray;
    }

    public void setRatearray(String[] ratearray) {
        this.ratearray = ratearray;
    }

    public String getDelivery_edt() {
        return delivery_edt;
    }

    public void setDelivery_edt(String delivery_edt) {
        this.delivery_edt = delivery_edt;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(String foodlist) {
        this.foodlist = foodlist;
    }

    public String getFoodquantity() {
        return foodquantity;
    }

    public void setFoodquantity(String foodquantity) {
        this.foodquantity = foodquantity;
    }

    public String getFoodrate() {
        return foodrate;
    }

    public void setFoodrate(String foodrate) {
        this.foodrate = foodrate;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
