package com.allinone.smartocity.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaxRatesItemModel {

    @SerializedName("txId")
    @Expose
    private String txId;


    @SerializedName("txRate")
    @Expose
    private String txRate;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getTxRate() {
        return txRate;
    }

    public void setTxRate(String txRate) {
        this.txRate = txRate;
    }
}
