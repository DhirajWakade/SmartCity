package com.allinone.smartocity.Business.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.allinone.smartocity.Business.adapter.*;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
import com.allinone.smartocity.model.home.TaxRatesItemModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.*;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.*;
import static com.allinone.smartocity.retrofit.Constant.SHARED_HOME_API_DETAILS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;


public class EditProductActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback, AdapterView.OnItemSelectedListener {

    private String TAG = "AddBusinessActivity";
    private Button btn_submit_product;

    private Spinner spiner_category, spinner_size, spinner_tax_rate, spiner_single_size, spiner_sub_category, spinner_color;
    private ArrayList<ProductTypeModel> product_type_list;
    private ArrayList<ProductSizeModel> product_size_list;
    private ArrayList<TaxRatesItemModel> product_tax_rate_list;
    private ArrayList<ProductSubCategory> sub_category_list;
    private ProductCategoryAdapter categoryAdapter;
    private MainLoginResponse mainLoginResponse = null;
    private LinearLayout ll_screen_1, ll_screen_2, ll_screen_3, ll_screen_4,
            parentLinearLayout_keyword, parentLinearLayout_size_data;
    private FloatingActionButton fab_next, fab_prev;
    private ProductSubCategoryAdapter subCategoryAdapter;
    private int count = 0;
    private ProductTypeModel productTypeModel = null;
    private ProductSubCategory productSubCategory = null;
    //private ImageView img_product, img_product1, img_product2, img_product3;
    //private static final int PERMISSION_REQUEST_CODE = 200;

    // private String uploadedImageName = "";
    private EditText edt_product_title, edt_product_brand,
            edt_product_actual_price, edt_product_final_price, edt_product_available_quantity,
            edt_product_max_order_quantity, edt_product_description, edt_encluse_material, edt_keyword;
    private String product_title = "", product_brand = "",
            product_actual_price = "", product_final_price = "", product_available_quantity = "", product_max_order_quantity = "",
            product_description = "", encluse_material = "", keyword = "";
   /* static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    static final int REQUEST_TAKE_PHOTO1 = 3;
    static final int REQUEST_GALLERY_PHOTO1 = 4;

    static final int REQUEST_TAKE_PHOTO2 = 5;
    static final int REQUEST_GALLERY_PHOTO2 = 6;

    static final int REQUEST_TAKE_PHOTO3 = 7;
    static final int REQUEST_GALLERY_PHOTO3 = 8;*/

    /*
        File mPhotoFile = null, mPhotoFile1 = null, mPhotoFile2 = null, mPhotoFile3 = null;
        FileCompressor mCompressor;*/
    private ProductSizeMultipleAdapter productSizeMultipleAdapter;
    private ProductSizeModel productSizeModel = null;
    private MainResponseBusinessHomeModel mainResponseBusinessHomeModel = null;
    private BusinessListResponse businessListResponse = null;

