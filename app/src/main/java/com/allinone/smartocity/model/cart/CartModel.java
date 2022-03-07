package com.allinone.smartocity.model.cart;

import com.allinone.smartocity.model.product.ProductDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartModel implements Serializable {

    @SerializedName("cpId")
    @Expose
    private String cpId;

    @SerializedName("opId")
    @Expose
    private String opId;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;


    @SerializedName("productDetails")
    @Expose
    private ProductDetail productDetails;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ProductDetail getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetail productDetails) {
        this.productDetails = productDetails;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }
}
