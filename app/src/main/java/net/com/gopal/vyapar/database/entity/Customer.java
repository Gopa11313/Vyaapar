package net.com.gopal.vyapar.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "branch")
    public String branch=null;

    @ColumnInfo(name = "customer_type")
    public String customerType=null;

    @ColumnInfo(name = "name")
    public String name=null;

    @ColumnInfo(name = "email")
    public String email=null;

    @ColumnInfo(name = "tin_number")
    public String tinNumber=null;

    @ColumnInfo(name = "address")
    public String address=null;

    @ColumnInfo(name = "city")
    public String city=null;

    @ColumnInfo(name = "zip")
    public String zip=null;

    @ColumnInfo(name = "mobile_number")
    public String mobileNumber=null;

    @ColumnInfo(name = "land_line")
    public String landLine=null;

    @ColumnInfo(name = "note")
    public String note=null;

    public String getCity() {
        return city;
    }

    public int getCid() {
        return cid;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public String getBranch() {
        return branch;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLandLine() {
        return landLine;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getNote() {
        return note;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
