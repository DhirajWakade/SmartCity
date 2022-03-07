package com.allinone.smartocity.model.cart;

import com.allinone.smartocity.model.mainresponse.CustomerResultResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessCartListMainResponseModel implements Serializable {

    @SerializedName("cartTotalAmount")
    @Expose
    private String cartTotalAmount;

    @SerializedName("customer")
    @Expose
    private CustomerResultResponse customer;

    @SerializedName("businessDetails")
    @Expose
    private List<BusinessDetailsModel> businessDetails = null;

    public String getCartTotalAmount() {
        return cartTotalAmount;
    }

    public void setCartTotalAmount(String cartTotalAmount) {
        this.cartTotalAmount = cartTotalAmount;
    }



    public List<BusinessDetailsModel> getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(List<BusinessDetailsModel> businessDetails) {
        this.businessDetails = businessDetails;
    }

    public CustomerResultResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResultResponse customer) {
        this.customer = customer;
    }
}
