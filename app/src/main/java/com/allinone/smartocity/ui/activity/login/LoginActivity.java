package com.allinone.smartocity.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.allinone.smartocity.Business.MainBusinessActivity;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.retrofit.*;
import com.allinone.smartocity.ui.activity.WelcomeActivity;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.LOGIN_DATA;
import static com.allinone.smartocity.retrofit.Constant.*;

public class LoginActivity extends AppCompatActivity implements RestCalllback, View.OnClickListener {


    private String TAG = "LoginActivity";
    private EditText edt_mobile_no;
    private EditText edt_password;
    private Button btn_sign_in_verify;
    private String login_type = "";
    private String mobile_no = "";
    private String password = "";
    private String fcmtoken = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {

        btn_sign_in_verify.setOnClickListener(this);

    }

    private void setData() {
        edt_mobile_no.setText(mobile_no);
    }

    private void initializeWidgets() {
        SharedPref.init(getApplicationContext());
        edt_password = findViewById(R.id.edt_password);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        btn_sign_in_verify = findViewById(R.id.btn_sign_in_verify);
        fcmtoken = FirebaseMessaging.getInstance().getToken().getResult();
    }

    private void initializeData() {
        login_type = getIntent().getStringExtra(LOGINTYPE);
        mobile_no = getIntent().getStringExtra(MOBILENO);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in_verify:
                if (Valid()) {
                    if (InternetConnection.checkConnection(LoginActivity.this)) {
                        signIn();

                    } else {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }
                break;


        }
    }

    private void signIn() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("mobNo", mobile_no);
            jsonObject.put("userType", login_type);
            jsonObject.put("fcmid", fcmtoken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getLoginDetails(jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(LoginActivity.this, LoginActivity.this, LOGIN_DATA, true));

    }

    private boolean Valid() {
        password = edt_password.getText().toString().trim();
        if (password.equals("")) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case LOGIN_DATA:
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
            case LOGIN_DATA:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainLoginResponse response = new Gson().fromJson(jsonObject.toString(), MainLoginResponse.class);
                    //now you have Pojo do whatever

                    if (response.getStatus().equals("1")) {
                        SharedPref.write(SHARED_LOGIN_DETAILS, new Gson().toJson(response));
                        SharedPref.write(LOGIN_TYPE, login_type);
                        if (login_type.equals(BUSINESS)) {
                            Intent mainIntent = new Intent(LoginActivity.this, MainBusinessActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

