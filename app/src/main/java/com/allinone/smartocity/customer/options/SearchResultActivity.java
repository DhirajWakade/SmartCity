package com.allinone.smartocity.customer.options;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.adaptor.CustomerProductListAdapter;
import com.allinone.smartocity.customer.product.ItemDetailsActivity;
import com.allinone.smartocity.customer.utility.ImageUrlUtils;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.model.product.ProductListMainResponse;
import com.allinone.smartocity.retrofit.*;
import com.allinone.smartocity.util.GridSpacingItemDecoration;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Objects;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.GETPRODUCTLISTPAGNATION;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;


public class SearchResultActivity extends AppCompatActivity implements RestCalllback, OnitemClick, View.OnClickListener {

    private String TAG = "SearchResultActivity";
    private RecyclerView recyclerView;
    private CustomerProductListAdapter productListAdapter;
    // private MainLoginResponse mainLoginResponse = null;

    private ArrayList<ProductDetail> product_list;
    private int pagination = 0;
    private EditText edt_product_search;
    private Button btn_search;
    private LinearLayout ll_display, ll_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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
       /* edt_product_search.setOnTouchListener(new View.OnTouchListener() {
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
        });*/
        btn_search.setOnClickListener(this);
    }


    private void setData() {
        ll_display.setVisibility(View.VISIBLE);
        ll_list.setVisibility(View.GONE);

    }


    private void initializeWidgets() {
        recyclerView = findViewById(R.id.recyclerview);
        ll_display = findViewById(R.id.ll_display);
        ll_list = findViewById(R.id.ll_list);
        edt_product_search = findViewById(R.id.edt_product_search);
        btn_search = findViewById(R.id.btn_search);

        product_list = new ArrayList<>();
        /*int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        productListAdapter = new CustomerProductListAdapter(SearchResultActivity.this, product_list, this);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
                getResources().getDimensionPixelSize(R.dimen._125sdp)));*/
        //recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        /*StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        int spanCount = 3; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        recyclerView.setLayoutManager(layoutManager);*/


        String[] items1 = null;
        items1 = ImageUrlUtils.getOffersUrls();

        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_width);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));
        productListAdapter = new CustomerProductListAdapter(SearchResultActivity.this, product_list, this);
        recyclerView.setAdapter(productListAdapter);


    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        //mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);


    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.getItem(0);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);
        searchItem.expandActionView();
        return true;
    }*/

   /* @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            searchProduct(query);
        }
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(SearchResultActivity.this, MainCustomerDashboardActivity.class);

        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            Intent mainIntent = new Intent(SearchResultActivity.this, AddBusinessActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(SearchResultActivity.this, MainCustomerDashboardActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    private void searchProduct() {

        if (!edt_product_search.getText().toString().trim().equals("")) {
            if (InternetConnection.checkConnection(SearchResultActivity.this)) {
                searchProductAPI();
            } else {
                Toast.makeText(SearchResultActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(SearchResultActivity.this, "Please enter search product details", Toast.LENGTH_SHORT).show();

        }

    }


    private void searchProductAPI() {

        JSONObject jsonObject = new JSONObject();


        Call<String> product_search = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerSearchProductPagination(edt_product_search.getText().toString().trim());
        product_search.enqueue(new RestProcess<String>(SearchResultActivity.this, SearchResultActivity.this, GETPRODUCTLISTPAGNATION, true));


    }

    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case GETPRODUCTLISTPAGNATION:

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

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    ProductListMainResponse response = new Gson().fromJson(jsonObject.toString(), ProductListMainResponse.class);
                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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


                                if (product_list.size() > 0) {
                                    ll_display.setVisibility(View.GONE);
                                    ll_list.setVisibility(View.VISIBLE);
                                } else {
                                    ll_display.setVisibility(View.VISIBLE);
                                    ll_list.setVisibility(View.GONE);
                                }
                                productListAdapter.updateProductList(product_list);


                            }
                        }


                    } else {

                        Toast.makeText(SearchResultActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    @Override
    public void itemClick(int pos, String type) {
        if (type.equals("edit")) {
            ProductDetail response = product_list.get(pos);
            Intent mainIntent = new Intent(SearchResultActivity.this, ItemDetailsActivity.class);
            mainIntent.putExtra("ProductMaster", response);
            //mainIntent.putExtra("BusinessResponse", businessListResponse);
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        searchProduct();
    }
}
