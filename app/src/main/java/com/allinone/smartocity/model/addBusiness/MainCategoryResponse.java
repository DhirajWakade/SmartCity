package com.allinone.smartocity.model.addBusiness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainCategoryResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("validationMessage")
    @Expose
    private Object validationMessage;

    public ArrayList<BusinessTypeModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<BusinessTypeModel> result) {
        this.result = result;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<BusinessTypeModel> result;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



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
