package com.allinone.smartocity.model.home;

import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainResponseBusinessHomeModel {


    @SerializedName("businessTypesWithProductType")
    @Expose
    private List<BusinessTypeModel> businessTypesWithProductType = null;
    @SerializedName("taxRates")
    @Expose
    private List<TaxRatesItemModel> taxRates = null;

    @SerializedName("businessTypes")
    @Expose
    private List<BusinessTypeModel> businessTypes = null;

  @SerializedName("businessDetailsList")
    @Expose
    private List<BusinessListResponse> businessDetailsList = null;



    public List<BusinessTypeModel> getBusinessTypesWithProductType() {
        return businessTypesWithProductType;
    }

    public void setBusinessTypesWithProductType(List<BusinessTypeModel> businessTypesWithProductType) {
        this.businessTypesWithProductType = businessTypesWithProductType;
    }


    public List<BusinessTypeModel> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<BusinessTypeModel> businessTypes) {
        this.businessTypes = businessTypes;
    }


    public List<TaxRatesItemModel> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRatesItemModel> taxRates) {
        this.taxRates = taxRates;
    }

    public List<BusinessListResponse> getBusinessDetailsList() {
        return businessDetailsList;
    }

    public void setBusinessDetailsList(List<BusinessListResponse> businessDetailsList) {
        this.businessDetailsList = businessDetailsList;
    }
}
