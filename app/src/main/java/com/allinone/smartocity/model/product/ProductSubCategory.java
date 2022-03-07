package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSubCategory implements Serializable {

    @SerializedName("productCatId")
    @Expose
    private String productCatId;

    @SerializedName("productCatName")
    @Expose
    private String productCatName;

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
