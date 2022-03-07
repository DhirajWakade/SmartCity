package com.allinone.smartocity.model.mainresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerResultResponse implements Serializable {

    @SerializedName("bmId")
    @Expose
    private String bmId;

    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("businessUsername")
    @Expose
    private String businessUsername;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailId")
    @Expose
    private String emailId;

    public String getBmId() {
        return bmId;
    }

    public void setBmId(String bmId) {
        this.bmId = bmId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessUsername() {
        return businessUsername;
    }

    public void setBusinessUsername(String businessUsername) {
        this.businessUsername = businessUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
