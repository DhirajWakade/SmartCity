package com.allinone.smartocity.customer.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.adaptor.AddressListAdapter;
import com.allinone.smartocity.model.address.AddressListMainResponseModel;
import com.allinone.smartocity.model.address.AddressModel;
import com.allinone.smartocity.model.cart.CartListMainResponseModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
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
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_ADDRESS_LIST;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class AddressListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick {
    public String TAG = AddressListActivity.class.getSimpleName();

    private AddressListAdapter adapter;
    private Context context;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;

    private MainLoginResponse mainLoginResponse = null;
    private ArrayList<AddressModel> addressList;
    private CartListMainResponseModel response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        context = AddressListActivity.this;
        initView();
        initializeData();


    }

    private void initializeData() {
        response = (CartListMainResponseModel) getIntent().getSerializableExtra("BusinessList");
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
        addressList = new ArrayList<>();

        adapter = new AddressListAdapter(context, addressList, this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnection.checkConnection(AddressListActivity.this)) {
            getShippingAddresses();
        } else {
            Toast.makeText(AddressListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(AddressListActivity.this, MainCustomerDashboardActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        } else if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(AddressListActivity.this, AddNewAddressActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getShippingAddresses() {

        addressList.clear();


        Call<String> addresslist = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getAddressList(mainLoginResponse.getResult().getCustomerId());
        addresslist.enqueue(new RestProcess<String>(AddressListActivity.this, AddressListActivity.this, CUSTOMER_ADDRESS_LIST, true));

    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {


            case CUSTOMER_ADDRESS_LIST:

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
            case CUSTOMER_ADDRESS_LIST:
                try {

                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    AddressListMainResponseModel response = new Gson().fromJson(jsonObject.toString(), AddressListMainResponseModel.class);
                    if (response.getStatus().equals("1")) {
                        Toast.makeText(Objects.requireNonNull(context), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {
                                //business_list.clear();
                                addressList.addAll(response.getResult());
                                adapter.updateBusinessList(addressList);
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
        if (InternetConnection.checkConnection(AddressListActivity.this)) {
            getShippingAddresses();
        } else {
            Toast.makeText(AddressListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void itemClick(int pos, String type) {
        AddressModel model = addressList.get(pos);
        Intent mainIntent = new Intent(AddressListActivity.this, PlaceOrderActivity.class);
        mainIntent.putExtra("BusinessList", response);
        mainIntent.putExtra("address", model);
        startActivity(mainIntent);
    }
}

