package com.allinone.smartocity.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.allinone.smartocity.Business.MainBusinessActivity;
import com.allinone.smartocity.R;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.allinone.smartocity.retrofit.Constant.*;


public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    Animation animFadeIn;
    LinearLayout linearLayout;
    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPref.init(getApplicationContext());
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }
        // load the animation
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_fade_in);
        // set animation listener
        animFadeIn.setAnimationListener(this);
        // animation for image
        linearLayout = (LinearLayout) findViewById(R.id.layout_linear);
        // start the animation
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.startAnimation(animFadeIn);

        initializeWidgets();

    }

    private void initializeWidgets() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //   String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        //under Implementation
    }

    public void onAnimationEnd(Animation animation) {
        // Start Main Screen

        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");

        if (sharefloginDetail.equals("")) {
            Intent mainIntent = new Intent(SplashActivity.this, SelectLoginTypeActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        } else {
            String login_type = SharedPref.read(LOGIN_TYPE, "");
            if (login_type.equals(BUSINESS)) {
                Intent mainIntent = new Intent(SplashActivity.this, MainBusinessActivity.class);
                startActivity(mainIntent);
                finish();
            } else {
                Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //under Implementation
    }

}









/*{


    private Animation animBlink;
    private MainLoginResponse mainLoginResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPref.init(getApplicationContext());
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        findViewById(R.id.textview).startAnimation(animBlink);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
                if(sharefloginDetail.equals("")){
                    Intent mainIntent = new Intent(SplashActivity.this, SelectLoginTypeActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainBusinessActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }



            }
        }, 2000);

    }*/

//}
