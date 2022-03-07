package com.allinone.smartocity.customer.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.adaptor.MyOrderHomeListAdapter;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.order.MainMyOrderHomeResponse;
import com.allinone.smartocity.model.order.SubMyOrderHomeResponseModel;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_MYORDER_HOME;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class CustomerOrderHomeListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick {
    public String TAG = CustomerOrderHomeListActivity.class.getSimpleName();

    private MyOrderHomeListAdapter adapter;
    private Context context;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;

    private MainLoginResponse mainLoginResponse = null;
    private ArrayList<SubMyOrderHomeResponseModel> orderList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_customer_home_list);
        context = CustomerOrderHomeListActivity.this;
        initView();
        initializeData();
    }

    private void initializeData() {

    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(Objects.requireNonNull(context));
        recyclerView.setLayoutManager(layoutManager);
        orderList = new ArrayList<>();

        adapter = new MyOrderHomeListAdapter(context, orderList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnection.checkConnection(CustomerOrderHomeListActivity.this)) {
            getShippingAddresses();
        } else {
            Toast.makeText(CustomerOrderHomeListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(CustomerOrderHomeListActivity.this, MainCustomerDashboardActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        } else if (item.getItemId() == R.id.action_add) {
            //  startActivity(new Intent(MyOrderHomeListActivity.this, AddNewAddressActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getShippingAddresses() {

        orderList.clear();


        Call<String> addresslist = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerMyOrderHomeDetailsList(mainLoginResponse.getResult().getCustomerId());
        addresslist.enqueue(new RestProcess<String>(CustomerOrderHomeListActivity.this, CustomerOrderHomeListActivity.this, CUSTOMER_MYORDER_HOME, true));

    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {


            case CUSTOMER_MYORDER_HOME:

                //  Log.e("Error",""+ Objects.requireNonNull(t.getMessage()));
                //   Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                break;

        }
    }

    @Override
    public void onResponse(Call call, Response backendResponse, int serviceMode) {
        String ApiResponse = "";
        if (backendResponse.body() != null) {
            ApiResponse = backendResponse.body().toString();
        }

        switch (serviceMode) {
            case CUSTOMER_MYORDER_HOME:
                try {

                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainMyOrderHomeResponse response = new Gson().fromJson(jsonObject.toString(), MainMyOrderHomeResponse.class);
                    if (response.getStatus().equals("1")) {
                        Toast.makeText(Objects.requireNonNull(context), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {
                                //business_list.clear();
                                orderList.addAll(response.getResult());
                                adapter.updateBusinessList(orderList);
                            }
                        }


                    } else {
                        Toast.makeText(Objects.requireNonNull(context), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }


    @Override
    public void onRefresh() {
        if (InternetConnection.checkConnection(CustomerOrderHomeListActivity.this)) {
            getShippingAddresses();
        } else {
            Toast.makeText(CustomerOrderHomeListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void itemClick(int pos, String type) {
        SubMyOrderHomeResponseModel model = orderList.get(pos);
        Intent mainIntent = new Intent(CustomerOrderHomeListActivity.this, CustomerOrderBusinessListActivity.class);
        mainIntent.putExtra("BusinessDetails", model);
        startActivity(mainIntent);
    }
}