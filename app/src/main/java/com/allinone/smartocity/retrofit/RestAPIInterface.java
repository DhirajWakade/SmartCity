package com.allinone.smartocity.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RestAPIInterface {

    @POST(ApiEndPoint.LOGIN)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getLoginDetails(@Body String body);

    @POST(ApiEndPoint.BUSINESSREGISTRATION)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessRegistrationDetails(@Body String body);

    @POST(ApiEndPoint.CUSTOMERREGISTRATION)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerRegistrationDetails(@Body String body);

    @GET(ApiEndPoint.CHECK_MOBILE_EXIST_OR_NOT + "{mobile}/{login_type}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getMobileExistOrNotDetails(@Path("mobile") String mobile, @Path("login_type") String logintype);

    @GET(ApiEndPoint.SEND_OTP_ON_EMAIL + "{email}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getSentOTPOnEmail(@Path("email") String email);

  /*  @POST(URL_REMOVEADDRESS + "{position}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getNexShoppingRemoveAddressDetails(@Path("position") String position, @Body String body);
*/

    @GET(ApiEndPoint.GET_BUSINESS_CATEGORIES_LIST)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessCategoriesDetails();

    @GET(ApiEndPoint.BUSINESS_LIST + "{bid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessDetails(@HeaderMap Map<String, String> headers, @Path("bid") String mobile);


    @POST(ApiEndPoint.ADD_BUSINESS)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getAddBusinessDetails(@HeaderMap Map<String, String> headers, @Body String body);


    @POST(ApiEndPoint.EDIT_BUSINESS_DETAILS)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getEditBusinessDetails(@HeaderMap Map<String, String> headers, @Body String body);


    @GET(ApiEndPoint.GET_PRODUCT_CATEGORIES_LIST)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getProductCategoriesDetails();

    @Multipart
    @POST(ApiEndPoint.GET_PRODUCT_UPLOAD_IMAGE)
    Call<String> getProductUploadImage(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part filePart);


    @Multipart
    @POST(ApiEndPoint.GET_PRODUCT_UPLOAD_SUB_IMAGE)
    Call<String> getProductUploadSubImage(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part[] filePart);


    @POST(ApiEndPoint.ADD_PRODUCT)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getAddProductDetails(@HeaderMap Map<String, String> headers, @Body String body);


    @POST(ApiEndPoint.CLOTHS_PRODUCT_UPDATE)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getupdateClothesProductDetails(@HeaderMap Map<String, String> headers, @Body String body);

    @POST(ApiEndPoint.EDIT_PRODUCT)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getEditProductDetails(@HeaderMap Map<String, String> headers, @Body String body);


    @GET(ApiEndPoint.SEARCH_PRODUCT + "{key}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getSearchProductDetails(@Path("key") String mobile);

    @GET(ApiEndPoint.GET_PRODUCT_PAGINATION + "{bid}/{page}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getProductListPaginationDetails(@HeaderMap Map<String, String> headers, @Path("bid") String bid, @Path("page") String page
    );


    @GET(ApiEndPoint.GET_CUSTOMER_PRODUCT_SEARCH_PAGINATION + "{bid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerSearchProductPagination(@Path("bid") String bid);


    @GET(ApiEndPoint.GET_PRODUCT_BY_BUSINESS_CATEGORIES + "{bid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getProductByBusinessCategories(@Path("bid") String bid);


    @GET(ApiEndPoint.PRODUCT_SIZE_DETAILS)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getProductSizeDetails();


    @GET(ApiEndPoint.BUSINESS_HOME + "{bid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessHomeApiDetails(@HeaderMap Map<String, String> headers, @Path("bid") String mobile);


    @GET(ApiEndPoint.CATEGORIES)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCategoryApiDetails();

    @GET(ApiEndPoint.CUSTOMERHOMEDATA)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerHomeApiDetails();


    @GET(ApiEndPoint.CUSTOMER_CART_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerCartList(@Path("cid") String bid);


    @POST(ApiEndPoint.CUSTOMER_ADD_TO_CART)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerAddToCart(@Body String body);


    @POST(ApiEndPoint.CUSTOMER_REMOVE_TO_CART)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerRemoveToCart(@Body String body);

    @POST(ApiEndPoint.CUSTOMER_ADD_ADDRESS)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getAddAddress(@Body String body);


    @GET(ApiEndPoint.CUSTOMER_ADD_ADDRESS_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getAddressList(@Path("cid") String bid);


    @POST(ApiEndPoint.CUSTOMER_PLACE_ORDER)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getPlaceOrderDeails(@Body String body);

    @GET(ApiEndPoint.CUSTOMER_MYORDER_HOME_DETAILS_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerMyOrderHomeDetailsList(@Path("cid") String bid);


    @GET(ApiEndPoint.BUSINESS_ORDER_HOME_DETAILS_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessOrderDetailsList(@Path("cid") String bid);


    @GET(ApiEndPoint.CUTOMER_NOTIFICATION_DETAILS_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getCustomerNotification(@Path("cid") String bid);


    @GET(ApiEndPoint.BUSINESS_NOTIFICATION_DETAILS_LIST + "{cid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessNotification(@Path("cid") String bid);



    @GET(ApiEndPoint.BUSINESS_VALAIDATION_FIELD + "{Bid}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getBusinessValidationField(@Path("Bid") String bid);

}
