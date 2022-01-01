package net.com.gopal.vyapar.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "product_code")
    public String productCode = null;

    @ColumnInfo(name = "description")
    public String description = null;

    @ColumnInfo(name = "image_url")
    public String imageUrl = null;

    @ColumnInfo(name = "Supplier")
    public String Supplier = null;

    @ColumnInfo(name = "rate")
    public String rate = null;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPid() {
        return pid;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getRate() {
        return rate;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

}
