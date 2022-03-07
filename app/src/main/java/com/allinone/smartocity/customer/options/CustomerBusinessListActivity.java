package com.allinone.smartocity.customer.options;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.adaptor.CustomerBusinessListAdapter;
import com.allinone.smartocity.model.addBusiness.BusinessListMainResponse;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.BUSINESS_LIST;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;


public class CustomerBusinessListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick {


    private String TAG = "CustomerBusinessListActivity";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private CustomerBusinessListAdapter businessListAdapter;
    private MainLoginResponse mainLoginResponse = null;
    private ArrayList<BusinessListResponse> business_list;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_business_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        initializeData();

        setData();

    }


    private void setData() {

    }

    private void initializeWidgets() {
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);


    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        business_list = new ArrayList<>();

        businessListAdapter = new CustomerBusinessListAdapter(CustomerBusinessListActivity.this, business_list, this);
        recyclerView.setAdapter(businessListAdapter);


    }

    private void doApiCall() {
        if (InternetConnection.checkConnection(CustomerBusinessListActivity.this)) {
            getBusiness();
        } else {
            Toast.makeText(CustomerBusinessListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }


    private void getBusiness() {
        JSONObject jsonObject = new JSONObject();
        try {
        /*    jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        if (mainLoginResponse != null) {
            if (mainLoginResponse.getResult() != null) {
                headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());

                Call<String> business_list = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessDetails(headers, mainLoginResponse.getResult().getBmId());
                business_list.enqueue(new RestProcess<String>(CustomerBusinessListActivity.this, CustomerBusinessListActivity.this, BUSINESS_LIST, true));

            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case BUSINESS_LIST:
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
            case BUSINESS_LIST:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    BusinessListMainResponse response = new Gson().fromJson(jsonObject.toString(), BusinessListMainResponse.class);
                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(BusinessListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {
                                business_list.clear();
                                business_list.addAll(response.getResult());
                                businessListAdapter.updateBusinessList(business_list);
                            }
                        }


                    } else {
                        Toast.makeText(CustomerBusinessListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


    @Override
    public void onRefresh() {
        doApiCall();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        doApiCall();
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
        BusinessListResponse businessTypeModel = business_list.get(pos);

        Intent mainIntent = new Intent(CustomerBusinessListActivity.this, ProductListWithFIlterActivity.class);
        mainIntent.putExtra("BusinessTypeModel", businessTypeModel);
        startActivity(mainIntent);
        finish();

    }
}
