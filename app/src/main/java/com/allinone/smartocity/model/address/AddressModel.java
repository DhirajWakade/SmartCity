package com.allinone.smartocity.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressModel implements Serializable {

    @SerializedName("addrId")
    @Expose
    private String addrId;

    @SerializedName("addreLine1")
    @Expose
    private String addreLine1;

    @SerializedName("addreLine2")
    @Expose
    private String addreLine2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("state")
    @Expose
    private String state;

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getAddreLine1() {
        return addreLine1;
    }

    public void setAddreLine1(String addreLine1) {
        this.addreLine1 = addreLine1;
    }

    public String getAddreLine2() {
        return addreLine2;
    }

    public void setAddreLine2(String addreLine2) {
        this.addreLine2 = addreLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
