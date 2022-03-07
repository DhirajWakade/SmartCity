package com.allinone.smartocity.Business.activity.addproduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.allinone.smartocity.BuildConfig;
import com.allinone.smartocity.Business.activity.EditProductActivity;
import com.allinone.smartocity.Business.activity.ProductListActivity;
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
import com.allinone.smartocity.util.FileCompressor;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.Manifest.permission.*;
import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.*;
import static com.allinone.smartocity.retrofit.Constant.SHARED_HOME_API_DETAILS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;


public class AddProductActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback, AdapterView.OnItemSelectedListener {

    private String TAG = "AddBusinessActivity";
    private Button btn_submit_product;

    private Spinner spiner_category, spinner_tax_rate, spinner_countryorigin, spiner_sub_category;
    private ArrayList<ProductTypeModel> product_type_list;

    private ArrayList<TaxRatesItemModel> product_tax_rate_list;
    private ArrayList<ProductSubCategory> sub_category_list;
    private ProductCategoryAdapter categoryAdapter;
    private MainLoginResponse mainLoginResponse = null;
    private LinearLayout
            parentLinearLayout_keyword;

    private ProductSubCategoryAdapter subCategoryAdapter;
    private int count = 0;
    private ProductTypeModel productTypeModel = null;
    private ProductSubCategory productSubCategory = null;


    private EditText edt_product_title, edt_manufacturer_Name, edt_product_brand,
            edt_product_actual_price, edt_product_final_price, edt_product_available_quantity,
            edt_product_max_order_quantity, edt_product_description, edt_encluse_material, edt_keyword, edt_refund_days, edt_product_hsncode;
    private String product_title = "", product_brand = "", manufacturer_Name = "",
            product_actual_price = "", product_final_price = "", product_available_quantity = "", product_max_order_quantity = "",
            product_description = "", encluse_material = "", keyword = "";


    private MainResponseBusinessHomeModel mainResponseBusinessHomeModel = null;
    private BusinessListResponse businessListResponse = null;


    ;


    private ProductTaxRateAdapter pproductTaxRateAdapter;
    private TaxRatesItemModel productTaxRateModel = null;
    private int taxrate = 0;
    private RadioGroup radioGrp;
    private RadioButton radioButton;


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

        spiner_category.setOnItemSelectedListener(this);

