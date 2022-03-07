package com.allinone.smartocity.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductKeyword implements Serializable {

@SerializedName("keywordId")
@Expose
private Integer keywordId;
@SerializedName("keyword")
@Expose
private String keyword;

public Integer getKeywordId() {
return keywordId;
}

public void setKeywordId(Integer keywordId) {
this.keywordId = keywordId;
}

public String getKeyword() {
return keyword;
}

public void setKeyword(String keyword) {
this.keyword = keyword;
}

}