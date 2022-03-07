package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSizeModel implements Serializable {

    @SerializedName("sizeTypeId")
    @Expose
    private String sizeTypeId;

    @SerializedName("sizeTypeName")
    @Expose
    private String sizeTypeName;

    @SerializedName("sizeTypeDesc")
    @Expose
    private String sizeTypeDesc;
    private Boolean selected = false;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(String sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    public String getSizeTypeName() {
        return sizeTypeName;
    }

    public void setSizeTypeName(String sizeTypeName) {
        this.sizeTypeName = sizeTypeName;
    }

    public String getSizeTypeDesc() {
        return sizeTypeDesc;
    }

    public void setSizeTypeDesc(String sizeTypeDesc) {
        this.sizeTypeDesc = sizeTypeDesc;
    }
}
