package com.allinone.smartocity.customer.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.order.MyOrderDetailsResponseModel;
import com.allinone.smartocity.model.order.SubMyOrderHomeResponseModel;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class CustomerOrderBusinessListActivity extends AppCompatActivity implements OnitemClick {
    private static Context mContext;
    private String TAG = "BusinessCartListActivity.class";
    private MainLoginResponse mainLoginResponse;
    private ArrayList<MyOrderDetailsResponseModel> business_list;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    TextView txt_pincode, txt_address_line1, txt_address_line2, txt_city, txt_state;
    private SubMyOrderHomeResponseModel businessDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_business_list);

        intializeWidgest();
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        mContext = CustomerOrderBusinessListActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        businessDetailsModel = (SubMyOrderHomeResponseModel) getIntent().getSerializableExtra("BusinessDetails");


        business_list = new ArrayList<>();
        business_list.addAll(businessDetailsModel.getOrderDetails());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(recyclerView, business_list, this);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);

        initilizeData();

    }

    private void initilizeData() {

    }

    private void intializeWidgest() {

    }


    @Override
    protected void onResume() {
        super.onResume();
      /*  if (InternetConnection.checkConnection(CustomerOrderBusinessListActivity.this)) {
            getCartList();
        } else {
            Toast.makeText(CustomerOrderBusinessListActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }*/

    }


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final OnitemClick click;
        private ArrayList<MyOrderDetailsResponseModel> sub_order_list;

        public void updateProductList(ArrayList<MyOrderDetailsResponseModel> product_lis) {
            this.sub_order_list = product_lis;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;


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

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<MyOrderDetailsResponseModel> product_list, OnitemClick clic) {
            this.sub_order_list = product_list;
            this.click = clic;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_businesscartlist_item, parent, false);
            return new SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final MyOrderDetailsResponseModel detail = sub_order_list.get(position);
            holder.txt_business_name.setText(detail.getOrderId());
            holder.txt_business_id.setText(detail.getCreateDate());
            holder.txt_business_total_amount.setText("Rs." + detail.getModifyDate());
            holder.txt_min_order_limit.setText(detail.getOrderCode());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CustomerOrderProductTrackDetailsActivity.class);
                    intent.putExtra("ProductTrackDetails", detail);

                    mContext.startActivity(intent);
                }
            });


        }


        @Override
        public int getItemCount() {
            return sub_order_list.size();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            finish();

        }

        return super.onOptionsItemSelected(item);
    }




 /*   private void getCartList() {

        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerCartList(mainLoginResponse.getResult().getCustomerId());
        walletDeductDetails.enqueue(new RestProcess<String>(CustomerOrderBusinessListActivity.this, CustomerOrderBusinessListActivity.this, CUSTOMER_CART_LIST, true));
    }*/


 /*   @Override
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

                        Toast.makeText(CustomerOrderBusinessListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    setCartLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            *//*case CUSTOMER_REMOVE_CART_LIST:
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
*//*
        }
    }
*/

}