package com.allinone.smartocity.model.addBusiness;

import com.allinone.smartocity.model.mainresponse.CustomerResultResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusinessListResponse implements Serializable {
    @SerializedName("businessId")
    @Expose
    private String businessId;
    @SerializedName("businessName")
    @Expose
    private String businessName;
    @SerializedName("panCardNo")
    @Expose
    private String panCardNo;
    @SerializedName("businessNameAaPerPancard")
    @Expose
    private String businessNameAaPerPancard;
    @SerializedName("businessmanUser")
    @Expose
    private CustomerResultResponse businessmanUser;
    @SerializedName("businessCategory")
    @Expose
    private List<BusinessTypeModel> businessCategory = null;
    @SerializedName("addressDetails")
    @Expose
    private AddressDetails addressDetails;
    @SerializedName("bankDetails")
    @Expose
    private BankDetails bankDetails;


    @SerializedName("businessType")
    @Expose
    private BusinessTypeModel businessTypes = null;

    @SerializedName("gstNumber")
    @Expose
    private String gstNumber;
    @SerializedName("isTermAndConditionAgreed")
    @Expose
    private String isTermAndConditionAgreed;
    @SerializedName("minAmtOrderExpected")
    @Expose
    private String minAmtOrderExpected;

    @SerializedName("provideCashOnDelivery")
    @Expose
    private String provideCashOnDelivery;

    @SerializedName("shopImgUrl")
    @Expose
    private String shopImgUrl;






      @SerializedName("foodLicenseNumberExpiryDate")
    @Expose
    private String foodLicenseNumberExpiryDate;

      @SerializedName("foodLicenseNumber")
    @Expose
    private String foodLicenseNumber;

      @SerializedName("freeDelivery")
    @Expose
    private String freeDelivery;

      @SerializedName("deliverablePIN_Code")
    @Expose
    private String deliverablePIN_Code;
      @SerializedName("deliveryChargesPerKM")
    @Expose
    private String deliveryChargesPerKM;


      @SerializedName("mOAWithCharges")
    @Expose
    private String mOAWithCharges;

      @SerializedName("addressProfImgUrl")
    @Expose
    private String addressProfImgUrl;
      @SerializedName("checkImageUrl")
    @Expose
    private String checkImageUrl;

      @SerializedName("panCardImgUrl")
    @Expose
    private String panCardImgUrl;

      @SerializedName("foodLicenseImageUrl")
    @Expose
    private String foodLicenseImageUrl;

    public String getFoodLicenseNumberExpiryDate() {
        return foodLicenseNumberExpiryDate;
    }

    public void setFoodLicenseNumberExpiryDate(String foodLicenseNumberExpiryDate) {
        this.foodLicenseNumberExpiryDate = foodLicenseNumberExpiryDate;
    }

    public String getFoodLicenseNumber() {
        return foodLicenseNumber;
    }

    public void setFoodLicenseNumber(String foodLicenseNumber) {
        this.foodLicenseNumber = foodLicenseNumber;
    }

    public String getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(String freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public String getDeliverablePIN_Code() {
        return deliverablePIN_Code;
    }

    public void setDeliverablePIN_Code(String deliverablePIN_Code) {
        this.deliverablePIN_Code = deliverablePIN_Code;
    }

    public String getDeliveryChargesPerKM() {
        return deliveryChargesPerKM;
    }

    public void setDeliveryChargesPerKM(String deliveryChargesPerKM) {
        this.deliveryChargesPerKM = deliveryChargesPerKM;
    }

    public String getmOAWithCharges() {
        return mOAWithCharges;
    }

    public void setmOAWithCharges(String mOAWithCharges) {
        this.mOAWithCharges = mOAWithCharges;
    }

    public String getAddressProfImgUrl() {
        return addressProfImgUrl;
    }

    public void setAddressProfImgUrl(String addressProfImgUrl) {
        this.addressProfImgUrl = addressProfImgUrl;
    }

    public String getCheckImageUrl() {
        return checkImageUrl;
    }

    public void setCheckImageUrl(String checkImageUrl) {
        this.checkImageUrl = checkImageUrl;
    }

    public String getPanCardImgUrl() {
        return panCardImgUrl;
    }

    public void setPanCardImgUrl(String panCardImgUrl) {
        this.panCardImgUrl = panCardImgUrl;
    }

    public String getFoodLicenseImageUrl() {
        return foodLicenseImageUrl;
    }

    public void setFoodLicenseImageUrl(String foodLicenseImageUrl) {
        this.foodLicenseImageUrl = foodLicenseImageUrl;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getIsTermAndConditionAgreed() {
        return isTermAndConditionAgreed;
    }

    public void setIsTermAndConditionAgreed(String isTermAndConditionAgreed) {
        this.isTermAndConditionAgreed = isTermAndConditionAgreed;
    }

    public String getMinAmtOrderExpected() {
        return minAmtOrderExpected;
    }

    public void setMinAmtOrderExpected(String minAmtOrderExpected) {
        this.minAmtOrderExpected = minAmtOrderExpected;
    }

    public String getProvideCashOnDelivery() {
        return provideCashOnDelivery;
    }

    public void setProvideCashOnDelivery(String provideCashOnDelivery) {
        this.provideCashOnDelivery = provideCashOnDelivery;
    }

    public String getShopImgUrl() {
        return shopImgUrl;
    }

    public void setShopImgUrl(String shopImgUrl) {
        this.shopImgUrl = shopImgUrl;
    }

    public CustomerResultResponse getBusinessmanUser() {
        return businessmanUser;
    }

    public void setBusinessmanUser(CustomerResultResponse businessmanUser) {
        this.businessmanUser = businessmanUser;
    }

    public BusinessTypeModel getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(BusinessTypeModel businessTypes) {
        this.businessTypes = businessTypes;
    }



    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public String getBusinessNameAaPerPancard() {
        return businessNameAaPerPancard;
    }

    public void setBusinessNameAaPerPancard(String businessNameAaPerPancard) {
        this.businessNameAaPerPancard = businessNameAaPerPancard;
    }


    public List<BusinessTypeModel> getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(List<BusinessTypeModel> businessCategory) {
        this.businessCategory = businessCategory;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }
}
