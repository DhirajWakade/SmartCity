package com.allinone.smartocity.model.addBusiness;

import com.allinone.smartocity.model.product.ProductSizeModel;
import com.allinone.smartocity.model.product.ProductTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessTypeModel implements Serializable {



    @SerializedName("businessTypeId")
    @Expose
    private String businessTypeId;

    @SerializedName("topBusinessIconUrl")
    @Expose
    private String topBusinessIconUrl;

    @SerializedName("businessTypeName")
    @Expose
    private String businessTypeName;

    @SerializedName("isGstNoRequired")
    @Expose
    private boolean isGstNoRequired;

    @SerializedName("businessTypeCode")
    @Expose
    private String businessTypeCode;
    @SerializedName("isMultiSelection")
    @Expose
    private String isMultiSelection;
    @SerializedName("isProductSearchAllow")
    @Expose
    private String isProductSearchAllow;

    @SerializedName("productSizeTypes")
    @Expose
    private List<ProductSizeModel> productSizeTypes = null;
    @SerializedName("productTypes")
    @Expose
    private List<ProductTypeModel> productTypes = null;

    public List<ProductSizeModel> getProductSizeTypes() {
        return productSizeTypes;
    }

    public void setProductSizeTypes(List<ProductSizeModel> productSizeTypes) {
        this.productSizeTypes = productSizeTypes;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public boolean isGstNoRequired() {
        return isGstNoRequired;
    }

    public void setGstNoRequired(boolean gstNoRequired) {
        isGstNoRequired = gstNoRequired;
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getIsMultiSelection() {
        return isMultiSelection;
    }

    public void setIsMultiSelection(String isMultiSelection) {
        this.isMultiSelection = isMultiSelection;
    }

    public String getIsProductSearchAllow() {
        return isProductSearchAllow;
    }

    public void setIsProductSearchAllow(String isProductSearchAllow) {
        this.isProductSearchAllow = isProductSearchAllow;
    }

    public List<ProductTypeModel> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductTypeModel> productTypes) {
        this.productTypes = productTypes;
    }

    public String getTopBusinessIconUrl() {
        return topBusinessIconUrl;
    }

    public void setTopBusinessIconUrl(String topBusinessIconUrl) {
        this.topBusinessIconUrl = topBusinessIconUrl;
    }
}
