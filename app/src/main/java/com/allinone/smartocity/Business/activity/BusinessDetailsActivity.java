package com.allinone.smartocity.Business.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.allinone.smartocity.R;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.InternetConnection;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.UPDATEBUSINESSDETAILS;

public class BusinessDetailsActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback {

    private String TAG = "AddBusinessActivity";
    private EditText edt_business_name, edt_pancard_no, edt_pancard_name;
    private EditText edt_ifsc_code, edt_account_no, edt_confirm_account_no;
    private EditText edt_address_one, edt_address_two, edt_country;
    private EditText edt_state, edt_city, edt_pincode;
    private Button btn_submit_business;
    private String business_name = "", pancard_no = "", pancard_name = "", ifsc_code = "", account_no = "";
    private String confirm_account_no = "", address_one = "", address_two = "", country = "";
    private String state = "", city = "", pincode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_busniess);


        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {

        btn_submit_business.setOnClickListener(this);

    }

    private void setData() {

    }

    private void initializeWidgets() {
        edt_business_name = findViewById(R.id.edt_business_name);
        edt_pancard_no = findViewById(R.id.edt_pancard_no);
        edt_pancard_name = findViewById(R.id.edt_pancard_name);
        edt_ifsc_code = findViewById(R.id.edt_ifsc_code);
        edt_account_no = findViewById(R.id.edt_account_no);
        edt_confirm_account_no = findViewById(R.id.edt_confirm_account_no);
        edt_address_one = findViewById(R.id.edt_address_one);
        edt_address_two = findViewById(R.id.edt_address_two);
        edt_country = findViewById(R.id.edt_country);
        edt_state = findViewById(R.id.edt_state);
        edt_city = findViewById(R.id.edt_city);
        edt_pincode = findViewById(R.id.edt_pincode);
        btn_submit_business = findViewById(R.id.btn_submit_business);


    }

    private void initializeData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_business:
                if (Valid()) {

                    if (InternetConnection.checkConnection(BusinessDetailsActivity.this)) {
                        addBusiness();
                    } else {
                        Toast.makeText(BusinessDetailsActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }
                break;


        }
    }


    private void addBusiness() {
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


        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessCategoriesDetails();
        walletDeductDetails.enqueue(new RestProcess<String>(BusinessDetailsActivity.this, BusinessDetailsActivity.this, UPDATEBUSINESSDETAILS, true));


    }


    private boolean Valid() {
        business_name = edt_business_name.getText().toString().trim();
        pancard_no = edt_pancard_no.getText().toString().trim();
        pancard_name = edt_pancard_name.getText().toString().trim();
        ifsc_code = edt_ifsc_code.getText().toString().trim();
        account_no = edt_account_no.getText().toString().trim();
        confirm_account_no = edt_confirm_account_no.getText().toString().trim();
        address_one = edt_address_one.getText().toString().trim();
        address_two = edt_address_two.getText().toString().trim();
        country = edt_country.getText().toString().trim();
        state = edt_state.getText().toString().trim();
        city = edt_city.getText().toString().trim();
        pincode = edt_pincode.getText().toString().trim();

        if (business_name.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter business name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pancard_no.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter pan card no", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pancard_name.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter pancard holder name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ifsc_code.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter Ifsc code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account_no.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter account no", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (confirm_account_no.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter confirm account no", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address_one.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address_two.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter address second", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (country.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (state.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter state", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pincode.equals("")) {
            Toast.makeText(BusinessDetailsActivity.this, "Please enter pincode", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bussiness_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case UPDATEBUSINESSDETAILS:
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
            case UPDATEBUSINESSDETAILS:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(BusinessDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(BusinessDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
}

