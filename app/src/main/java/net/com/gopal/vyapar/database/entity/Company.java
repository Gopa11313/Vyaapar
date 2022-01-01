package net.com.gopal.vyapar.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "companies")
public class Company {
    @PrimaryKey(autoGenerate = true)
    public int comId;

    @ColumnInfo(name = "name")
    public String name = null;

    @ColumnInfo(name = "address1")
    public String aaddress1 = null;

    @ColumnInfo(name = "address2")
    public String address2 = null;

    @ColumnInfo(name = "address3")
    public String address3 = null;

    @ColumnInfo(name = "state")
    public String state = null;

    @ColumnInfo(name = "zip")
    public String zip = null;

    @ColumnInfo(name = "sales")
    public String sales = null;

    @ColumnInfo(name = "office")
    public String office = null;

    @ColumnInfo(name = "mobile")
    public String mobile = null;

    @ColumnInfo(name = "email")
    public String email = null;

    @ColumnInfo(name = "website")
    public String website = null;

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getComId() {
        return comId;
    }

    public String getZip() {
        return zip;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAaddress1() {
        return aaddress1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOffice() {
        return office;
    }

    public String getSales() {
        return sales;
    }

    public String getState() {
        return state;
    }

    public void setAaddress1(String aaddress1) {
        this.aaddress1 = aaddress1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getWebsite() {
        return website;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
