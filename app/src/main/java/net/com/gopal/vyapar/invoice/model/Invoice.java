package net.com.gopal.vyapar.invoice.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "invoices")
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    public int invoice_id;

    @ColumnInfo(name = "invoice_Code")
    public String invoiceCode=null;

    @ColumnInfo(name = "date")
    public String date=null;

    @ColumnInfo(name = "invoice_type")
    public String invoiceType=null;

    @ColumnInfo(name = "customer_name")
    public String customerName=null;

    @ColumnInfo(name = "tin_no")
    public String tinNo=null;

    @ColumnInfo(name = "invoice_item")
    public String invoiceItem=null;

    @ColumnInfo(name = "total")
    public String total=null;

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public String getDate() {
        return date;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public String getInvoiceItem() {
        return invoiceItem;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setInvoiceItem(String invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
