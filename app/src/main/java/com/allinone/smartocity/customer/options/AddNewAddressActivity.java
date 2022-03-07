package com.allinone.smartocity.customer.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.allinone.smartocity.R;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_ADD_ADDRESS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class AddNewAddressActivity extends AppCompatActivity implements RestCalllback {

    EditText  edtAddressNewAddress1, edtAddressNewAddress2, edtCityNewAddress, edtStateNewAddress;
    android.widget.EditText edtPinNewAddress;
    Button btnSaveNEwAddress, btnVerify;
    String  address1, address2, city, pin, state;
    Toolbar toolbar;

    Boolean check = false;
    Context context;
    public String TAG = AddNewAddressActivity.class.getSimpleName();
    private MainLoginResponse mainLoginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        context = AddNewAddressActivity.this;
        initView();


        btnSaveNEwAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                   /* if (isValidPin(pin)) {
                        checkpin(pin);
                    }*/

                    if (InternetConnection.checkConnection(AddNewAddressActivity.this)) {
                        saveaddress();
                    } else {
                        Toast.makeText(AddNewAddressActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (!edtPinNewAddress.getText().toString().trim().isEmpty() || edtPinNewAddress.getText().toString().trim() != null) {
                    verifyPin(edtPinNewAddress.getText().toString().trim());
                } else {
                    Toast.makeText(context, "Please Enter Pin Code(Six Digit)", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }

    public void saveaddress() {

        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObjectcustomer = new JSONObject();
            jsonObjectcustomer.put("customerId", Integer.parseInt(mainLoginResponse.getResult().getCustomerId()));
            JSONObject jsonObjectaddressDetail = new JSONObject();
            jsonObjectaddressDetail.put("addreLine1", edtAddressNewAddress1.getText().toString().trim());
            jsonObjectaddressDetail.put("addreLine2", edtAddressNewAddress2.getText().toString().trim());
            jsonObjectaddressDetail.put("city", edtCityNewAddress.getText().toString().trim());
            jsonObjectaddressDetail.put("state", edtStateNewAddress.getText().toString().trim());
            jsonObjectaddressDetail.put("pinCode", edtPinNewAddress.getText().toString().trim());
            jsonObject.put("addressDetail", jsonObjectaddressDetail);
            jsonObject.put("customer", jsonObjectcustomer);


        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<String> saveAddress = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getAddAddress(jsonObject.toString());
        saveAddress.enqueue(new RestProcess<String>(AddNewAddressActivity.this, AddNewAddressActivity.this, CUSTOMER_ADD_ADDRESS, true));

    }

  /*  public void checkpin(String code) {

        JSONObject checkObj = new JSONObject();
        try {
            checkObj.put("pincode", code);
            checkObj.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<String> checkPin = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getNexShoppingCheckPinDetails(checkObj.toString());
        checkPin.enqueue(new RestProcess<String>(AddNewAddressActivity.this, AddNewAddressActivity.this, Constant.SERVICE_MODE.GET_NEX_SHOPPING_CHECK_PIN_DETAILS, true));

    }*/

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //edtNameNewAddress = (EditText) findViewById(R.id.edtNameNewAddress);
        edtAddressNewAddress1 = (EditText) findViewById(R.id.edtAddressNewAddress1);
        edtAddressNewAddress2 = (EditText) findViewById(R.id.edtAddressNewAddress2);
        edtCityNewAddress = (EditText) findViewById(R.id.edtCityNewAddress);
        edtPinNewAddress = findViewById(R.id.edtPinNewAddress);
        edtStateNewAddress = (EditText) findViewById(R.id.edtStateNewAddress);
       // edtMobileNewAddress = (EditText) findViewById(R.id.edtMobileNewAddress);
        btnSaveNEwAddress = (Button) findViewById(R.id.btnSaveNEwAddress);
        //edtLocality = (EditText) findViewById(R.id.edtLocality);
        btnVerify = findViewById(R.id.btnVerify);
      //  edtMobileAlternateNo = findViewById(R.id.edtMobileAlternateNo);

        edtCityNewAddress.setOnKeyListener(null);
        edtStateNewAddress.setOnKeyListener(null);
        //edtLocality.setOnKeyListener(null);


        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
    }


    public boolean isValid() {

      //  name = edtNameNewAddress.getText().toString().trim();
        address1 = edtAddressNewAddress1.getText().toString().trim();
        address2 = edtAddressNewAddress2.getText().toString().trim();
        city = edtCityNewAddress.getText().toString().trim();
        pin = edtPinNewAddress.getText().toString().trim();
        state = edtStateNewAddress.getText().toString().trim();
       // mobile = edtMobileNewAddress.getText().toString().trim();
      //  locality = edtLocality.getText().toString().trim();


        /*if (name.length() <= 0) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (address1.length() <= 0) {
            Toast.makeText(this, "Please Enter Address 1", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address1.length() >= 35) {
            Toast.makeText(this, "Please enter less than 35 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address2.length() <= 0) {
            Toast.makeText(this, "Please Enter Address 2", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address2.length() >= 35) {
            Toast.makeText(this, "Please enter less than 35 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (pin.length() <= 5) {
            Toast.makeText(this, "Please Enter Pin Code(Six Digit)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (city.length() <= 0) {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
            return false;
        }

      /*  if (locality.length() <= 0) {
            Toast.makeText(this, "Please Enter Locality", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if (state.length() <= 0) {
            Toast.makeText(this, "Please Enter State", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*if (mobile.length() <= 9) {
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtMobileAlternateNo.getText().toString().trim().length() <= 9) {
            Toast.makeText(this, "Please Enter Alternative Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        return true;
    }

    public boolean isValidPin(String pin) {

        if (pin == null || pin.length() <= 0) {
            Toast.makeText(this, "Please Enter Pincode First", Toast.LENGTH_SHORT).show();
            //ShowLog.ShowToast(context, "Please Enter Pincode First");
            return false;
        }

        if (pin.length() != 6) {
            Toast.makeText(this, "Pincode Not Valid", Toast.LENGTH_SHORT).show();
            //ShowLog.ShowToast(context, "Pincode Not Valid");
            return false;
        }


        return true;
    }


/*    private void verifyPin(final String pinCode) {

        Map<String, String> param = new HashMap<>();
        param.put("pincode", pinCode);

        Call<String> verifyPin = ServiceGenerator.createService(RestAPIInterface.class, BuildConfig.Base_URL).getVerifyPin(param);
        verifyPin.enqueue(new RestProcess<String>(AddNewAddressActivity.this, AddNewAddressActivity.this, Constant.SERVICE_MODE.GET_NEX_SHOPPING_VERIFY_PIN_DETAILS, false));


    }*/

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

            case CUSTOMER_ADD_ADDRESS:
                //Log.e("Error", "" + Objects.requireNonNull(t.getMessage()));
                //Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                //ShowLog.ShowToast(AddNewAddressActivity.this,"Something went wrong");
                //ShowLog.ShowError("Error",""+message);
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
            case CUSTOMER_ADD_ADDRESS:
                try {
                    JSONObject response = new JSONObject(ApiResponse);
                    if (response.getString("status").equals("1")) {
                        Toast.makeText(context, "Address Added Successfully...", Toast.LENGTH_SHORT).show();
                        //ShowLog.ShowToast(context, "Address Added Successfully...");
                        Intent intent = new Intent();
                        intent.putExtra("refresh", 1);
                        setResult(RESULT_OK, intent);
                        AddNewAddressActivity.this.finish();
                    } else {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /*case Constant.SERVICE_MODE.GET_NEX_SHOPPING_CHECK_PIN_DETAILS:
                try {
                    JSONObject response = new JSONObject(ApiResponse);
                    String available = response.getString("Availability");
                    String codAvail = response.getString("CashOnDeliveryAvailble");
                    if (available.equalsIgnoreCase("Yes")) {
                        //   if (codAvail.equalsIgnoreCase("Yes") || codAvail.equalsIgnoreCase("NO")) {
                        saveaddress();
//                        } else {
//                            Toast.makeText(context,"Delivery not available to this Pincode",Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(context, "Delivery not available to this Pincode", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
          /*      break;
            case Constant.SERVICE_MODE.GET_NEX_SHOPPING_VERIFY_PIN_DETAILS:
                if (dialog != null)
                    if (dialog.isShowing())
                        dialog.dismiss();


                if (ApiResponse.equalsIgnoreCase("0")) {
                    Toast.makeText(AddNewAddressActivity.this, "Invalid pin Please Try Again!", Toast.LENGTH_SHORT).show();
                } else {

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(ApiResponse);

                        String statename = jsonObject.getString("statename");
                        String districtname = jsonObject.getString("districtname");
                        String postoffice = jsonObject.getString("postoffice");
                        edtStateNewAddress.setText(statename);
                        edtCityNewAddress.setText(districtname);
                        edtLocality.setText(postoffice);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(AddNewAddressActivity.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
                break;*/


        }
    }
}