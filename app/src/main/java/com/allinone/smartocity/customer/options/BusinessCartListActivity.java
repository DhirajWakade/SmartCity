package com.allinone.smartocity.customer.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.model.cart.BusinessDetailsModel;
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
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_CART_LIST;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.CUSTOMER_REMOVE_CART_LIST;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class BusinessCartListActivity extends AppCompatActivity implements RestCalllback, OnitemClick, View.OnClickListener {
    private static Context mContext;
    private String TAG = "BusinessCartListActivity.class";
    private MainLoginResponse mainLoginResponse;
    private ArrayList<BusinessDetailsModel> business_list;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    private TextView text_action_bottom1, text_action_bottom2;
    private LinearLayout layoutCartItems, layoutCartPayments, layoutCartNoItems;
    private CartListMainResponseModel response=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        mContext = BusinessCartListActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        text_action_bottom1 = findViewById(R.id.text_action_bottom1);
        text_action_bottom2 = findViewById(R.id.text_action_bottom2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Show cart layout based on items

        Button bStartShopping = (Button) findViewById(R.id.bAddNew);
        bStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(BusinessCartListActivity.this, MainCustomerDashboardActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });


        layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        business_list = new ArrayList<>();

        setCartLayout();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(recyclerView, business_list, this);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);


        text_action_bottom2.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (InternetConnection.checkConnection(BusinessCartListActivity.this)) {
            getCartList();
        } else {
            Toast.makeText(BusinessCartListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_action_bottom2:
                Intent mainIntent = new Intent(BusinessCartListActivity.this, AddressListActivity.class);
                mainIntent.putExtra("BusinessList", response);
                startActivity(mainIntent);

                break;
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final OnitemClick click;
        private ArrayList<BusinessDetailsModel> cart_list;

        public void updateProductList(ArrayList<BusinessDetailsModel> product_lis) {
            this.cart_list = product_lis;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final ImageView btn_watch_product;
            // public final LinearLayout mLayoutItem;
            public final TextView txt_business_name, txt_business_id, txt_business_total_amount, txt_min_order_limit;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                //   mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                txt_business_name = view.findViewById(R.id.txt_business_name);
                txt_business_id = view.findViewById(R.id.txt_business_id);
                txt_business_total_amount = view.findViewById(R.id.txt_business_total_amount);
                txt_min_order_limit = view.findViewById(R.id.txt_min_order_limit);
                btn_watch_product = view.findViewById(R.id.btn_watch_product);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<BusinessDetailsModel> product_list, OnitemClick clic) {
            this.cart_list = product_list;
            this.click = clic;
        }

        @Override
        public SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_businesscartlist_item, parent, false);
            return new SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final BusinessDetailsModel detail = cart_list.get(position);
            holder.txt_business_name.setText(detail.getBusinessName());
            holder.txt_business_id.setText(detail.getBusinessId());
            holder.txt_business_total_amount.setText("Rs." + detail.getBusinessTotalAmount());
            holder.txt_min_order_limit.setText(detail.getMinOrderLimit());
            holder.btn_watch_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CartListActivity.class);
                    intent.putExtra("CartItem", detail);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });


        /*    holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click != null) {
                        click.itemClick(position, "");
                    }
                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra("ProductMaster", detail.getProductDetails());
                    mContext.startActivity(intent);
                }
            });*/
        }


        @Override
        public int getItemCount() {
            return cart_list.size();
        }
    }


    @Override
    public void itemClick(int pos, String type) {
        /*if (type.equals("")) {
            BusinessDetailsModel response = business_list.get(pos);
            removeToCart(response);
        }*/
    }

  /*  private void removeToCart(CartModel productDetail) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObjectcustomer = new JSONObject();
            jsonObjectcustomer.put("customerId", Integer.parseInt(mainLoginResponse.getResult().getCustomerId()));
            JSONObject jsonObjectproductDetail = new JSONObject();
            jsonObjectproductDetail.put("cpId", productDetail.getCpId());
            jsonObject.put("customer", jsonObjectcustomer);
            jsonObject.put("cartProduct", jsonObjectproductDetail);


        } catch (Exception e) {
            e.printStackTrace();
        }

       *//* Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());*//*
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerRemoveToCart(jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(BusinessCartListActivity.this, BusinessCartListActivity.this, CUSTOMER_REMOVE_CART_LIST, true));
    }*/


    protected void setCartLayout() {


        if (business_list.size() > 0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)

            Intent mainIntent = new Intent(BusinessCartListActivity.this, MainCustomerDashboardActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainIntent = new Intent(BusinessCartListActivity.this, MainCustomerDashboardActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    private void getCartList() {

        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerCartList(mainLoginResponse.getResult().getCustomerId());
        walletDeductDetails.enqueue(new RestProcess<String>(BusinessCartListActivity.this, BusinessCartListActivity.this, CUSTOMER_CART_LIST, true));
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case CUSTOMER_CART_LIST:
                break;
            case CUSTOMER_REMOVE_CART_LIST:
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
            case CUSTOMER_CART_LIST:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                     response = new Gson().fromJson(jsonObject.toString(), CartListMainResponseModel.class);
                    business_list.clear();

                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult().getBusinessDetails() != null) {
                            if (response.getResult().getBusinessDetails().size() > 0) {
                                business_list.addAll(response.getResult().getBusinessDetails());
                                simpleStringRecyclerViewAdapter.updateProductList(business_list);
                                text_action_bottom1.setText("Rs." + response.getResult().getCartTotalAmount());

                            }

                        }


                    } else {

                        Toast.makeText(BusinessCartListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    setCartLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            /*case CUSTOMER_REMOVE_CART_LIST:
                try {
                    MainCustomerActivity.notificationCountCart--;
                    Toast.makeText(BusinessCartListActivity.this, "Item remove from cart.", Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    CartListMainResponseModel response = new Gson().fromJson(jsonObject.toString(), CartListMainResponseModel.class);
                    business_list.clear();
                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult().getCardProducts() != null) {
                            if (response.getResult().getCardProducts().size() > 0) {
                                business_list.addAll(response.getResult().getCardProducts());
                                simpleStringRecyclerViewAdapter.updateProductList(business_list);
                                text_action_bottom1.setText("Rs." + response.getResult().getTotalAmount());

                            }

                        }


                    } else {
                        Toast.makeText(BusinessCartListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    setCartLayout();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
*/
        }
    }


}