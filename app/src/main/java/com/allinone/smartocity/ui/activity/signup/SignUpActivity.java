package com.allinone.smartocity.ui.activity.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.allinone.smartocity.R;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.ui.activity.login.LoginActivity;
import com.allinone.smartocity.util.InternetConnection;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;

import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.*;
import static com.allinone.smartocity.retrofit.Constant.*;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback {
    private Button btn_sign_up_verify, btn_send_OTP, btn_resend_OTP;
    private String TAG = "SignUpActivity";
    private String login_type = "";
    private String mobile_no = "";
    private EditText edt_first_name, edt_last_name, edt_email, edt_password, edt_confirm_password, edt_otp;
    private String first_name = "", last_name = "", email = "", password = "", confirm_password = "", otp = "";
    private String resultotp = "";
    private TextView txt_otp_send_on;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {

        btn_sign_up_verify.setOnClickListener(this);
        btn_send_OTP.setOnClickListener(this);
        btn_resend_OTP.setOnClickListener(this);
    }

    private void setData() {

    }

    private void initializeWidgets() {
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        edt_otp = findViewById(R.id.edt_otp);
        btn_send_OTP = findViewById(R.id.btn_send_OTP);
        btn_resend_OTP = findViewById(R.id.btn_resend_OTP);
        btn_sign_up_verify = findViewById(R.id.btn_sign_up_verify);
        txt_otp_send_on = findViewById(R.id.txt_otp_send_on);

    }

    private void initializeData() {
        login_type = getIntent().getStringExtra(LOGINTYPE);
        mobile_no = getIntent().getStringExtra(MOBILENO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up_verify:
                if (Valid()) {

                    if(InternetConnection.checkConnection(SignUpActivity.this)) {
                        signUp();
                    }else{
                        Toast.makeText(SignUpActivity.this,getResources().getString(R.string.internetconnectio),Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_send_OTP:
            case R.id.btn_resend_OTP:
                if(InternetConnection.checkConnection(SignUpActivity.this)) {

                    sendOTP();
                }else{
                    Toast.makeText(SignUpActivity.this,getResources().getString(R.string.internetconnectio),Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    private void sendOTP() {
        if (validEmail()) ;
        {
            Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getSentOTPOnEmail(email);
            walletDeductDetails.enqueue(new RestProcess<String>(SignUpActivity.this, SignUpActivity.this, SENDOTPONEMAIL, true));
        }
    }

    private void signUp() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (login_type.equals(CUSTOMER)) {
            Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerRegistrationDetails(jsonObject.toString());
            walletDeductDetails.enqueue(new RestProcess<String>(SignUpActivity.this, SignUpActivity.this, REGISTRATION_DATA, true));

        } else {
            Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessRegistrationDetails(jsonObject.toString());
            walletDeductDetails.enqueue(new RestProcess<String>(SignUpActivity.this, SignUpActivity.this, REGISTRATION_DATA, true));

        }


    }


    private boolean validEmail() {

        email = edt_email.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignUpActivity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private boolean Valid() {
        first_name = edt_first_name.getText().toString().trim();
        last_name = edt_last_name.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        password = edt_password.getText().toString().trim();
        confirm_password = edt_confirm_password.getText().toString().trim();
        otp = edt_otp.getText().toString().trim();
        if (first_name.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (last_name.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignUpActivity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirm_password.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirm_password)) {
            Toast.makeText(SignUpActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (otp.equals("")) {
            Toast.makeText(SignUpActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!otp.equals(resultotp)) {
            Toast.makeText(SignUpActivity.this, "OTP mismatch", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case REGISTRATION_DATA:
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
            case REGISTRATION_DATA:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                        mainIntent.putExtra(LOGINTYPE, login_type);
                        mainIntent.putExtra(MOBILENO, mobile_no);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case SENDOTPONEMAIL:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        resultotp = jsonObject.getString("result");
                        btn_send_OTP.setVisibility(View.GONE);
                        btn_resend_OTP.setVisibility(View.VISIBLE);
                        txt_otp_send_on.setText(getResources().getString(R.string.we_have_sent_6_digit_otp) + " "+email);
                    } else {
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
