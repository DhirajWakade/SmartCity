package com.allinone.smartocity.model.category;

import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponseChildListModel {

    @SerializedName("bcId")
    @Expose
    private String bcId;


    @SerializedName("bcName")
    @Expose
    private String bcName;

    @SerializedName("bcCode")
    @Expose
    private String bcCode;


    @SerializedName("businessType")
    @Expose
    private ArrayList<BusinessTypeModel> businessType = null;

    public String getBcId() {
        return bcId;
    }

    public void setBcId(String bcId) {
        this.bcId = bcId;
    }

    public String getBcName() {
        return bcName;
    }

    public void setBcName(String bcName) {
        this.bcName = bcName;
    }

    public String getBcCode() {
        return bcCode;
    }

    public void setBcCode(String bcCode) {
        this.bcCode = bcCode;
    }

    public ArrayList<BusinessTypeModel> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(ArrayList<BusinessTypeModel> businessType) {
        this.businessType = businessType;
    }
}
