package com.allinone.smartocity.model.addBusiness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pincodemodel implements Serializable  {


    @SerializedName("pincode")
    @Expose
    private String pincode;

    private Boolean selected = false;


    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

