package com.allinone.smartocity.Business.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.ProductMaster;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.util.*;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.ADDPRODUCT;

import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class AddProductWithSearchActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback {

    private String TAG = "AddProductWithSearchActivity";
    private Button btn_submit_product;
    private MainLoginResponse mainLoginResponse = null;


    private ImageView image_product;


    private EditText
            edt_product_price, edt_product_tax_rate, edt_product_final_price, edt_product_available_quantity, edt_product_description;
    private String product_price = "", product_available_quantity = "",
            product_description = "", product_tax_rate = "", product_final_price = "";
    private ProductMaster productMaster;
    private TextView txt_product_name, txt_color, txt_size, txt_type, txt_brand, txt_description;
    private BusinessListResponse businessListResponse = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_with_search);
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
    }

    private void setData() {
        txt_product_name.setText(productMaster.getProductName() + "(" + productMaster.getProductCode() + ")");
        txt_color.setText(productMaster.getProductColor());
        txt_size.setText("" + productMaster.getProductSize());
        txt_type.setText(productMaster.getProductTypes().getProductCatName());
        txt_brand.setText(productMaster.getProductBrand());
        txt_description.setText(productMaster.getDesciption());
        Glide.with(AddProductWithSearchActivity.this)
                .load(BASE_URL +"productImages/" + productMaster.getProductImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.place_holder))
                .into(image_product);
    }


    private void initializeWidgets() {
        edt_product_price = findViewById(R.id.edt_product_price);
        edt_product_tax_rate = findViewById(R.id.edt_product_tax_rate);
        edt_product_final_price = findViewById(R.id.edt_product_final_price);
        edt_product_available_quantity = findViewById(R.id.edt_product_available_quantity);
        edt_product_description = findViewById(R.id.edt_product_description);
        btn_submit_product = findViewById(R.id.btn_submit_product);
        image_product = findViewById(R.id.image_produc);
        txt_product_name = findViewById(R.id.txt_product_name);
        txt_color = findViewById(R.id.txt_color);
        txt_size = findViewById(R.id.txt_size);
        txt_type = findViewById(R.id.txt_type);
        txt_brand = findViewById(R.id.txt_brand);
        txt_description = findViewById(R.id.txt_description);
    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
        productMaster = (ProductMaster) getIntent().getSerializableExtra("ProductMaster");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_product:
                if (Valid()) {
                    if (InternetConnection.checkConnection(AddProductWithSearchActivity.this)) {
                        addProduct();
                    } else {
                        Toast.makeText(AddProductWithSearchActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }

                }
                break;


        }
    }


    private void addProduct() {

        String abc = new Gson().toJson(productMaster);

        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObjec = new JSONObject(abc);
            JSONObject businessDetails = new JSONObject();
            businessDetails.put("businessId", Integer.parseInt(businessListResponse.getBusinessId()));
            jsonObject.put("price", Integer.parseInt(product_price));
            jsonObject.put("availableQty", Integer.parseInt(product_available_quantity));
            jsonObject.put("businessDetails", businessDetails);
            jsonObject.put("finalPrice", Integer.parseInt(product_final_price));
            jsonObject.put("taxRate", Integer.parseInt(product_tax_rate));
            jsonObject.put("productMaster", jsonObjec);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getAddProductDetails(headers, jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(AddProductWithSearchActivity.this, AddProductWithSearchActivity.this, ADDPRODUCT, true));

    }


    private boolean Valid() {

        product_price = edt_product_price.getText().toString().trim();
        product_available_quantity = edt_product_available_quantity.getText().toString().trim();

        product_description = edt_product_description.getText().toString().trim();
        product_tax_rate = edt_product_tax_rate.getText().toString().trim();
        product_final_price = edt_product_final_price.getText().toString().trim();
        if (product_price.equals("")) {
            Toast.makeText(AddProductWithSearchActivity.this, "Please enter product price", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (product_available_quantity.equals("")) {
            Toast.makeText(AddProductWithSearchActivity.this, "Please enter product available quantity", Toast.LENGTH_SHORT).show();
            return false;
        }


        /*if (product_description.equals("")) {
            Toast.makeText(AddProductWithSearchActivity.this, "Please enter product description", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {


            case ADDPRODUCT:
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
            case ADDPRODUCT:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(AddProductWithSearchActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddProductWithSearchActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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


}


