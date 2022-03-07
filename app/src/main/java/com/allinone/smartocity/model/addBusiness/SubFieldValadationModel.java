package com.allinone.smartocity.model.addBusiness;

import com.allinone.smartocity.model.product.ProductSizeModel;
import com.allinone.smartocity.model.product.ProductTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubFieldValadationModel implements Serializable {



    @SerializedName("isVisible")
    @Expose
    private String isVisible;

    @SerializedName("mandaryOrOptional")
    @Expose
    private String mandaryOrOptional;

    @SerializedName("fieldId")
    @Expose
    private int fieldId;

    @SerializedName("businessField")
    @Expose
    private BusinessField businessField;

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public String getMandaryOrOptional() {
        return mandaryOrOptional;
    }

    public void setMandaryOrOptional(String mandaryOrOptional) {
        this.mandaryOrOptional = mandaryOrOptional;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public BusinessField getBusinessField() {
        return businessField;
    }

    public void setBusinessField(BusinessField businessField) {
        this.businessField = businessField;
    }
}
