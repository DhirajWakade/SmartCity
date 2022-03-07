package com.allinone.smartocity.model.addBusiness;

import com.allinone.smartocity.model.product.ProductSizeModel;
import com.allinone.smartocity.model.product.ProductTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessField implements Serializable {



    @SerializedName("fId")
    @Expose
    private int fId;

    @SerializedName("fieldName")
    @Expose
    private String fieldName;

    @SerializedName("fieldCode")
    @Expose
    private String fieldCode;

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
}
