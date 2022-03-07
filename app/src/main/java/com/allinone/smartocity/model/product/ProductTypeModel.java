package com.allinone.smartocity.model.product;

import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductTypeModel implements Serializable {

    @SerializedName("productTypeId")
    @Expose
    private String productCatId;

    @SerializedName("productType")
    @Expose
    private String productCatName;


    @SerializedName("businessTypes")
    @Expose
    private BusinessTypeModel businessTypes;

    public String getProductCatId() {
        return productCatId;
    }

    public void setProductCatId(String productCatId) {
        this.productCatId = productCatId;
    }

    public String getProductCatName() {
        return productCatName;
    }

    public void setProductCatName(String productCatName) {
        this.productCatName = productCatName;
    }
}
