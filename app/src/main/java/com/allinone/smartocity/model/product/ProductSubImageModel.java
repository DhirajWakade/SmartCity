package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSubImageModel implements Serializable {

        @SerializedName("subImgId")
        @Expose
        private Integer subImgId;
        @SerializedName("subImgUrl")
        @Expose
        private String subImgUrl;

    public Integer getSubImgId() {
        return subImgId;
    }

    public void setSubImgId(Integer subImgId) {
        this.subImgId = subImgId;
    }

    public String getSubImgUrl() {
        return subImgUrl;
    }

    public void setSubImgUrl(String subImgUrl) {
        this.subImgUrl = subImgUrl;
    }
}
