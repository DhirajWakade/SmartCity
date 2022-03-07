package com.allinone.smartocity.Business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.Business.activity.addproduct.AddProductActivity;
import com.allinone.smartocity.Business.adapter.ProductListAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.product.ItemDetailsActivity;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.model.product.ProductListMainResponse;
import com.allinone.smartocity.retrofit.*;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.SEARCH_PRODUCT;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class SearchProductListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick, AdapterView.OnItemSelectedListener {


    private String TAG = "BusinessListActivity";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private MainLoginResponse mainLoginResponse = null;

    private ArrayList<ProductDetail> product_list;
    private FloatingActionButton fab;
    private EditText edt_product_search;

    private LinearLayout layout_empty;
    private BusinessListResponse businessListResponse = null;
    private LinearLayout ll_product_search;
    private RelativeLayout rl_businesscategory;
    private String type="";
    //private Spinner spinner_business_category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchProductListActivity.this, AddProductActivity.class);
                mainIntent.putExtra("BusinessResponse", businessListResponse);
                startActivity(mainIntent);

            }
        });

        // spinner_business_category.setOnItemSelectedListener(this);
        edt_product_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_product_search.getRight() - edt_product_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //searchProduct();
                        //  BusinessListResponse response = business_list.get(pos);
                        searchProduct();
                        return true;
                    }
                }
                return false;
            }
        });

        edt_product_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0 && s.length() > 2)
                    searchProduct();
            }
        });


    }

    private void searchProduct() {
        if (!edt_product_search.getText().toString().trim().equals("")) {
            if (InternetConnection.checkConnection(SearchProductListActivity.this)) {
                searchProductAPI();
            } else {
                Toast.makeText(SearchProductListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(SearchProductListActivity.this, "Please enter search product details", Toast.LENGTH_SHORT).show();

        }
    }

    private void setData() {

    }

    private void initializeWidgets() {
        layout_empty = findViewById(R.id.support_layout);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        // spinner_business_category = findViewById(R.id.spinner_business_category);
        edt_product_search = findViewById(R.id.edt_product_search);
        ll_product_search = findViewById(R.id.ll_product_search);
        fab.setVisibility(View.GONE);
        ll_product_search.setVisibility(View.VISIBLE);
        rl_businesscategory = findViewById(R.id.rl_businesscategory);

    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        type = getIntent().getStringExtra("type");
        if (type.equals("1"))
            rl_businesscategory.setVisibility(View.GONE);
         else
            rl_businesscategory.setVisibility(View.VISIBLE);
        businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        product_list = new ArrayList<>();


        productListAdapter = new ProductListAdapter(SearchProductListActivity.this, product_list, this);
        recyclerView.setAdapter(productListAdapter);
        layout_empty.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.GONE);

    }


    private void searchProductAPI() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_product", edt_product_search.getText().toString().trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());

        Call<String> product_search = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getSearchProductDetails(edt_product_search.getText().toString().trim());
        product_search.enqueue(new RestProcess<String>(SearchProductListActivity.this, SearchProductListActivity.this, SEARCH_PRODUCT, true));


    }

    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case SEARCH_PRODUCT:
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
            case SEARCH_PRODUCT:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    ProductListMainResponse response = new Gson().fromJson(jsonObject.toString(), ProductListMainResponse.class);
                    if (response.getStatus().equals("1")) {
                        Toast.makeText(SearchProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getProductDetails() != null) {
                            if (response.getProductDetails().size() > 0) {

                                product_list.clear();
                                product_list.addAll(response.getProductDetails());
                               /*
                                BusinessListResponse response1 = new BusinessListResponse();
                                response1.setBusinessName("Select Business Category");
                                business_list.add(response1);
                                business_list.addAll(response.getProductDetails());
                                 product_list.addAll(response.getProductDetails());

                                BusinessCategoryAdapter businessAdapter = new BusinessCategoryAdapter(ProductListActivity.this, 0,
                                        business_list);
                                // spinner_business_category.setAdapter(businessAdapter);*/

                                productListAdapter.updateProductList(product_list);
                                layout_empty.setVisibility(View.GONE);
                                swipeRefresh.setVisibility(View.VISIBLE);
                            }
                        }


                    } else {
                        layout_empty.setVisibility(View.VISIBLE);
                        swipeRefresh.setVisibility(View.GONE);
                        Toast.makeText(SearchProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


    @Override
    public void onRefresh() {

        swipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClick(int pos, String type) {
        ProductDetail response = product_list.get(pos);
        Intent mainIntent = new Intent(SearchProductListActivity.this, ItemDetailsActivity.class);
        mainIntent.putExtra("ProductDetails", response);
        mainIntent.putExtra("BusinessResponse", businessListResponse);
        startActivity(mainIntent);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_business_category:
               /* if (business_list.size() > 0) {
                    BusinessListResponse businessListResponse = business_list.get(position);
                    if (!businessListResponse.getBusinessName().equals("Select Business Category")) {
                        product_list.clear();
                        product_list.addAll(businessListResponse.getProductDetails());
                        productListAdapter.updateProductList(product_list);
                    } else {
                        product_list.clear();
                        productListAdapter.updateProductList(product_list);
                    }
                }*/
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
