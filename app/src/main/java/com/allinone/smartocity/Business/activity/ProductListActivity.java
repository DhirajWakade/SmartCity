package com.allinone.smartocity.Business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.Business.activity.addproduct.AddProductActivity;
import com.allinone.smartocity.Business.adapter.BusinessCategoryAdapter;
import com.allinone.smartocity.Business.adapter.ProductListAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
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
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.GETPRODUCTLISTPAGNATION;
import static com.allinone.smartocity.retrofit.Constant.SHARED_HOME_API_DETAILS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class ProductListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick, AdapterView.OnItemSelectedListener, View.OnClickListener {


    private String TAG = "BusinessListActivity";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private MainLoginResponse mainLoginResponse = null;

    private ArrayList<ProductDetail> product_list;
    private FloatingActionButton fab;
    private EditText edt_product_search;
    private BusinessListResponse businessListResponse = null;
    private LinearLayout layout_empty, ll_product_search;
    //private Spinner spinner_business_category;
    private NoPaginate noPaginate;
    private int pagination = 0;
    private Spinner spinner_business_category;
    private RelativeLayout rl_businesscategory;
    private String type = "";
    private ArrayList<BusinessListResponse> business_list;

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
        fab.setOnClickListener(this);

        spinner_business_category.setOnItemSelectedListener(this);
        edt_product_search.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("-----", "end");
                   // noPaginate.setNoMoreItems(false);

                }
            }
        });
    }

    private void searchProduct(String businessId) {

        if (InternetConnection.checkConnection(ProductListActivity.this)) {
            searchProductAPI(businessId);
        } else {
            Toast.makeText(ProductListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }

    }

    private void setData() {


    }


    private void setupPagination() {
        noPaginate = NoPaginate.with(recyclerView)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        //http or db request
                        pagination++;
                        searchProductAPIPagination();
                    }
                })
                .setLoadingTriggerThreshold(3)
                .build();
    }


    private void initializeWidgets() {
        layout_empty = findViewById(R.id.support_layout);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        spinner_business_category = findViewById(R.id.spinner_business_category);
        ll_product_search = findViewById(R.id.ll_product_search);
        edt_product_search = findViewById(R.id.edt_product_search);
        rl_businesscategory = findViewById(R.id.rl_businesscategory);
        edt_product_search.setFocusable(false);

    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);



        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            rl_businesscategory.setVisibility(View.GONE);
            businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
            setSearchVisibility();
        } else {

            rl_businesscategory.setVisibility(View.VISIBLE);
            String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");

            MainResponseBusinessHomeModel mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);
            business_list = new ArrayList<>();

           /* BusinessListResponse response1 = new BusinessListResponse();
            response1.setBusinessName("Select Business Category");
            business_list.add(response1);*/
            if (mainResponseBusinessHomeModel.getBusinessDetailsList().size() > 0)
                business_list.addAll(mainResponseBusinessHomeModel.getBusinessDetailsList());


            BusinessCategoryAdapter businessAdapter = new BusinessCategoryAdapter(ProductListActivity.this, 0,
                    business_list);
            spinner_business_category.setAdapter(businessAdapter);


        }
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        product_list = new ArrayList<>();


        productListAdapter = new ProductListAdapter(ProductListActivity.this, product_list, this);
        recyclerView.setAdapter(productListAdapter);

        swipeRefresh.setVisibility(View.GONE);


    }


    private void setSearchVisibility() {


        if (businessListResponse != null)
            if (businessListResponse.getBusinessTypes().getIsProductSearchAllow().equals("YES")) {
                ll_product_search.setVisibility(View.VISIBLE);
            } else {
                ll_product_search.setVisibility(View.GONE);
            }
    }

    private void searchProductAPI(String businessId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_product", "oi");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());

        Call<String> product_search = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductListPaginationDetails(headers, businessId, String.valueOf(pagination));
        product_search.enqueue(new RestProcess<String>(ProductListActivity.this, ProductListActivity.this, GETPRODUCTLISTPAGNATION, true));


    }

    private void searchProductAPIPagination() {
        noPaginate.showLoading(true);
        noPaginate.showError(false);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("search_product", "oil");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());

        Call<String> product_search = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductListPaginationDetails(headers, businessListResponse.getBusinessId(), String.valueOf(pagination));
        product_search.enqueue(new RestProcess<String>(ProductListActivity.this, ProductListActivity.this, GETPRODUCTLISTPAGNATION, false));


    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case GETPRODUCTLISTPAGNATION:
                //noPaginate.showLoading(false);
              //  noPaginate.showError(true);
              //  noPaginate.setNoMoreItems(true);
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
            case GETPRODUCTLISTPAGNATION:
               // noPaginate.showLoading(false);
               // noPaginate.setNoMoreItems(true);
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    ProductListMainResponse response = new Gson().fromJson(jsonObject.toString(), ProductListMainResponse.class);
                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getProductDetails() != null) {
                            if (response.getProductDetails().size() > 0) {
                                layout_empty.setVisibility(View.GONE);
                                product_list.clear();
                                product_list.addAll(response.getProductDetails());
                                productListAdapter.updateProductList(product_list);

                                swipeRefresh.setVisibility(View.VISIBLE);
                            }
                        }


                    } else {
                       // noPaginate.showLoading(false);
                       // noPaginate.showError(false);
                       // noPaginate.setNoMoreItems(true);
                        layout_empty.setVisibility(View.GONE);
                        swipeRefresh.setVisibility(View.VISIBLE);
                        Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
        if (type.equals("1")) {
            searchProduct(businessListResponse.getBusinessId());
        }

      //  setupPagination();
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
        if (type.equals("edit")) {
            ProductDetail response = product_list.get(pos);
            Intent mainIntent = new Intent(ProductListActivity.this, EditProductActivity.class);
            mainIntent.putExtra("ProductMaster", response);
            mainIntent.putExtra("BusinessResponse", businessListResponse);
            startActivity(mainIntent);
            finish();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_business_category:
                if (business_list.size() > 0) {
                    BusinessListResponse businessListResponse = business_list.get(position);
                    searchProduct(businessListResponse.getBusinessId());
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent mainIntent = new Intent(ProductListActivity.this, AddProductActivity.class);
                mainIntent.putExtra("BusinessResponse", businessListResponse);
                startActivity(mainIntent);
                finish();
                break;

            case R.id.edt_product_search:
                Intent mainIntent11 = new Intent(ProductListActivity.this, SearchProductListActivity.class);
                mainIntent11.putExtra("BusinessResponse", businessListResponse);
                mainIntent11.putExtra("type", "1");
                startActivity(mainIntent11);
                finish();
                break;
        }
    }


    @Override
    public void onDestroy() {
       // noPaginate.unbind();
        super.onDestroy();
    }
}