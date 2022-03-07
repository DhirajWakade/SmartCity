package com.allinone.smartocity.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.allinone.smartocity.Business.adapter.SliderAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.adaptor.CustomerProductListAdapter;
import com.allinone.smartocity.customer.adaptor.HomeMenuListAdapter;
import com.allinone.smartocity.customer.adaptor.HomeTopCategoriesAdaptor;
import com.allinone.smartocity.customer.adaptor.SimpleStringRecyclerViewAdapter;
import com.allinone.smartocity.customer.miscellaneous.EmptyActivity;
import com.allinone.smartocity.notification.NotificationCountSetClass;
import com.allinone.smartocity.notification.NotificationListActivity;
import com.allinone.smartocity.customer.options.BusinessCartListActivity;
import com.allinone.smartocity.customer.options.CategoryListActivity;
import com.allinone.smartocity.customer.options.ProductListWithFIlterActivity;
import com.allinone.smartocity.customer.options.SearchResultActivity;
import com.allinone.smartocity.customer.order.CustomerOrderHomeListActivity;
import com.allinone.smartocity.customer.product.ItemDetailsActivity;
import com.allinone.smartocity.customer.utility.ImageUrlUtils;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.model.addBusiness.SliderItem;
import com.allinone.smartocity.model.home.MainResponseCustomerHomeModel;
import com.allinone.smartocity.model.home.MenuIcon;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.retrofit.*;
import com.allinone.smartocity.ui.activity.SelectLoginTypeActivity;
import com.allinone.smartocity.util.GridSpacingItemDecoration;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
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
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_HOME_API;
import static com.allinone.smartocity.retrofit.Constant.*;

public class MainCustomerDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, RestCalllback, OnitemClick {
    public static int notificationCountCart = 0;
    public static int notificationCount = 0;
    private DrawerLayout drawer;
    private String TAG = "MainBusinessActivity.class";
    private NavigationView navigationView;
    private TextView txt_name;
    private MainLoginResponse mainLoginResponse = null;
    private SliderView sliderView, sliderView1;
    private SliderAdapter adapter;


    private BottomBar bottomBar;
    private RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6;

    private List<MenuIcon> menuList, menuList1;
    private List<BusinessTypeModel> topBusinessList;
    private ArrayList<ProductDetail> topOfferedProductList;
    private MainResponseCustomerHomeModel mainResponseCustomerHomeModel = null;
    private HomeTopCategoriesAdaptor homeTopCategoriesAdaptor;
    private CustomerProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
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


