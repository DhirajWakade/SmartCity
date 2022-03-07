package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductDetail implements Serializable {

    @SerializedName("productDetailId")
    @Expose
    private Integer productDetailId;
    @SerializedName("productMaster")
    @Expose
    private ProductMaster productMaster;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("taxRate")
    @Expose
    private Integer taxRate;
    @SerializedName("finalPrice")
    @Expose
    private Double finalPrice;
    @SerializedName("availableQty")
    @Expose
    private String availableQty;
    @SerializedName("insertTime")
    @Expose
    private String insertTime;

    public Integer getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Integer productDetailId) {
        this.productDetailId = productDetailId;
    }

    public ProductMaster getProductMaster() {
        return productMaster;
    }

    public void setProductMaster(ProductMaster productMaster) {
        this.productMaster = productMaster;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(String availableQty) {
        this.availableQty = availableQty;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}