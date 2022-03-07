package com.allinone.smartocity.customer.options;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.model.address.AddressModel;
import com.allinone.smartocity.model.cart.CartListMainResponseModel;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_PLACE_ORDER;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback {


    private String TAG = "PlaceOrderActivity";
    private Button btn_place_order;
    private CartListMainResponseModel response;
    private AddressModel address;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        btn_place_order.setOnClickListener(this);
    }

    private void setData() {

    }

    private void initializeWidgets() {
        btn_place_order = findViewById(R.id.btn_place_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void initializeData() {
        response = (CartListMainResponseModel) getIntent().getSerializableExtra("BusinessList");
        address = (AddressModel) getIntent().getSerializableExtra("address");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_place_order) {
            if (Valid()) {
                if (InternetConnection.checkConnection(PlaceOrderActivity.this)) {
                    placeOrderAPI();
                } else {
                    Toast.makeText(PlaceOrderActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void placeOrderAPI() {

        try {

            Gson gson = new Gson();
            String dd = gson.toJson(response.getResult());
            JSONObject jsonObj = new JSONObject(dd);

            JSONObject jsonaddress = new JSONObject();
            jsonaddress.put("addrId", address.getAddrId());
            jsonObj.put("orderAddress", jsonaddress);

            Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getPlaceOrderDeails(jsonObj.toString());
            walletDeductDetails.enqueue(new RestProcess<String>(PlaceOrderActivity.this, PlaceOrderActivity.this, CUSTOMER_PLACE_ORDER, true));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean Valid() {
        /*mobile_no = edt_mobile_no.getText().toString().trim();
        if (mobile_no.equals("")) {
            Toast.makeText(PlaceOrderActivity.this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.PHONE.matcher(mobile_no).matches()) {
            Toast.makeText(PlaceOrderActivity.this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
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
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CUSTOMER_PLACE_ORDER:
                orderStatusAlert(2, "", "", "");
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
            case CUSTOMER_PLACE_ORDER:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {

                        JSONObject jsonObject1=jsonObject.getJSONObject("result");
                        String order_id = jsonObject1.getString("mainOrderId");
                        String main_order_id = jsonObject1.getString("mainOrderCode");
                        String createDate = jsonObject1.getString("createDate");
                        orderStatusAlert(1, order_id, main_order_id, createDate);

                    } else {
                        Toast.makeText(PlaceOrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        orderStatusAlert(2, "", "", "");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void orderStatusAlert(int i, String order_id, String main_order_id, String createDate) {
        LayoutInflater li = LayoutInflater.from(PlaceOrderActivity.this);
        View customView = li.inflate(R.layout.order_status_alert, null);

        TextView txt_payment_description = customView.findViewById(R.id.txt_payment_description);
        TextView txt_payment_success_failed = customView.findViewById(R.id.txt_payment_success_failed);
        ImageView img_payment_success_failed = customView.findViewById(R.id.img_payment_success_failed);
        Button btn_place_order_done = customView.findViewById(R.id.btn_place_order_done);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceOrderActivity.this);
        alertDialogBuilder.setView(customView);
        alertDialogBuilder.setCancelable(false);
        //txt_bill_to.setText(bbpsPaymentResponseModel.getBillerName());
        //txt_refference_id.setText("Reference ID : BBPS" + bbpsPaymentResponseModel.getTransactionRefId());
        // billAmount = bbpsPaymentResponseModel.getAmount();
        // billAmount = new StringBuilder(billAmount).insert(billAmount.length() - 2, ".").toString();
        if (i == 1) {
            txt_payment_description.setText("Order ID : "+order_id+"  "+"Date : "+createDate);
            txt_payment_success_failed.setText("Order Placed Successful");
            img_payment_success_failed.setImageDrawable(getResources().getDrawable(R.drawable.success));
        } else {
             txt_payment_description.setText("");
            txt_payment_success_failed.setText("Order Placed Failed");
            img_payment_success_failed.setImageDrawable(getResources().getDrawable(R.drawable.fail));
        }

        btn_place_order_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent mainIntent = new Intent(PlaceOrderActivity.this, MainCustomerDashboardActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}