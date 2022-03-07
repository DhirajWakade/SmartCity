package com.allinone.smartocity.Business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.allinone.smartocity.Business.activity.BusinessListActivity;
import com.allinone.smartocity.Business.activity.OrderListActivity;
import com.allinone.smartocity.Business.activity.ProductListActivity;
import com.allinone.smartocity.Business.adapter.SliderAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.model.addBusiness.SliderItem;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.notification.NotificationCountSetClass;
import com.allinone.smartocity.notification.NotificationListActivity;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.ui.activity.SelectLoginTypeActivity;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.*;
import static com.allinone.smartocity.retrofit.Constant.*;

public class MainBusinessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, RestCalllback {

    public static int notificationCount = 0;
    private DrawerLayout drawer;
    private String TAG = "MainBusinessActivity.class";
    private NavigationView navigationView;
    private TextView txt_name, txt_business;
    private MainLoginResponse mainLoginResponse = null;
    private SliderView sliderView;
    private SliderAdapter adapter;
    private LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5;
    private MainResponseBusinessHomeModel homeApiResponse = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_business);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();
        processIntent(getIntent());

    }


    private void setOnClickListener() {
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);

    }

    private void processIntent(Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.get("data") != null) {
            //here can get notification message
            String datas = bundle.get("data").toString();

            Log.d(TAG, "onResponse: " + datas);

            Intent mainIntent = new Intent(MainBusinessActivity.this, NotificationListActivity.class);

            startActivity(mainIntent);
        }
    }


    private void setData() {
        //Util.displayDailog(MainBusinessActivity.this, "Login", "You have successfully login with :" + login_type);
        if (mainLoginResponse != null) {
            if (mainLoginResponse.getResult() != null) {
                txt_name.setText(mainLoginResponse.getResult().getFirstName());
                txt_business.setText(mainLoginResponse.getResult().getBusinessUsername());
            }
        }
        //slider
        sliderItems();

        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        if (sharefHomeAPIDetail.equals("")) {
            getBusinessHome();
        }


    }

    private void getBusinessHome() {
        if (InternetConnection.checkConnection(MainBusinessActivity.this)) {
            BusinessHomeList();
        } else {
            Toast.makeText(MainBusinessActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }

    public void sliderItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    private void initializeWidgets() {
        View header = navigationView.getHeaderView(0);
        txt_name = header.findViewById(R.id.txt_name);
        txt_business = header.findViewById(R.id.txt_business);
        sliderView = findViewById(R.id.imageSlider);

        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_1);
        ll_5 = findViewById(R.id.ll_4);

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        super.onStop();
    }


    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            logoutFromApp();
            return true;
        } else if (id == R.id.action_notifications) {
            startActivity(new Intent(MainBusinessActivity.this, NotificationListActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_business, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)

        MenuItem item1 = menu.findItem(R.id.action_notifications);

        NotificationCountSetClass.setAddToCart(MainBusinessActivity.this, item1, notificationCount);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }


    private void logoutFromApp() {
        new AlertDialog.Builder(MainBusinessActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPref.deleteAllSharedPrefs();
                        Intent intent = new Intent(MainBusinessActivity.this, SelectLoginTypeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_business:
                Intent mainIntent = new Intent(MainBusinessActivity.this, BusinessListActivity.class);
                startActivity(mainIntent);
                break;

            case R.id.nav_product:
                mainIntent = new Intent(MainBusinessActivity.this, ProductListActivity.class);

                // mainIntent.putExtra("BusinessResponse", response);
                mainIntent.putExtra("type", "2");
                startActivity(mainIntent);
                break;

            case R.id.nav_logout:
                logoutFromApp();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                Intent mainIntent = new Intent(MainBusinessActivity.this, BusinessListActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.ll_2:
                Intent mainItent = new Intent(MainBusinessActivity.this, OrderListActivity.class);
                startActivity(mainItent);
                break;
            case R.id.ll_3:
                Intent mainntent = new Intent(MainBusinessActivity.this, BusinessListActivity.class);
                startActivity(mainntent);
                break;
            case R.id.ll_4:
                Intent mainInent = new Intent(MainBusinessActivity.this, ProductListActivity.class);
                mainInent.putExtra("type", "2");
                startActivity(mainInent);
                break;
            case R.id.ll_5:
                Toast.makeText(MainBusinessActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    private void BusinessHomeList() {
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

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessHomeApiDetails(headers, mainLoginResponse.getResult().getBmId());
        walletDeductDetails.enqueue(new RestProcess<String>(MainBusinessActivity.this, MainBusinessActivity.this, HOME_API, true));
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case HOME_API:
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
            case HOME_API:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);

                    //now you have Pojo do whatever

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject resultjson = jsonObject.getJSONObject("result");
                        homeApiResponse = new Gson().fromJson(resultjson.toString(), MainResponseBusinessHomeModel.class);
                        SharedPref.write(SHARED_HOME_API_DETAILS, new Gson().toJson(homeApiResponse));

                    } else {
                        Toast.makeText(MainBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
