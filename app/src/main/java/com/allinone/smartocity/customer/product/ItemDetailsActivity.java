package com.allinone.smartocity.customer.product;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.adaptor.ColorAdapter;
import com.allinone.smartocity.customer.fragments.ViewPagerActivity;
import com.allinone.smartocity.model.product.FilterColorPojo;
import com.allinone.smartocity.notification.NotificationCountSetClass;
import com.allinone.smartocity.customer.options.BusinessCartListActivity;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_ADD_CART_LIST;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class ItemDetailsActivity extends AppCompatActivity implements RestCalllback, OnitemClick {

    private MainLoginResponse mainLoginResponse;
    private String TAG = "ItemDetailsActivity.class";
    private ProductDetail productDetail;
    private EditText txtNumbers;
    private TextView txt_price,text_action_bottom;
    private RecyclerView rvSize;
    private ColorAdapter colorAdapter;
    private FilterColorPojo colorPojo = null;
    private ArrayList<FilterColorPojo> colorList;
    private LinearLayout ll_plus_minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        initializeWidgets();
        initilizeData();
        updatePrice();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        SimpleDraweeView mImageView = (SimpleDraweeView) findViewById(R.id.image1);

        TextView textViewBuyNow = (TextView) findViewById(R.id.text_action_bottom2);
        ImageView imgMinus = findViewById(R.id.imgMinus);

        ImageView imgPlus = findViewById(R.id.imgPlus);


        Uri uri = Uri.parse(BASE_URL + "productImages/" + productDetail.getProductMaster().getProductImageUrl());
        mImageView.setImageURI(uri);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);

            }
        });

        text_action_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_plus_minus.setVisibility(View.VISIBLE);
                text_action_bottom.setVisibility(View.GONE);
                if (InternetConnection.checkConnection(ItemDetailsActivity.this)) {
                    addtocart();
                } else {
                    Toast.makeText(ItemDetailsActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                }


            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(BASE_URL + "productImages/" + productDetail.getProductMaster().getProductImageUrl());
                MainCustomerActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainCustomerActivity.notificationCountCart);*/
                startActivity(new Intent(ItemDetailsActivity.this, BusinessCartListActivity.class));

            }
        });


        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNumbers.getText().toString().trim().length() >= 1) {
                    txtNumbers.setText("" + (Integer.parseInt(txtNumbers.getText().toString().trim()) - 1));
                    updatePrice();


                }
            }
        });


        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNumbers.getText().toString().trim().length() >= 1) {
                    txtNumbers.setText("" + (Integer.parseInt(txtNumbers.getText().toString().trim()) + 1));
                    updatePrice();

                    if (InternetConnection.checkConnection(ItemDetailsActivity.this)) {
                        addtocart();
                    } else {
                        Toast.makeText(ItemDetailsActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        txtNumbers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }

                if (s.toString().length() == 0) {
                    txtNumbers.setText("1");
                }

            }
        });


    }

    private void updatePrice() {

        String quantity = txtNumbers.getText().toString().trim();
        txt_price.setText("Rs." + (productDetail.getPrice() * Integer.parseInt(quantity)));


    }

    private void initializeWidgets() {
        txt_price = findViewById(R.id.txt_price);
        txtNumbers = findViewById(R.id.txtNumbers);
        ll_plus_minus = findViewById(R.id.ll_plus_minus);
        text_action_bottom = findViewById(R.id.text_action_bottom);

        rvSize = (RecyclerView) findViewById(R.id.rv_color);
        colorList = new ArrayList<>();
    }

    private void initilizeData() {
        productDetail = (ProductDetail) getIntent().getSerializableExtra("ProductDetails");

        FilterColorPojo colorPojo = new FilterColorPojo();
        colorPojo.setColorName("Pink");
        colorList.add(colorPojo);
        FilterColorPojo colorPojo1 = new FilterColorPojo();
        colorPojo1.setColorName("Yellow");
        colorList.add(colorPojo1);
        FilterColorPojo colorPojo2 = new FilterColorPojo();
        colorPojo2.setColorName("Blue");
        colorList.add(colorPojo2);
        rvSize.setHasFixedSize(true);
        rvSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        colorAdapter = new ColorAdapter(this, colorList, this);
        rvSize.setAdapter(colorAdapter);
        rvSize.setItemAnimator(new DefaultItemAnimator());
    }

  /*  private void addtocart() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(UtilsConstants.KEY_PR_COMPID, "");
        hashMap.put(UtilsConstants.KEY_PR_USERNAME, mainLoginResponse.getResult().getBmId());
        hashMap.put(UtilsConstants.KEY_PR_PASS, "");
      *//*  hashMap.put(UtilsConstants.KEY_PR_ID, data.getString("product_id"));
        hashMap.put(UtilsConstants.KEY_PR_NAME, data.getString("product_name"));
        hashMap.put(UtilsConstants.KEY_PR_MRP, data.getString("mrp"));
        hashMap.put(UtilsConstants.KEY_PR_CAT, data.getString("category"));
        hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, data.getString("sub_category"));
        hashMap.put(UtilsConstants.KEY_PR_BRAND, data.getString("brand"));
        hashMap.put(UtilsConstants.KEY_PR_PACK, data.getString("pack"));
        hashMap.put(UtilsConstants.KEY_PR_DISC, data.getString("description"));
        hashMap.put(UtilsConstants.KEY_PR_IMGURL, data.getString("img_url"));*//*

        hashMap.put(UtilsConstants.KEY_PR_ID, "product_id");
        hashMap.put(UtilsConstants.KEY_PR_NAME, "product_name");
        hashMap.put(UtilsConstants.KEY_PR_MRP, "mrp");
        hashMap.put(UtilsConstants.KEY_PR_CAT, "category");
        hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, "sub_category");
        hashMap.put(UtilsConstants.KEY_PR_BRAND, "brand");
        hashMap.put(UtilsConstants.KEY_PR_PACK, "pack");
        hashMap.put(UtilsConstants.KEY_PR_DISC, "description");
        hashMap.put(UtilsConstants.KEY_PR_IMGURL, stringImageUri);

        hashMap.put(UtilsConstants.KEY_PR_FAV, "0");
        hashMap.put(UtilsConstants.KEY_PR_SYNCSTATUS, "2");
        OpenHelper openHelper = new OpenHelper(ItemDetailsActivity.this);
        openHelper.addProduct(hashMap);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(ItemDetailsActivity.this, MainCustomerDashboardActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(ItemDetailsActivity.this, MainCustomerDashboardActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    private void addtocart() {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObjectcustomer = new JSONObject();
            jsonObjectcustomer.put("customerId", Integer.parseInt(mainLoginResponse.getResult().getCustomerId()));
            JSONObject jsonObjectproductDetail = new JSONObject();
            jsonObjectproductDetail.put("productDetailId", productDetail.getProductDetailId());
            jsonObject.put("productDetail", jsonObjectproductDetail);
            jsonObject.put("customer", jsonObjectcustomer);
            jsonObject.put("quantity", Integer.parseInt(txtNumbers.getText().toString().trim()));
            String totalamount = String.valueOf(Integer.parseInt(txtNumbers.getText().toString().trim()) * productDetail.getPrice());

            jsonObject.put("totalAmount", Double.parseDouble(totalamount));


        } catch (Exception e) {
            e.printStackTrace();
        }

       /* Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());*/
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerAddToCart(jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(ItemDetailsActivity.this, ItemDetailsActivity.this, CUSTOMER_ADD_CART_LIST, true));
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CUSTOMER_ADD_CART_LIST:
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
            case CUSTOMER_ADD_CART_LIST:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);

                    //now you have Pojo do whatever

                    if (jsonObject.getString("status").equals("1")) {

                        Toast.makeText(ItemDetailsActivity.this, "Item added to cart.", Toast.LENGTH_SHORT).show();
                        MainCustomerDashboardActivity.notificationCountCart++;
                        NotificationCountSetClass.setNotifyCount(MainCustomerDashboardActivity.notificationCountCart);
                        // JSONObject resultjson = jsonObject.getJSONObject("result");
                      /*  mainResponseCustomerHomeModel = new Gson().fromJson(resultjson.toString(), MainResponseCustomerHomeModel.class);
                        SharedPref.write(SHARED_HOME_API_DETAILS, new Gson().toJson(mainResponseCustomerHomeModel));
                        updateHomeAPIData();*/
                    } else {
                        Toast.makeText(ItemDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    @Override
    public void itemClick(int position, String hint) {
        if (hint.equals("color")) {
            if (colorList.size() > 0) {
                ArrayList<FilterColorPojo> sizeList = colorList;
                for (int i = 0; i < sizeList.size(); i++) {
                    FilterColorPojo colorPojo = sizeList.get(i);
                    if (i == position) {
                        colorPojo.setSelectedPos(1);
                        this.colorPojo = colorPojo;
                    } else {
                        colorPojo.setSelectedPos(0);
                    }
                    sizeList.set(i, colorPojo);
                }
                colorAdapter.update(sizeList);
            }
        }
    }
}
