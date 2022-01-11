package net.com.gopal.vyapar.invoice.model;

public class InvoiceItem {
    private String itemName=null;
    private String discount=null;
    private String quantity=null;
    private String rate=null;
    private String total=null;
    private String tax=null;

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }


    public String getDiscount() {
        return discount;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
