package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductMaster implements Serializable {

@SerializedName("productCode")
@Expose
private String productCode;
    @SerializedName("productSize")
@Expose
private Integer productSize;
@SerializedName("prodId")
@Expose
private Integer prodId;
@SerializedName("productId")
@Expose
private String productId;
@SerializedName("productTitle")
@Expose
private String productName;
@SerializedName("productBrand")
@Expose
private String productBrand;
@SerializedName("productColor")
@Expose
private String productColor;
@SerializedName("productMaterial")
@Expose
private String productMaterial;
@SerializedName("productTypes")
@Expose
private ProductTypeModel productTypes;
@SerializedName("productKeywords")
@Expose
private List<ProductKeyword> productKeywords = null;

@SerializedName("productSubImages")
@Expose
private List<ProductSubImageModel> productSubImages = null;
@SerializedName("productSubCategory")
@Expose
private ProductSubCategory productSubCategory;
@SerializedName("desciption")
@Expose
private String desciption;
@SerializedName("productImageUrl")
@Expose
private String productImageUrl;
@SerializedName("insertTime")
@Expose
private String insertTime;
@SerializedName("updateTime")
@Expose
private String updateTime;

@SerializedName("productSizeType")
@Expose
private ProductSizeModel productSizeType;


    @SerializedName("productPrize")
    @Expose
    private String productPrize;


    public String getProductPrize() {
        return productPrize;
    }

    public void setProductPrize(String productPrize) {
        this.productPrize = productPrize;
    }

    public ProductSizeModel getProductSizeType() {
        return productSizeType;
    }

    public void setProductSizeType(ProductSizeModel productSizeType) {
        this.productSizeType = productSizeType;
    }

    public Integer getProdId() {
return prodId;
}

public void setProdId(Integer prodId) {
this.prodId = prodId;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public String getProductBrand() {
return productBrand;
}

public void setProductBrand(String productBrand) {
this.productBrand = productBrand;
}

public String getProductColor() {
return productColor;
}

public void setProductColor(String productColor) {
this.productColor = productColor;
}

public String getProductMaterial() {
return productMaterial;
}

public void setProductMaterial(String productMaterial) {
this.productMaterial = productMaterial;
}


public List<ProductKeyword> getProductKeywords() {
return productKeywords;
}

public void setProductKeywords(List<ProductKeyword> productKeywords) {
this.productKeywords = productKeywords;
}

public ProductSubCategory getProductSubCategory() {
return productSubCategory;
}

public void setProductSubCategory(ProductSubCategory productSubCategory) {
this.productSubCategory = productSubCategory;
}

public String getDesciption() {
return desciption;
}

public void setDesciption(String desciption) {
this.desciption = desciption;
}

public String getProductImageUrl() {
return productImageUrl;
}

public void setProductImageUrl(String productImageUrl) {
this.productImageUrl = productImageUrl;
}

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getProductSize() {
        return productSize;
    }

    public void setProductSize(Integer productSize) {
        this.productSize = productSize;
    }


    public ProductTypeModel getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(ProductTypeModel productTypes) {
        this.productTypes = productTypes;
    }

    public List<ProductSubImageModel> getProductSubImages() {
        return productSubImages;
    }

    public void setProductSubImages(List<ProductSubImageModel> productSubImages) {
        this.productSubImages = productSubImages;
    }
}