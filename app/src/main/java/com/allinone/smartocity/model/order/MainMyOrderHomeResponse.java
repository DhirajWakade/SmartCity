package com.allinone.smartocity.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MainMyOrderHomeResponse implements Serializable {

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
    private ArrayList<SubMyOrderHomeResponseModel> result;





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
    public ArrayList<SubMyOrderHomeResponseModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<SubMyOrderHomeResponseModel> result) {
        this.result = result;
    }


}
