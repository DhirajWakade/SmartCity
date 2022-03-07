package com.allinone.smartocity.ui.activity.mobileno;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.allinone.smartocity.R;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.ui.activity.login.LoginActivity;
import com.allinone.smartocity.ui.activity.signup.SignUpActivity;
import com.allinone.smartocity.util.InternetConnection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;


import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CHECK_MOBILE_NO_ALREADY_EXIST_OR_NOT;
import static com.allinone.smartocity.retrofit.Constant.LOGINTYPE;
import static com.allinone.smartocity.retrofit.Constant.MOBILENO;

public class MobileActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback {


    private String TAG = "MobileActivity";
    private String login_type = "";
    private EditText edt_mobile_no;
    private FloatingActionButton fab_mobile_no;
    private String mobile_no = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        fab_mobile_no.setOnClickListener(this);
    }

    private void setData() {

    }

    private void initializeWidgets() {
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        fab_mobile_no = findViewById(R.id.fab_mobile_no);
    }

    private void initializeData() {
        login_type = getIntent().getStringExtra(LOGINTYPE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_mobile_no) {
            if (Valid()) {
                if (InternetConnection.checkConnection(MobileActivity.this)) {
                    checkMobileNoExistOrNot();
                } else {
                    Toast.makeText(MobileActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void checkMobileNoExistOrNot() {
       /* JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(MOBILENO, mobile_no);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getMobileExistOrNotDetails(mobile_no, login_type);
        walletDeductDetails.enqueue(new RestProcess<String>(MobileActivity.this, MobileActivity.this, CHECK_MOBILE_NO_ALREADY_EXIST_OR_NOT, true));

    }

    private boolean Valid() {
        mobile_no = edt_mobile_no.getText().toString().trim();
        if (mobile_no.equals("")) {
            Toast.makeText(MobileActivity.this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.PHONE.matcher(mobile_no).matches()) {
            Toast.makeText(MobileActivity.this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CHECK_MOBILE_NO_ALREADY_EXIST_OR_NOT:
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
            case CHECK_MOBILE_NO_ALREADY_EXIST_OR_NOT:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Intent intent = new Intent(MobileActivity.this, SignUpActivity.class);
                        intent.putExtra(LOGINTYPE, login_type);
                        intent.putExtra(MOBILENO, mobile_no);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MobileActivity.this, LoginActivity.class);
                        intent.putExtra(LOGINTYPE, login_type);
                        intent.putExtra(MOBILENO, mobile_no);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MobileActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}