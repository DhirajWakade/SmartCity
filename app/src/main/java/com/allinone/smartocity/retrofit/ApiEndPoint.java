package com.allinone.smartocity.retrofit;

public class ApiEndPoint {
    public static final String BASE_URL = "http://3.22.98.76:8080/Smartocity/";
    // public static final String BASE_URL_OTP = "http://3.131.152.78:8080/Smartocity/";
    public static final String LOGIN = "rest/login";
    public static final String CUSTOMERREGISTRATION = "customer/register";
    public static final String BUSINESSREGISTRATION = "business/register";
    public static final String CHECK_MOBILE_EXIST_OR_NOT = "rest/checkMobileNo/";
    public static final String SEND_OTP_ON_EMAIL = "rest/sendOTP/";
    public static final String ADD_BUSINESS = "business/addNewBusinessNew";
    public static final String BUSINESS_LIST = "business/getBusinessDetails/";
    public static final String EDIT_BUSINESS_DETAILS = "business/editNewBusiness";
    public static final String GET_BUSINESS_CATEGORIES_LIST = "rest/getBusinessTypes";
    public static final String GET_PRODUCT_CATEGORIES_LIST = "rest/getProductTypes";
    public static final String GET_PRODUCT_UPLOAD_IMAGE = "business/uploadProductImg";
    public static final String GET_PRODUCT_UPLOAD_SUB_IMAGE = "business/uploadProductSubImgs";
    public static final String ADD_PRODUCT = "business/addProductNew";
    public static final String CLOTHS_PRODUCT_UPDATE = "business/addMultiSizeToProduct";
    public static final String EDIT_PRODUCT = "business/editProduct";
    public static final String SEARCH_PRODUCT = "rest/searchProduct/";
    public static final String GET_PRODUCT_PAGINATION = "business/getProductByBusinessId/";
    public static final String GET_CUSTOMER_PRODUCT_SEARCH_PAGINATION = "customer/searchProduct/";
    public static final String PRODUCT_SIZE_DETAILS = "rest/getProductSizeTypes";
    public static final String BUSINESS_HOME = "business/HomeAPIBusiness/";
    public static final String CATEGORIES = "customer/categories";
    public static final String CUSTOMERHOMEDATA = "customer/HomeAPICustomer";
    public static final String GET_PRODUCT_BY_BUSINESS_CATEGORIES = "customer/getProductByBusinessType/";
    public static final String CUSTOMER_CART_LIST = "customer/getCartDetails/";
    public static final String CUSTOMER_ADD_TO_CART = "customer/addToCart";
    public static final String CUSTOMER_REMOVE_TO_CART = "customer/removeFromCart";
    public static final String CUSTOMER_ADD_ADDRESS = "customer/addCustomerAddress";
    public static final String CUSTOMER_ADD_ADDRESS_LIST = "customer/getCustomerAddress/";
    public static final String CUSTOMER_PLACE_ORDER = "customer/placeOrder";
    public static final String CUSTOMER_MYORDER_HOME_DETAILS_LIST = "customer/myOrders/";
    public static final String BUSINESS_ORDER_HOME_DETAILS_LIST = "business/myOrder/";
    public static final String CUTOMER_NOTIFICATION_DETAILS_LIST = "customer/notification/";
    public static final String BUSINESS_NOTIFICATION_DETAILS_LIST = "business/notification/";
    public static final String BUSINESS_VALAIDATION_FIELD = "restAdmin/getBusinessField/";


    public static class ServiceMode {
        public static final int LOGIN_DATA = 1;
        public static final int REGISTRATION_DATA = 2;
        public static final int CHECK_MOBILE_NO_ALREADY_EXIST_OR_NOT = 3;
        public static final int SENDOTPONEMAIL = 4;
        public static final int ADDBUSINESS = 5;
        public static final int BUSINESS_LIST = 6;
        public static final int UPDATEBUSINESSDETAILS = 7;
        public static final int GETBUSINESSCATEGORIESLIST = 8;
        public static final int GETPROSUCTCATEGORIESLIST = 9;
        public static final int GETPROSUCTUPLOADIMAGE = 10;
        public static final int ADDPRODUCT = 11;
        public static final int UPLOADIMAGE_CHECKBOOK = 12;
        public static final int UPLOADIMAGE_SHOP = 13;
        public static final int SEARCH_PRODUCT = 14;
        public static final int PRODUCT_SIZE = 15;
        public static final int HOME_API = 16;
        public static final int GETPROSUCTUPLOADSUBIMAGE = 17;
        public static final int GETPRODUCTLISTPAGNATION = 18;
        public static final int EDIT_PRODUCT = 19;
        public static final int CATEGORIES = 20;
        public static final int CUSTOMER_HOME_API = 21;
        public static final int CUSTOMER_CART_LIST = 22;
        public static final int CUSTOMER_ADD_CART_LIST = 22;
        public static final int CUSTOMER_REMOVE_CART_LIST = 23;
        public static final int CUSTOMER_ADD_ADDRESS = 24;
        public static final int CUSTOMER_ADDRESS_LIST = 25;
        public static final int CUSTOMER_PLACE_ORDER = 26;
        public static final int CUSTOMER_MYORDER_HOME = 27;
        public static final int BUSINESS_MYORDER_HOME = 28;
        public static final int NOTIFICATION_DETAILS = 29;
        public static final int UPDATE_CLOTHS_PRODUCT =30;
        public static final int BUSINESS_VALIDATION_FIELD =31;

        public static final int UPLOADIMAGE_PANCARD = 32;
        public static final int UPLOADIMAGE_ADDRESS_PROOF = 33;
        public static final int UPLOADIMAGE_FOOD_LICENCE = 34;

        public static final int EDITBUSINESS = 35;
    }


}
