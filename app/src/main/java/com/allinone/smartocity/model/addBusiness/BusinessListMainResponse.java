package com.allinone.smartocity.model.addBusiness;

import com.allinone.smartocity.model.product.ProductDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BusinessListMainResponse {


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("validationMessage")
    @Expose
    private Object validationMessage;

    public ArrayList<BusinessListResponse> getResult() {
        return result;
    }

    public void setResult(ArrayList<BusinessListResponse> result) {
        this.result = result;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<BusinessListResponse> result;
    @SerializedName("productDetails")
    @Expose
    private List<ProductDetail> productDetails = null;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(Object validationMessage) {
        this.validationMessage = validationMessage;
    }
}