    private void processIntent(Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.get("data") != null) {
            //here can get notification message
            String datas = bundle.get("data").toString();

            Log.d(TAG, "onResponse: " + datas);

            Intent mainIntent = new Intent(MainCustomerDashboardActivity.this, NotificationListActivity.class);

            startActivity(mainIntent);
        }
    }


    private void setOnClickListener() {

        bottomBar.setBadgeBackgroundColor(R.color.colorAccent);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_home:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                        break;
                    case R.id.tab_category:
                        startActivity(new Intent(MainCustomerDashboardActivity.this, CategoryListActivity.class));

                        break;
                   /* case R.id.tab_search:
                        startActivity(new Intent(MainCustomerDashboardActivity.this, SearchResultActivity.class));

                        break;*/
                    case R.id.tab_my_list:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.

                        startActivity(new Intent(MainCustomerDashboardActivity.this, CustomerOrderHomeListActivity.class));

                        break;

                    case R.id.tab_advertise:
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.

                        startActivity(new Intent(MainCustomerDashboardActivity.this, EmptyActivity.class));

                        break;


                /*    case R.id.tab_service:
                        startActivity(new Intent(MainCustomerDashboardActivity.this, BusinessCartListActivity.class));

                        break;
                    case R.id.tab_basket:
                        startActivity(new Intent(MainCustomerDashboardActivity.this, BusinessCartListActivity.class));

                        break;*/
                }


            }
        });


    }

    private void setData() {
        //Util.displayDailog(MainBusinessActivity.this, "Login", "You have successfully login with :" + login_type);
        if (mainLoginResponse != null) {
            if (mainLoginResponse.getResult() != null) {
                txt_name.setText(mainLoginResponse.getResult().getFirstName());

            }
        }
        //slider
        sliderItems();

        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        if (sharefHomeAPIDetail.equals("")) {
            getCustomerHome();
        } else {

            mainResponseCustomerHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseCustomerHomeModel.class);
            updateHomeAPIData();
        }


    }

    private void updateHomeAPIData() {
        topBusinessList.clear();
        topBusinessList.addAll(mainResponseCustomerHomeModel.getTopBusinessType());
        homeTopCategoriesAdaptor.update(topBusinessList);


        topOfferedProductList.clear();
        topOfferedProductList.addAll(mainResponseCustomerHomeModel.getOfferedProduct());
        productListAdapter.updateProductList(topOfferedProductList);

    }

    private void getCustomerHome() {
        if (InternetConnection.checkConnection(MainCustomerDashboardActivity.this)) {
            CustomerHomeList();
        } else {
            Toast.makeText(MainCustomerDashboardActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
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
        sliderView = findViewById(R.id.imageSlider);
        sliderView1 = findViewById(R.id.imageSlider1);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView4 = findViewById(R.id.recyclerView4);
        recyclerView5 = findViewById(R.id.recyclerView5);
        recyclerView6 = findViewById(R.id.recyclerView6);

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


        sliderView1.setSliderAdapter(adapter);
        sliderView1.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView1.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView1.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView1.setIndicatorSelectedColor(Color.WHITE);
        sliderView1.setIndicatorUnselectedColor(Color.GRAY);
        sliderView1.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView1.startAutoCycle();


        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        menuList = new ArrayList<>();
        menuList.add(new MenuIcon(R.drawable.ic_pantry, "Pantry"));
        menuList.add(new MenuIcon(R.drawable.ic_mobiles, "Mobiles"));
        menuList.add(new MenuIcon(R.drawable.ic_fashion, "Fashion"));
        menuList.add(new MenuIcon(R.drawable.ic_appliances, "Home"));
        menuList.add(new MenuIcon(R.drawable.ic_order_bag, "Application"));
        menuList.add(new MenuIcon(R.drawable.ic_electroics, "Electronics"));


        HomeMenuListAdapter homeMenuListAdapter = new HomeMenuListAdapter(MainCustomerDashboardActivity
                .this, menuList, this);
        recyclerView.setAdapter(homeMenuListAdapter);


        recyclerView6.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView6.setLayoutManager(layoutManager1);
        menuList1 = new ArrayList<>();
        menuList1.add(new MenuIcon(R.drawable.ic_pantry, "Pantry"));
        menuList1.add(new MenuIcon(R.drawable.ic_mobiles, "Mobiles"));
        menuList1.add(new MenuIcon(R.drawable.ic_fashion, "Fashion"));
        menuList1.add(new MenuIcon(R.drawable.ic_appliances, "Home"));
        menuList1.add(new MenuIcon(R.drawable.ic_order_bag, "Application"));
        menuList1.add(new MenuIcon(R.drawable.ic_electroics, "Electronics"));


        HomeMenuListAdapter homeMenuListAdapter1 = new HomeMenuListAdapter(MainCustomerDashboardActivity
                .this, menuList1, this);
        recyclerView6.setAdapter(homeMenuListAdapter1);


        topBusinessList = new ArrayList<>();
        topOfferedProductList = new ArrayList<>();


        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);
        homeTopCategoriesAdaptor = new HomeTopCategoriesAdaptor(MainCustomerDashboardActivity.this, topBusinessList, this);
        recyclerView2.setAdapter(homeTopCategoriesAdaptor);


        String[] items2 = null;

        items2 = ImageUrlUtils.getElectronicsUrls();

        StaggeredGridLayoutManager layoutManager3 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_width);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels));
        productListAdapter = new CustomerProductListAdapter(MainCustomerDashboardActivity.this, topOfferedProductList, this);
        recyclerView3.setAdapter(productListAdapter);


        String[] items3 = null;

        items3 = ImageUrlUtils.getLifeStyleUrls();

        StaggeredGridLayoutManager layoutManager4 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView4.setLayoutManager(layoutManager4);
        SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter3 = new SimpleStringRecyclerViewAdapter(recyclerView4, items3, this);
        recyclerView4.setAdapter(simpleStringRecyclerViewAdapter3);

        String[] items4 = null;
        items4 = ImageUrlUtils.getHomeApplianceUrls();

        StaggeredGridLayoutManager layoutManager5 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView5.setLayoutManager(layoutManager5);
        SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter4 = new SimpleStringRecyclerViewAdapter(recyclerView5, items4, this);
        recyclerView5.setAdapter(simpleStringRecyclerViewAdapter4);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItem item1 = menu.findItem(R.id.action_notifications);
        NotificationCountSetClass.setAddToCart(MainCustomerDashboardActivity.this, item, notificationCountCart);
        NotificationCountSetClass.setAddToCart(MainCustomerDashboardActivity.this, item1, notificationCount);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(MainCustomerDashboardActivity.this, SearchResultActivity.class));

            return true;
        } else if (id == R.id.action_cart) {

           /* NotificationCountSetClass.setAddToCart(MainActivity.this, item, notificationCount);
            invalidateOptionsMenu();*/
            startActivity(new Intent(MainCustomerDashboardActivity.this, BusinessCartListActivity.class));

           /* notificationCount=0;//clear notification count
            invalidateOptionsMenu();*/
            return true;
        } else if (id == R.id.action_exit) {
            logoutFromApp();
            return true;
        } else if (id == R.id.action_notifications) {
            startActivity(new Intent(MainCustomerDashboardActivity.this, NotificationListActivity.class));

            return true;
        } else {
            startActivity(new Intent(MainCustomerDashboardActivity.this, EmptyActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutFromApp() {
        new AlertDialog.Builder(MainCustomerDashboardActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPref.deleteAllSharedPrefs();
                        Intent intent = new Intent(MainCustomerDashboardActivity.this, SelectLoginTypeActivity.class);
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

            case R.id.action_exit:
                logoutFromApp();
                break;

            case R.id.my_orders:
                Intent intent = new Intent(MainCustomerDashboardActivity.this, CustomerOrderHomeListActivity.class);
                startActivity(intent);
                break;
            case R.id.my_cart:
                Intent maIntent = new Intent(MainCustomerDashboardActivity.this, BusinessCartListActivity.class);
                startActivity(maIntent);
                break;
            case R.id.my_account:
                //logoutFromApp();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.ll_1:
                Intent mainIntent = new Intent(MainCustomerDashboardActivity.this, BusinessListActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.ll_2:
                Intent mainItent = new Intent(MainCustomerDashboardActivity.this, OrderListActivity.class);
                startActivity(mainItent);
                break;
            case R.id.ll_3:
                Intent mainntent = new Intent(MainCustomerDashboardActivity.this, BusinessListActivity.class);
                startActivity(mainntent);
                break;
            case R.id.ll_4:
                Intent mainInent = new Intent(MainCustomerDashboardActivity.this, ProductListActivity.class);
                startActivity(mainInent);
                break;
            case R.id.ll_5:
                Toast.makeText(MainCustomerDashboardActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();

                break;*/
        }
    }


    private void CustomerHomeList() {
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
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerHomeApiDetails();
        walletDeductDetails.enqueue(new RestProcess<String>(MainCustomerDashboardActivity.this, MainCustomerDashboardActivity.this, CUSTOMER_HOME_API, true));
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CUSTOMER_HOME_API:
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
            case CUSTOMER_HOME_API:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);

                    //now you have Pojo do whatever

                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject resultjson = jsonObject.getJSONObject("result");
                        mainResponseCustomerHomeModel = new Gson().fromJson(resultjson.toString(), MainResponseCustomerHomeModel.class);
                        SharedPref.write(SHARED_HOME_API_DETAILS, new Gson().toJson(mainResponseCustomerHomeModel));
                        updateHomeAPIData();
                    } else {
                        Toast.makeText(MainCustomerDashboardActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void itemClick(int pos, String type) {
        if (type.equals("")) {
            BusinessTypeModel businessTypeModel = topBusinessList.get(pos);
            Intent mainIntent = new Intent(MainCustomerDashboardActivity.this, ProductListWithFIlterActivity.class);
            mainIntent.putExtra("BusinessTypeModel", businessTypeModel);
            startActivity(mainIntent);
            finish();
        } else if (type.equals("TopProduct")) {
            ProductDetail response = topOfferedProductList.get(pos);
            Intent mainIntent = new Intent(MainCustomerDashboardActivity.this, ItemDetailsActivity.class);
            mainIntent.putExtra("ProductMaster", response);
            startActivity(mainIntent);
            finish();
        }

    }
}
