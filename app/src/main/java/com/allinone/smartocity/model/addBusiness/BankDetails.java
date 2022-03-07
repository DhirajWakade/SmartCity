package com.allinone.smartocity.model.addBusiness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BankDetails implements Serializable  {

    @SerializedName("bdId")
    @Expose
    private String bdId;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("ifscCode")
    @Expose
    private String ifscCode;

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
