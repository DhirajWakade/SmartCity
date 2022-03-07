package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainProductSizeResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("validationMessage")
    @Expose
    private Object validationMessage;

    public ArrayList<ProductSizeModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<ProductSizeModel> result) {
        this.result = result;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<ProductSizeModel> result;





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
