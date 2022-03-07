package com.allinone.smartocity.model.order;

import com.allinone.smartocity.model.address.AddressModel;
import com.allinone.smartocity.model.cart.CartModel;
import com.allinone.smartocity.model.mainresponse.CustomerResultResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyOrderDetailsResponseModel implements Serializable {

    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("customer")
    @Expose
    private CustomerResultResponse customer;


    @SerializedName("address")
    @Expose
    private AddressModel address;
    @SerializedName("modifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderProducts")
    @Expose
    private List<CartModel> orderProducts;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;

    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public CustomerResultResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResultResponse customer) {
        this.customer = customer;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<CartModel> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<CartModel> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