    private EditText ed;
    List<EditText> allEds = new ArrayList<EditText>();
    List<ProductSizeModel> selected_product_size_id_model = new ArrayList<ProductSizeModel>();
    private ProductSizeSingleAdapter productSizeSingleAdapter;
    private RelativeLayout rl_product_multiple_size, rl_product_single_size;
    //private JSONArray jsonSubImageArray;
    private ProductTaxRateAdapter pproductTaxRateAdapter;
    private TaxRatesItemModel productTaxRateModel = null;
    private ProductDetail roductDetail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        btn_submit_product.setOnClickListener(this);
        fab_next.setOnClickListener(this);
        fab_prev.setOnClickListener(this);
        spiner_category.setOnItemSelectedListener(this);
        spinner_size.setOnItemSelectedListener(this);
        spiner_single_size.setOnItemSelectedListener(this);
        spiner_sub_category.setOnItemSelectedListener(this);
        spinner_tax_rate.setOnItemSelectedListener(this);
        /*img_product.setOnClickListener(this);
        img_product1.setOnClickListener(this);
        img_product2.setOnClickListener(this);
        img_product3.setOnClickListener(this);*/
    }

    private void setData() {
        // getProductCategoryList();
        screen1();
        for (int i = 0; i < mainResponseBusinessHomeModel.getBusinessTypesWithProductType().size(); i++) {
            BusinessTypeModel typeModel = mainResponseBusinessHomeModel.getBusinessTypesWithProductType().get(i);
            if (businessListResponse.getBusinessTypes().getBusinessTypeId().equals(typeModel.getBusinessTypeId())) {
                setProductTypeList(typeModel);
                setProductSizeList(typeModel);
            }
        }

        setProductTaxRateList(mainResponseBusinessHomeModel.getTaxRates());
        edt_product_title.setText(roductDetail.getProductMaster().getProductName());
        edt_product_brand.setText(roductDetail.getProductMaster().getProductBrand());

        edt_product_actual_price.setText("" + roductDetail.getPrice());

        edt_product_final_price.setText("" + roductDetail.getFinalPrice());
        edt_product_available_quantity.setText(roductDetail.getAvailableQty());
        edt_product_description.setText(roductDetail.getProductMaster().getDesciption());
        edt_encluse_material.setText(roductDetail.getProductMaster().getProductMaterial());
        //parentLinearLayout_bulletpoint = findViewById(R.id.parentLinearLayout_bulletpoint);
        parentLinearLayout_keyword = findViewById(R.id.parentLinearLayout_keyword);
    }


    private void setProductSizeList(BusinessTypeModel typeModel) {
        product_size_list.clear();
        ProductSizeModel category1 = new ProductSizeModel();
        category1.setSizeTypeName("Select size");
        product_size_list.add(category1);
        if (typeModel.getProductSizeTypes().size() > 0)
            product_size_list.addAll(typeModel.getProductSizeTypes());
        if (businessListResponse.getBusinessTypes().getIsMultiSelection().equals("YES")) {
            rl_product_multiple_size.setVisibility(View.VISIBLE);
            rl_product_single_size.setVisibility(View.GONE);
            productSizeMultipleAdapter = new ProductSizeMultipleAdapter(EditProductActivity.this, 0,
                    product_size_list);
            spinner_size.setAdapter(productSizeMultipleAdapter);
        } else {
            rl_product_multiple_size.setVisibility(View.GONE);
            rl_product_single_size.setVisibility(View.VISIBLE);
            productSizeSingleAdapter = new ProductSizeSingleAdapter(EditProductActivity.this, 0,
                    product_size_list);
            spiner_single_size.setAdapter(productSizeSingleAdapter);
        }
    }


    private void setProductTaxRateList(List<TaxRatesItemModel> rateList) {
        product_tax_rate_list.clear();
        TaxRatesItemModel category1 = new TaxRatesItemModel();
        category1.setTxRate("Select rate");
        product_tax_rate_list.add(category1);
        if (rateList.size() > 0)
            product_tax_rate_list.addAll(rateList);
        pproductTaxRateAdapter = new ProductTaxRateAdapter(EditProductActivity.this, 0, product_tax_rate_list);
        spinner_tax_rate.setAdapter(pproductTaxRateAdapter);

    }

    private void setProductTypeList(BusinessTypeModel typeModel) {
        product_type_list.clear();
        ProductTypeModel category1 = new ProductTypeModel();
        category1.setProductCatName("Select product type");
        product_type_list.add(category1);
        if (typeModel.getProductTypes().size() > 0)
            product_type_list.addAll(typeModel.getProductTypes());

        categoryAdapter = new ProductCategoryAdapter(EditProductActivity.this, 0,
                product_type_list);
        spiner_category.setAdapter(categoryAdapter);
    }

   /* private void getProductCategoryList() {
        if (
                InternetConnection.checkConnection(AddProductActivity.this)) {
            categoriesList();
        } else {
            Toast.makeText(AddProductActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }*/


    private void getProductSizeList() {
        if (
                InternetConnection.checkConnection(EditProductActivity.this)) {
            ProductSizeList();
        } else {
            Toast.makeText(EditProductActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeWidgets() {
        rl_product_multiple_size = findViewById(R.id.rl_product_multiple_size);
        rl_product_single_size = findViewById(R.id.rl_product_single_size);
        spinner_size = findViewById(R.id.spinner_size);
        spiner_single_size = findViewById(R.id.spiner_single_size);
        spiner_category = findViewById(R.id.spiner_category);
        spiner_sub_category = findViewById(R.id.spiner_sub_category);
        spinner_color = findViewById(R.id.spinner_color);
        edt_product_title = findViewById(R.id.edt_product_title);
        edt_product_brand = findViewById(R.id.edt_product_brand);
        parentLinearLayout_size_data = findViewById(R.id.parentLinearLayout_size_data);

        // edt_product_sdler_sku = findViewById(R.id.edt_product_sdler_sku);
        edt_product_actual_price = findViewById(R.id.edt_product_actual_price);
        spinner_tax_rate = findViewById(R.id.spinner_tax_rate);
        edt_product_final_price = findViewById(R.id.edt_product_final_price);
        edt_product_available_quantity = findViewById(R.id.edt_product_available_quantity);
        edt_product_max_order_quantity = findViewById(R.id.edt_product_max_order_quantity);
        edt_product_description = findViewById(R.id.edt_product_description);
        edt_encluse_material = findViewById(R.id.edt_encluse_material);
        edt_keyword = findViewById(R.id.edt_keyword);

        btn_submit_product = findViewById(R.id.btn_submit_product);
        ll_screen_1 = findViewById(R.id.ll_screen_1);
        ll_screen_2 = findViewById(R.id.ll_screen_2);
        ll_screen_3 = findViewById(R.id.ll_screen_3);
        ll_screen_4 = findViewById(R.id.ll_screen_4);
        fab_next = findViewById(R.id.fab_next);
        fab_prev = findViewById(R.id.fab_prev);
        //parentLinearLayout_bulletpoint = findViewById(R.id.parentLinearLayout_bulletpoint);
        parentLinearLayout_keyword = findViewById(R.id.parentLinearLayout_keyword);
       /* img_product = findViewById(R.id.img_product);
        img_product1 = findViewById(R.id.img_product1);
        img_product2 = findViewById(R.id.img_product2);
        img_product3 = findViewById(R.id.img_product3);*/
        // mCompressor = new FileCompressor(this);

    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);

        businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
        roductDetail = (ProductDetail) getIntent().getSerializableExtra("ProductMaster");
        product_type_list = new ArrayList<>();
        sub_category_list = new ArrayList<>();
        product_size_list = new ArrayList<>();
        product_tax_rate_list = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_product:
               /* if (valid4()) {

                    if (InternetConnection.checkConnection(EditProductActivity.this)) {
                        uploadImage();

                    } else {
                        Toast.makeText(EditProductActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }*/
                break;

            case R.id.fab_next:
                if (ll_screen_1.getVisibility() == View.VISIBLE) {
                    if (valid1())
                        screenNext2();
                } else if (ll_screen_2.getVisibility() == View.VISIBLE) {
                    if (valid2())
                        screen3();
                } else if (ll_screen_3.getVisibility() == View.VISIBLE) {
                    if (valid3())
                        editProduct();
                }


                break;
            case R.id.fab_prev:

                if (ll_screen_2.getVisibility() == View.VISIBLE) {
                    screen1();
                } else if (ll_screen_3.getVisibility() == View.VISIBLE) {
                    screenPre2();
                } else if (ll_screen_4.getVisibility() == View.VISIBLE) {
                    screen3();
                }

                break;

            case R.id.img_product:


                /*if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage(REQUEST_TAKE_PHOTO, REQUEST_GALLERY_PHOTO);

                }*/
                break;

            /*case R.id.img_product1:


                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage(REQUEST_TAKE_PHOTO1, REQUEST_GALLERY_PHOTO1);

                }

                break;
            case R.id.img_product2:


                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage(REQUEST_TAKE_PHOTO2, REQUEST_GALLERY_PHOTO2);

                }
                break;
            case R.id.img_product3:


                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage(REQUEST_TAKE_PHOTO3, REQUEST_GALLERY_PHOTO3);

                }


                break;*/


        }
    }


    /*private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (writeAccepted && cameraAccepted && readAccepted) {
                        showMessageOKCancel("Permission Granted, Now you can access location data and camera.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //selectImage(REQUEST_TAKE_PHOTO, REQUEST_GALLERY_PHOTO, mPhotoFile);
                            }
                        });
                    } else {

                        showMessageOKCancel("Permission Denied, You cannot access location data and camera.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_CODE);
                            }
                        });

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }*/


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EditProductActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void screen4() {
        ll_screen_1.setVisibility(View.GONE);
        ll_screen_2.setVisibility(View.GONE);
        ll_screen_3.setVisibility(View.GONE);
        ll_screen_4.setVisibility(View.VISIBLE);
        fab_next.setVisibility(View.GONE);
        fab_prev.setVisibility(View.VISIBLE);

    }

    private void screen3() {
        ll_screen_1.setVisibility(View.GONE);
        ll_screen_2.setVisibility(View.GONE);
        ll_screen_3.setVisibility(View.VISIBLE);
        ll_screen_4.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
    }

    private void screenPre2() {
        ll_screen_1.setVisibility(View.GONE);
        ll_screen_2.setVisibility(View.VISIBLE);
        ll_screen_3.setVisibility(View.GONE);
        ll_screen_4.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);

    }

    private void screenNext2() {
        ll_screen_1.setVisibility(View.GONE);
        ll_screen_2.setVisibility(View.VISIBLE);
        ll_screen_3.setVisibility(View.GONE);
        ll_screen_4.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
        if (businessListResponse.getBusinessTypes().getIsMultiSelection().equals("YES")) {

            for (int i = 0; i < selected_product_size_id_model.size(); i++) {
                //ProductSizeModel productSizeModel = selected_product_size_id_model.get(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                ed = new EditText(EditProductActivity.this);
                allEds.add(ed);

                ed.setBackground(getResources().getDrawable(R.drawable.edit_border));
                ed.setId(i);
                ed.setInputType(InputType.TYPE_CLASS_NUMBER);
                ed.setPadding(15, 20, 15, 20);
                ed.setHint("Enter quantity " + selected_product_size_id_model.get(i).getSizeTypeName());
                ed.setLayoutParams(params);
                parentLinearLayout_size_data.addView(ed);
                    /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.view_product_size_price_field, null);
                    // Add the new rowView.serow after  the first  field button.
                    parentLinearLayout_size_data.addView(rowView, parentLinearLayout_size_data.getChildCount());*/

            }

        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            ed = new EditText(EditProductActivity.this);
            allEds.add(ed);
            selected_product_size_id_model.add(productSizeModel);
            ed.setBackground(getResources().getDrawable(R.drawable.edit_border));
            ed.setId(0);
            ed.setInputType(InputType.TYPE_CLASS_NUMBER);
            ed.setPadding(15, 20, 15, 20);
            ed.setHint("Enter quantity");
            ed.setLayoutParams(params);
            parentLinearLayout_size_data.addView(ed);
        }
    }

  /*  String[] strings = new String[](allEds.size());

for(int i=0; i < allEds.size(); i++){
        string[i] = allEds.get(i).getText().toString();
    }*/

    private void screen1() {
        ll_screen_1.setVisibility(View.VISIBLE);
        ll_screen_2.setVisibility(View.GONE);
        ll_screen_3.setVisibility(View.GONE);
        ll_screen_4.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.GONE);
        allEds.clear();
        selected_product_size_id_model.clear();
        parentLinearLayout_size_data.removeAllViews();
    }

