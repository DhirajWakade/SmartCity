package com.allinone.smartocity.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubNotificationResultResponse implements Serializable {

    @SerializedName("insertTime")
    @Expose
    private String insertTime;

    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("notifId")
    @Expose
    private String notifId;
    @SerializedName("notifText")
    @Expose
    private String notifText;
    @SerializedName("readstatus")
    @Expose
    private String readstatus;

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getNotifText() {
        return notifText;
    }

    public void setNotifText(String notifText) {
        this.notifText = notifText;
    }

    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus;
    }
}
