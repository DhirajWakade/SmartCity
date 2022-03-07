package com.allinone.smartocity.model.home;

import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.model.product.ProductDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainResponseCustomerHomeModel {


    @SerializedName("OfferedProduct")
    @Expose
    private List<ProductDetail> offeredProduct = null;

    @SerializedName("TopBusinessType")
    @Expose
    private List<BusinessTypeModel> topBusinessType = null;


    public List<ProductDetail> getOfferedProduct() {
        return offeredProduct;
    }

    public void setOfferedProduct(List<ProductDetail> offeredProduct) {
        this.offeredProduct = offeredProduct;
    }

    public List<BusinessTypeModel> getTopBusinessType() {
        return topBusinessType;
    }

    public void setTopBusinessType(List<BusinessTypeModel> topBusinessType) {
        this.topBusinessType = topBusinessType;
    }
}