        spiner_sub_category.setOnItemSelectedListener(this);
        spinner_tax_rate.setOnItemSelectedListener(this);
        edt_product_actual_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0) {
                    if (productTaxRateModel != null) {

                        int actual_prize = Integer.parseInt(edt_product_actual_price.getText().toString().trim());
                        taxrate = (actual_prize * Integer.parseInt(productTaxRateModel.getTxRate()) / 100);
                        String finalprize = String.valueOf(actual_prize + taxrate);
                        edt_product_final_price.setText(finalprize);
                    } else {
                        edt_product_final_price.setText(taxrate);
                    }
                }
            }
        });


        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if (rb.getText().equals("Y")) {
                    edt_refund_days.setVisibility(View.VISIBLE);
                } else {
                    edt_refund_days.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setData() {

        for (int i = 0; i < mainResponseBusinessHomeModel.getBusinessTypesWithProductType().size(); i++) {

            BusinessTypeModel typeModel = mainResponseBusinessHomeModel.getBusinessTypesWithProductType().get(i);

            if (businessListResponse.getBusinessTypes().getBusinessTypeId().equals(typeModel.getBusinessTypeId())) {
                setProductTypeList(typeModel);

            }
        }

        setProductTaxRateList(mainResponseBusinessHomeModel.getTaxRates());


    }


    private void setProductTaxRateList(List<TaxRatesItemModel> rateList) {
        product_tax_rate_list.clear();
        TaxRatesItemModel category1 = new TaxRatesItemModel();
        category1.setTxRate("Select rate");
        product_tax_rate_list.add(category1);
        if (rateList.size() > 0)
            product_tax_rate_list.addAll(rateList);


        pproductTaxRateAdapter = new ProductTaxRateAdapter(AddProductActivity.this, 0, product_tax_rate_list);
        spinner_tax_rate.setAdapter(pproductTaxRateAdapter);

    }

    private void setProductTypeList(BusinessTypeModel typeModel) {
        product_type_list.clear();
        ProductTypeModel category1 = new ProductTypeModel();
        category1.setProductCatName("Select product type");
        product_type_list.add(category1);
        if (typeModel.getProductTypes().size() > 0)
            product_type_list.addAll(typeModel.getProductTypes());

        categoryAdapter = new ProductCategoryAdapter(AddProductActivity.this, 0,
                product_type_list);
        spiner_category.setAdapter(categoryAdapter);
    }


    private void initializeWidgets() {

        spiner_category = findViewById(R.id.spiner_category);
        spiner_sub_category = findViewById(R.id.spiner_sub_category);

        edt_product_title = findViewById(R.id.edt_product_title);
        edt_manufacturer_Name = findViewById(R.id.edt_manufacturer_Name);
        edt_product_brand = findViewById(R.id.edt_product_brand);


        //  edt_product_sdler_sku = findViewById(R.id.edt_product_sdler_sku);
        edt_product_actual_price = findViewById(R.id.edt_product_actual_price);
        spinner_tax_rate = findViewById(R.id.spinner_tax_rate);
        spinner_countryorigin = findViewById(R.id.spinner_countryorigin);
        edt_product_final_price = findViewById(R.id.edt_product_final_pricee);
        edt_product_available_quantity = findViewById(R.id.edt_product_available_quantity);
        edt_product_max_order_quantity = findViewById(R.id.edt_product_max_order_quantity);
        edt_product_description = findViewById(R.id.edt_product_description);
        edt_encluse_material = findViewById(R.id.edt_encluse_material);
        edt_keyword = findViewById(R.id.edt_keyword);

        btn_submit_product = findViewById(R.id.btn_submit_product);

        radioGrp = findViewById(R.id.radioGrp);
        edt_refund_days = findViewById(R.id.edt_refund_days);
        edt_product_hsncode = findViewById(R.id.edt_product_hsncode);
        parentLinearLayout_keyword = findViewById(R.id.parentLinearLayout_keyword);


    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);
        businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
        product_type_list = new ArrayList<>();
        sub_category_list = new ArrayList<>();

        product_tax_rate_list = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_product:
                if (valid()) {
                    if (InternetConnection.checkConnection(AddProductActivity.this)) {
                        addProduct();
                    } else {
                        Toast.makeText(AddProductActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }
                break;


        }
    }


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


    private void addProduct() {

        JSONObject jsonObject = new JSONObject();
        try {

            int selectedId = radioGrp.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            radioButton = (RadioButton) findViewById(selectedId);

            JSONObject producttype = new JSONObject();
            producttype.put("productTypeId", Integer.parseInt(productTypeModel.getProductCatId()));
            //  jsonObject.put("productId", productTypeModel.getProductCatName());
            jsonObject.put("productTypes", producttype);
            jsonObject.put("title", product_title);
            jsonObject.put("brand", product_brand);
            jsonObject.put("manufacturer_Name", manufacturer_Name);
            jsonObject.put("hscCode", edt_product_hsncode.getText().toString());
            jsonObject.put("countryOfOrigin", spinner_countryorigin.getSelectedItem().toString());

            jsonObject.put("productMaterial", encluse_material);


            jsonObject.put("productDescription", product_description);

            JSONArray productKeywords = new JSONArray();
            JSONObject keywor = new JSONObject();
            keywor.put("keyword", keyword);
            productKeywords.put(keywor);
            jsonObject.put("productKeywords", productKeywords);
            JSONObject businessDetails = new JSONObject();
            businessDetails.put("businessId", Integer.parseInt(businessListResponse.getBusinessId()));
            jsonObject.put("MPR", Integer.parseInt(product_actual_price));
          //  jsonObject.put("availableQuantity", Integer.parseInt(product_available_quantity));
            jsonObject.put("businessDetails", businessDetails);
            jsonObject.put("dealPrice", Integer.parseInt(product_final_price));
            jsonObject.put("gstRate", Integer.parseInt(productTaxRateModel.getTxRate()));
            jsonObject.put("isProductReturnable", radioButton.getText());
            jsonObject.put("returnInDays", edt_refund_days.getText().toString().trim());
            jsonObject.put("WeightPerPrduct", "1kg");
            jsonObject.put("limitPerOrder", product_max_order_quantity);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getAddProductDetails(headers, jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(AddProductActivity.this, AddProductActivity.this, ADDPRODUCT, true));

    }


    private boolean valid() {
        product_title = edt_product_title.getText().toString().trim();
        manufacturer_Name = edt_manufacturer_Name.getText().toString().trim();
        product_brand = edt_product_brand.getText().toString().trim();
        if (productTypeModel == null) {
            Toast.makeText(AddProductActivity.this, "Please select type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_title.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product  title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (manufacturer_Name.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter manufacturer name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_brand.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product brand", Toast.LENGTH_SHORT).show();
            return false;
        }


        // product_sdler_sku = edt_product_sdler_sku.getText().toString().trim();
        product_actual_price = edt_product_actual_price.getText().toString().trim();

        product_final_price = edt_product_final_price.getText().toString().trim();

       /* if (product_sdler_sku.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product sku", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if (productTaxRateModel == null) {
            Toast.makeText(AddProductActivity.this, "Please select product tax rate", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (product_actual_price.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product actual price", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (product_final_price.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product final price", Toast.LENGTH_SHORT).show();
            return false;
        }

        product_available_quantity = edt_product_available_quantity.getText().toString().trim();
        product_max_order_quantity = edt_product_max_order_quantity.getText().toString().trim();
        product_description = edt_product_description.getText().toString().trim();
        keyword = edt_keyword.getText().toString().trim();
        encluse_material = edt_encluse_material.getText().toString().trim();

       /* if (productSubCategory == null) {
            Toast.makeText(AddProductActivity.this, "Please select Sub Category", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (product_available_quantity.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product available quantity", Toast.LENGTH_SHORT).show();
            return false;
        }
*/

        if (product_max_order_quantity.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product max order", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (encluse_material.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter material", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (product_description.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter product description", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (keyword.equals("")) {
            Toast.makeText(AddProductActivity.this, "Please enter keyword", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case ADDBUSINESS:
                break;

            case GETPROSUCTCATEGORIESLIST:
                break;

            case GETPROSUCTUPLOADIMAGE:
                break;

            case ADDPRODUCT:
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

                                categoryAdapter = new ProductCategoryAdapter(AddProductActivity.this, 0,
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


                                subCategoryAdapter = new ProductSubCategoryAdapter(AddProductActivity.this, 0,
                                        sub_category_list);
                                spiner_sub_category.setAdapter(subCategoryAdapter);


                            }
                        }


                    } else {
                        Toast.makeText(AddProductActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case ADDPRODUCT:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("result"));
                        String productDetailId = jsonObject1.getString("productDetailId");
                        Intent mainIntent = new Intent(AddProductActivity.this, UpdateClothesProductActivity.class);
                        mainIntent.putExtra("productDetailsId", productDetailId);
                        mainIntent.putExtra("BusinessResponse", businessListResponse);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(AddProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

            finish();

        }

        return super.onOptionsItemSelected(item);
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


            case R.id.spinner_tax_rate:
                if (product_tax_rate_list.size() > 0) {
                    productTaxRateModel = product_tax_rate_list.get(position);
                    if (productTaxRateModel.getTxRate().equals("Select rate")) {
                        productTaxRateModel = null;
                        edt_product_final_price.setText("0");
                    } else {
                        if (edt_product_actual_price.getText().toString().trim().length() != 0) {
                            int actual_prize = Integer.parseInt(edt_product_actual_price.getText().toString().trim());
                            taxrate = (actual_prize * Integer.parseInt(productTaxRateModel.getTxRate()) / 100);
                            String finalprize = String.valueOf(actual_prize + taxrate);
                            edt_product_final_price.setText(finalprize);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

