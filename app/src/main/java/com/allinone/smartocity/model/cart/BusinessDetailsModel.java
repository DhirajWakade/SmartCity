package com.allinone.smartocity.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessDetailsModel implements Serializable {

    @SerializedName("businessId")
    @Expose
    private String businessId;
    @SerializedName("businessName")
    @Expose
    private String businessName;
    @SerializedName("businessTotalAmount")
    @Expose
    private String businessTotalAmount;
    @SerializedName("minOrderLimit")
    @Expose
    private String minOrderLimit;

    @SerializedName("cartProducts")
    @Expose
    private List<CartModel> cardProducts = null;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessTotalAmount() {
        return businessTotalAmount;
    }

    public void setBusinessTotalAmount(String businessTotalAmount) {
        this.businessTotalAmount = businessTotalAmount;
    }

    public String getMinOrderLimit() {
        return minOrderLimit;
    }

    public void setMinOrderLimit(String minOrderLimit) {
        this.minOrderLimit = minOrderLimit;
    }

    public List<CartModel> getCardProducts() {
        return cardProducts;
    }

    public void setCardProducts(List<CartModel> cardProducts) {
        this.cardProducts = cardProducts;
    }
}
