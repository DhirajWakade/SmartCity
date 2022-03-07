package com.allinone.smartocity.model.mainresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainLoginResponse {

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

    @SerializedName("result")
    @Expose
    private CustomerResultResponse result;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CustomerResultResponse getResult() {
        return result;
    }

    public void setResult(CustomerResultResponse result) {
        this.result = result;
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
