package com.allinone.smartocity.model.order;

import com.allinone.smartocity.model.mainresponse.CustomerResultResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubMyOrderHomeResponseModel implements Serializable {

    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("customer")
    @Expose
    private CustomerResultResponse customer;
    @SerializedName("mainOrderCode")
    @Expose
    private String mainOrderCode;
    @SerializedName("mainOrderId")
    @Expose
    private String mainOrderId;
    @SerializedName("mainOrderStatus")
    @Expose
    private String mainOrderStatus;
    @SerializedName("modifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("orderDetails")
    @Expose
    private List<MyOrderDetailsResponseModel> orderDetails;

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

    public String getMainOrderCode() {
        return mainOrderCode;
    }

    public void setMainOrderCode(String mainOrderCode) {
        this.mainOrderCode = mainOrderCode;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getMainOrderStatus() {
        return mainOrderStatus;
    }

    public void setMainOrderStatus(String mainOrderStatus) {
        this.mainOrderStatus = mainOrderStatus;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<MyOrderDetailsResponseModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<MyOrderDetailsResponseModel> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
