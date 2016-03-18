package commontestware.com.delieveryordermgmt.model;

import java.io.Serializable;

/**
 * Created by BLT0037 on 9/26/2015.
 */
public class ModelDineIn implements Serializable {
    private String category;
    private String fooditem;
    private String quantity;
    //private String rate;
    private String totalamount;
    //private String tax;
    //private String grossamount;
    private String[] ratearray;

    public String[] getRatearray() {
        return ratearray;
    }

    public void setRatearray(String[] ratearray) {
        this.ratearray = ratearray;
    }

    public ModelDineIn() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFooditem() {
        return fooditem;
    }

    public void setFooditem(String fooditem) {
        this.fooditem = fooditem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

   /* public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }*/

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

  /*  public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
*/
   /* public String getGrossamount() {
        return grossamount;
    }

    public void setGrossamount(String grossamount) {
        this.grossamount = grossamount;
    }*/
}