/*    public void onAddFieldBulletPoint(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.bullet_field, null);
        // Add the new row before the add field button.
        parentLinearLayout_bulletpoint.addView(rowView, parentLinearLayout_bulletpoint.getChildCount() - 1);
    }

    public void onDeleteBulletPoint(View v) {
        parentLinearLayout_bulletpoint.removeView((View) v.getParent());
    }*/

    public void onAddFieldKeyword(View v) {
        if (count < 5) {
            count++;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.keyword_field, null);
            // Add the new row after  the first  field button.
            parentLinearLayout_keyword.addView(rowView, parentLinearLayout_keyword.getChildCount());
        }

    }

    public void onDeleteKeyword(View v) {
        count--;
        parentLinearLayout_keyword.removeView((View) v.getParent());
    }


    private void editProduct() {


        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject productMaster = new JSONObject();
            JSONObject producttype = new JSONObject();
            producttype.put("productTypeId", Integer.parseInt(productTypeModel.getProductCatId()));
            productMaster.put("productId", roductDetail.getProductMaster().getProdId());
            productMaster.put("productTypes", producttype);
            productMaster.put("productName", product_title);
            productMaster.put("productBrand", product_brand);
            productMaster.put("productColor", spinner_color.getSelectedItem().toString());
            productMaster.put("productMaterial", encluse_material);

            JSONArray productSizeArray = new JSONArray();
            for (int i = 0; i < selected_product_size_id_model.size(); i++) {
                ProductSizeModel productSizeModel = selected_product_size_id_model.get(i);
                //allEds.get(i).getText().toString();
                JSONObject productSuegor = new JSONObject();
                JSONObject productSubCategor = new JSONObject();
                productSubCategor.put("sizeTypeId", Integer.parseInt(productSizeModel.getSizeTypeId()));
                productSuegor.put("ProductSizeType", productSubCategor);
                productSuegor.put("price", Integer.parseInt(allEds.get(i).getText().toString()));
                productSizeArray.put(productSuegor);
            }


            String json = new Gson().toJson(roductDetail.getProductMaster().getProductSubImages());
            JSONArray jsonSubImageA = new JSONArray(json);
           /*  for (int i = 0; i < jsonSubImageArray.length(); i++) {
                String productSizeModel = jsonSubImageArray.get(i).toString();
                //allEds.get(i).getText().toString();
                JSONObject productSubCategor = new JSONObject();
                productSubCategor.put("subImgUrl", productSizeModel);
                jsonSubImageA.put(productSubCategor);
            }*/

            productMaster.put("productSubImages", jsonSubImageA);
            productMaster.put("multiSizeProductPrices", productSizeArray);
            productMaster.put("desciption", product_description);
            productMaster.put("productImageUrl", roductDetail.getProductMaster().getProductImageUrl());
            JSONArray productKeywords = new JSONArray();
            JSONObject keywor = new JSONObject();
            keywor.put("keyword", keyword);
            productKeywords.put(keywor);
            productMaster.put("productKeywords", productKeywords);
            JSONObject businessDetails = new JSONObject();
            businessDetails.put("businessId", Integer.parseInt(businessListResponse.getBusinessId()));
            jsonObject.put("price", Integer.parseInt(product_actual_price));
            jsonObject.put("availableQty", Integer.parseInt(product_available_quantity));
            jsonObject.put("businessDetails", businessDetails);
            jsonObject.put("finalPrice", Integer.parseInt(product_final_price));
            jsonObject.put("taxRate", Integer.parseInt(productTaxRateModel.getTxRate()));
            jsonObject.put("productMaster", productMaster);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getEditProductDetails(headers, jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(EditProductActivity.this, EditProductActivity.this, ADDPRODUCT, true));

    }

    private void categoriesList() {
        JSONObject jsonObject = new JSONObject();
        try {
        /*
            jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<String> productDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductCategoriesDetails();
        productDetails.enqueue(new RestProcess<String>(EditProductActivity.this, EditProductActivity.this, GETPROSUCTCATEGORIESLIST, true));
    }


    private void ProductSizeList() {
        JSONObject jsonObject = new JSONObject();
        try {
        /*
            jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<String> productsizeDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductSizeDetails();
        productsizeDetails.enqueue(new RestProcess<String>(EditProductActivity.this, EditProductActivity.this, PRODUCT_SIZE, true));
    }


    /*private void uploadImage() {
        JSONObject jsonObject = new JSONObject();
        try {
        *//*
            jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);
            *//*
        } catch (Exception e) {
            e.printStackTrace();
        }


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", mPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mPhotoFile));
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> productDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
        productDetails.enqueue(new RestProcess<String>(EditProductActivity.this, EditProductActivity.this, GETPROSUCTUPLOADIMAGE, true));
    }


    private void uploadSubImage() {
        JSONObject jsonObject = new JSONObject();
        try {
        *//*
            jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);
            *//*
        } catch (Exception e) {
            e.printStackTrace();
        }
        MultipartBody.Part[] parts = new MultipartBody.Part[3];
        parts[0] = MultipartBody.Part.createFormData("file", mPhotoFile1.getName(), RequestBody.create(MediaType.parse("image/*"), mPhotoFile1));
        parts[1] = MultipartBody.Part.createFormData("file", mPhotoFile2.getName(), RequestBody.create(MediaType.parse("image/*"), mPhotoFile2));
        parts[2] = MultipartBody.Part.createFormData("file", mPhotoFile3.getName(), RequestBody.create(MediaType.parse("image/*"), mPhotoFile3));

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> productDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadSubImage(headers, parts);
        productDetails.enqueue(new RestProcess<String>(EditProductActivity.this, EditProductActivity.this, GETPROSUCTUPLOADSUBIMAGE, true));
    }*/

    private boolean valid1() {
        product_title = edt_product_title.getText().toString().trim();
        product_brand = edt_product_brand.getText().toString().trim();
        if (productTypeModel == null) {
            Toast.makeText(EditProductActivity.this, "Please select type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_title.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product  title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_brand.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product brand", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (businessListResponse.getBusinessTypes().getIsMultiSelection().equals("YES")) {
            if (productSizeMultipleAdapter.getProductSizeList() != null) {
                if (productSizeMultipleAdapter.getProductSizeList().size() > 0) {
                    for (int i = 0; i < productSizeMultipleAdapter.getProductSizeList().size(); i++) {
                        ProductSizeModel productSizeModel = productSizeMultipleAdapter.getProductSizeList().get(i);
                        if (productSizeModel.getSelected()) {
                            selected_product_size_id_model.add(productSizeModel);
                        }

                    }
                }
            }

            if (selected_product_size_id_model.size() == 0) {
                Toast.makeText(EditProductActivity.this, "Please select product size at list one", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (productSizeModel == null) {
                Toast.makeText(EditProductActivity.this, "Please select product size", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

   /* private boolean valid4() {
        if (mPhotoFile == null) {

            Toast.makeText(EditProductActivity.this, "Please select main_business product image", Toast.LENGTH_SHORT).show();
            return false;

        }


        if (mPhotoFile1 == null) {

            Toast.makeText(EditProductActivity.this, "Please select Sub image 1", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (mPhotoFile2 == null) {

            Toast.makeText(EditProductActivity.this, "Please select Sub image 2", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (mPhotoFile3 == null) {

            Toast.makeText(EditProductActivity.this, "Please select Sub image 3", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }*/

    private boolean valid2() {

        //  product_sdler_sku = edt_product_sdler_sku.getText().toString().trim();
        product_actual_price = edt_product_actual_price.getText().toString().trim();

        product_final_price = edt_product_final_price.getText().toString().trim();

       /* if (product_sdler_sku.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product sku", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if (productTaxRateModel == null) {
            Toast.makeText(EditProductActivity.this, "Please select product tax rate", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (product_actual_price.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product actual price", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_final_price.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product final price", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private boolean valid3() {
        product_available_quantity = edt_product_available_quantity.getText().toString().trim();
        product_max_order_quantity = edt_product_max_order_quantity.getText().toString().trim();
        product_description = edt_product_description.getText().toString().trim();
        keyword = edt_keyword.getText().toString().trim();
        encluse_material = edt_encluse_material.getText().toString().trim();

       /* if (productSubCategory == null) {
            Toast.makeText(AddProductActivity.this, "Please select Sub Category", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if (product_available_quantity.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product available quantity", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (product_max_order_quantity.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product max order", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (encluse_material.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter material", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (product_description.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter product description", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (keyword.equals("")) {
            Toast.makeText(EditProductActivity.this, "Please enter keyword", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


   /* private void selectImage(final int requestTakePhoto, final int requestGalleryPhoto) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    try {

                        dispatchTakePictureIntent(requestTakePhoto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    dispatchGalleryIntent(requestGalleryPhoto);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent(int requestGalleryPhoto) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, requestGalleryPhoto);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


            switch (requestCode) {

                case 1:
                    try {
                        mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product);
                    break;

                case 2:

                    Uri selectedImage = data.getData();
                    try {
                        mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product);
                    break;

                case 3:
                    try {
                        mPhotoFile1 = mCompressor.compressToFile(mPhotoFile1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile1)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product1);
                    break;

                case 4:

                    Uri selectedImage1 = data.getData();
                    try {
                        mPhotoFile1 = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage1)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile1)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product1);
                    break;

                case 5:
                    try {
                        mPhotoFile2 = mCompressor.compressToFile(mPhotoFile2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile2)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product2);
                    break;

                case 6:

                    Uri selectedImage2 = data.getData();
                    try {
                        mPhotoFile2 = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage2)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile2)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product2);
                    break;
                case 7:
                    try {
                        mPhotoFile3 = mCompressor.compressToFile(mPhotoFile3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile3)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product3);
                    break;

                case 8:

                    Uri selectedImage3 = data.getData();
                    try {
                        mPhotoFile3 = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage3)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(EditProductActivity.this)
                            .load(mPhotoFile3)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product3);
                    break;
            }


        }
    }*/

    /**
     * Get real file path from URI
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }


/*    private void dispatchTakePictureIntent(int requestTakePhoto) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                switch (requestTakePhoto) {
                    case 1:
                        mPhotoFile = photoFile;
                        break;
                    case 3:
                        mPhotoFile1 = photoFile;
                        break;
                    case 5:
                        mPhotoFile2 = photoFile;
                        break;

                    case 7:
                        mPhotoFile3 = photoFile;
                        break;
                }


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestTakePhoto);
            }
        }
    }*/


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case ADDBUSINESS:
                break;

            case GETPROSUCTCATEGORIESLIST:
                break;

            case GETPROSUCTUPLOADIMAGE:
                break;

            case EDIT_PRODUCT:
                break;

            case GETPROSUCTUPLOADSUBIMAGE:
                break;
        }
    }

    @Override
    public void onResponse(Call call, Response backendResponse, int serviceMode) {
        String ApiResponse = "";
        if (backendResponse.body() != null) {
            ApiResponse = backendResponse.body().toString();
        }
        Log.d(TAG, "onResponse: " + ApiResponse.toString());
        switch (serviceMode) {

           /* case GETPROSUCTUPLOADIMAGE:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        uploadedImageName = jsonObject.getString("result");

                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        uploadSubImage();
                    } else {
                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;*/

            case GETPROSUCTUPLOADSUBIMAGE:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Log.d(TAG, "onResponse: " + jsonObject.getString("result"));
                        // jsonSubImageArray = new JSONArray(jsonObject.getString("result"));
                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        editProduct();
                    } else {
                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case GETPROSUCTCATEGORIESLIST:
                //getProductSizeList();
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainProductCategoryResponse response = new Gson().fromJson(jsonObject.toString(), MainProductCategoryResponse.class);
                    if (response.getStatus().equals("1")) {
                        //Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {

                                product_type_list.clear();
                                ProductTypeModel category1 = new ProductTypeModel();
                                category1.setProductCatName("Select product type");
                                product_type_list.add(category1);
                                product_type_list.addAll(response.getResult());

                                categoryAdapter = new ProductCategoryAdapter(EditProductActivity.this, 0,
                                        product_type_list);
                                spiner_category.setAdapter(categoryAdapter);

                                sub_category_list.clear();
                                ProductSubCategory subcategory1 = new ProductSubCategory();
                                subcategory1.setProductCatName("Select Sub Category");
                                sub_category_list.add(subcategory1);
                                ProductSubCategory subcategory2 = new ProductSubCategory();
                                subcategory2.setProductCatName("Interior Designer & Decorator");
                                subcategory2.setProductCatId("26");
                                sub_category_list.add(subcategory2);

                                ProductSubCategory subcategory3 = new ProductSubCategory();
                                subcategory3.setProductCatName("Roofing Contractors");
                                subcategory3.setProductCatId("35");
                                sub_category_list.add(subcategory3);

                                ProductSubCategory subcategory4 = new ProductSubCategory();
                                subcategory4.setProductCatName("Carpenters");
                                subcategory4.setProductCatId("27");
                                sub_category_list.add(subcategory4);

                                ProductSubCategory subcategory5 = new ProductSubCategory();
                                subcategory5.setProductCatName("Flooring Contractor");
                                subcategory5.setProductCatId("33");
                                sub_category_list.add(subcategory5);

                                ProductSubCategory subcategory6 = new ProductSubCategory();
                                subcategory6.setProductCatName("Gardening Tools Services");
                                subcategory6.setProductCatId("33");
                                sub_category_list.add(subcategory6);


                                subCategoryAdapter = new ProductSubCategoryAdapter(EditProductActivity.this, 0,
                                        sub_category_list);
                                spiner_sub_category.setAdapter(subCategoryAdapter);


                            }
                        }


                    } else {
                        Toast.makeText(EditProductActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case EDIT_PRODUCT:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PRODUCT_SIZE:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainProductSizeResponse response = new Gson().fromJson(jsonObject.toString(), MainProductSizeResponse.class);
                    if (response.getStatus().equals("1")) {
                        //Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {

                                product_size_list.clear();
                                ProductSizeModel category1 = new ProductSizeModel();
                                category1.setSizeTypeName("Select size");
                                product_size_list.add(category1);
                                product_size_list.addAll(response.getResult());

                                productSizeMultipleAdapter = new ProductSizeMultipleAdapter(EditProductActivity.this, 0,
                                        product_size_list);
                                //spinner_size.setAdapter(productSizeMultipleAdapter);

                            }
                        }


                    } else {
                        Toast.makeText(EditProductActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            if (ll_screen_2.getVisibility() == View.VISIBLE) {
                screen1();
            } else if (ll_screen_3.getVisibility() == View.VISIBLE) {
                screenPre2();
            } else if (ll_screen_4.getVisibility() == View.VISIBLE) {
                screen3();
            } else {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ll_screen_2.getVisibility() == View.VISIBLE) {
            screen1();
        } else if (ll_screen_3.getVisibility() == View.VISIBLE) {
            screenPre2();
        } else if (ll_screen_4.getVisibility() == View.VISIBLE) {
            screen3();
        } else {
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spiner_category:
                if (product_type_list.size() > 0) {
                    productTypeModel = product_type_list.get(position);
                    if (productTypeModel.getProductCatName().equals("Select product type")) {
                        productTypeModel = null;
                    }
                }
                break;
            case R.id.spiner_sub_category:
                if (sub_category_list.size() > 0) {
                    productSubCategory = sub_category_list.get(position);
                    if (productSubCategory.getProductCatName().equals("Select Sub Category")) {
                        productSubCategory = null;
                    }
                }
                break;

            case R.id.spiner_single_size:
                if (product_size_list.size() > 0) {
                    productSizeModel = product_size_list.get(position);
                    if (productSizeModel.getSizeTypeName().equals("Select size")) {
                        productSizeModel = null;
                    }
                }
                break;

            case R.id.spinner_tax_rate:
                if (product_tax_rate_list.size() > 0) {
                    productTaxRateModel = product_tax_rate_list.get(position);
                    if (productTaxRateModel.getTxRate().equals("Select rate")) {
                        productTaxRateModel = null;
                    }
                }
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPhotoFile != null)
            outState.putString("AStringKey", mPhotoFile.getAbsolutePath());
        if (mPhotoFile1 != null)
            outState.putString("AStringKey1", mPhotoFile1.getAbsolutePath());
        if (mPhotoFile2 != null)
            outState.putString("AStringKey2", mPhotoFile2.getAbsolutePath());
        if (mPhotoFile3 != null)
            outState.putString("AStringKey3", mPhotoFile3.getAbsolutePath());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPhotoFile = new File(Objects.requireNonNull(savedInstanceState.getString("AStringKey")));
        mPhotoFile1 = new File(Objects.requireNonNull(savedInstanceState.getString("AStringKey1")));
        mPhotoFile2 = new File(Objects.requireNonNull(savedInstanceState.getString("AStringKey2")));
        mPhotoFile3 = new File(Objects.requireNonNull(savedInstanceState.getString("AStringKey3")));

    }*/
}

