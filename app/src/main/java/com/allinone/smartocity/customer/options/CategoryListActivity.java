package com.allinone.smartocity.customer.options;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ExpandableListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.adaptor.ExpandableListAdapter;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.model.category.CategoryMainResponseModel;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CATEGORIES;
import static com.allinone.smartocity.retrofit.Constant.SHARED_CATEGORY_API_DETAILS;

public class CategoryListActivity extends AppCompatActivity implements RestCalllback {

    private String TAG = "CategoryListActivity";
    ExpandableListView expandableListView;

    ArrayList<String> title;

    HashMap<String, ArrayList<BusinessTypeModel>> hashMap;
    private CategoryMainResponseModel categoryMainResponseModel;
    private ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        getCategoryData();


    }

    private void getCategoryData() {
        String sharefHomeAPIDetail = SharedPref.read(SHARED_CATEGORY_API_DETAILS, "");
        if (sharefHomeAPIDetail.equals("")) {
            if (InternetConnection.checkConnection(CategoryListActivity.this)) {
                getCatgeoryList();
            } else {
                Toast.makeText(CategoryListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
            }
        } else {
            updateList();
        }
    }


    private void getCatgeoryList() {
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

        //  Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> category = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCategoryApiDetails();
        category.enqueue(new RestProcess<String>(CategoryListActivity.this, CategoryListActivity.this, CATEGORIES, true));
    }


    private void initializeWidgets() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        hashMap = new HashMap<>();
        title = new ArrayList<>();

        expandableListAdapter = new ExpandableListAdapter(this, title, hashMap);
        expandableListView.setAdapter(expandableListAdapter);

        //child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), hashMap.get(title.get(groupPosition)).get(childPosition).getBusinessTypeName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(CategoryListActivity.this, MainCustomerDashboardActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(CategoryListActivity.this, MainCustomerDashboardActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CATEGORIES:
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
            case CATEGORIES:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);

                    //now you have Pojo do whatever

                    if (jsonObject.getString("status").equals("1")) {

                        categoryMainResponseModel = new Gson().fromJson(jsonObject.toString(), CategoryMainResponseModel.class);
                        SharedPref.write(SHARED_CATEGORY_API_DETAILS, new Gson().toJson(categoryMainResponseModel));
                        updateList();


                    } else {
                        Toast.makeText(CategoryListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void updateList() {
        String sharefHomeAPIDetail = SharedPref.read(SHARED_CATEGORY_API_DETAILS, "");

        categoryMainResponseModel = new Gson().fromJson(sharefHomeAPIDetail, CategoryMainResponseModel.class);

        title.clear();
        hashMap.clear();

        for (int i = 0; i < categoryMainResponseModel.getResult().size(); i++) {
            title.add(categoryMainResponseModel.getResult().get(i).getBcName());
            hashMap.put(categoryMainResponseModel.getResult().get(i).getBcName(), categoryMainResponseModel.getResult().get(i).getBusinessType());

        }

        expandableListAdapter.updatelist(title, hashMap);
    }
}
