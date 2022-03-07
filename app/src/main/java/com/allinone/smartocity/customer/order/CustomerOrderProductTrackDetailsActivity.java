package com.allinone.smartocity.customer.order;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.options.CartListActivity;
import com.allinone.smartocity.customer.product.ItemDetailsActivity;
import com.allinone.smartocity.model.cart.CartModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.order.MyOrderDetailsResponseModel;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.util.SharedPref;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class CustomerOrderProductTrackDetailsActivity extends AppCompatActivity implements OnitemClick, View.OnClickListener {
    private static Context mContext;
    private String TAG = "CustomerOrderProductTrackDetailsActivity.class";
    private MainLoginResponse mainLoginResponse;
    private ArrayList<CartModel> product_list;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    TextView txt_pincode, txt_address_line1, txt_address_line2, txt_city, txt_state;
    private MyOrderDetailsResponseModel producTrackList;
    private ImageView img_arrow_up,img_arrow_down,img_item_details_arrow_up,img_item_details_arrow_down;
    private LinearLayout ll_address_details,ll_product_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_product_track_details_list);

        intializeWidgest();
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        mContext = CustomerOrderProductTrackDetailsActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        producTrackList = (MyOrderDetailsResponseModel) getIntent().getSerializableExtra("ProductTrackDetails");


        product_list = new ArrayList<>();
        product_list.addAll(producTrackList.getOrderProducts());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(recyclerView, product_list, this);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);

        initilizeData();
        intializeListener();

    }

    private void intializeListener() {
        img_arrow_up.setOnClickListener(this);
        img_arrow_down.setOnClickListener(this);

        img_item_details_arrow_up.setOnClickListener(this);
        img_item_details_arrow_down.setOnClickListener(this);
    }

    private void initilizeData() {
        txt_pincode.setText(producTrackList.getAddress().getPinCode());
        txt_address_line1.setText(producTrackList.getAddress().getAddreLine1());
        txt_address_line2.setText(producTrackList.getAddress().getAddreLine2());
        txt_city.setText(producTrackList.getAddress().getCity());
        txt_state.setText(producTrackList.getAddress().getState());
    }

    private void intializeWidgest() {
        txt_pincode = findViewById(R.id.txt_pincode);
        txt_address_line1 = findViewById(R.id.txt_address_line1);
        txt_address_line2 = findViewById(R.id.txt_address_line2);
        txt_city = findViewById(R.id.txt_city);
        txt_state = findViewById(R.id.txt_state);
        img_arrow_up = findViewById(R.id.img_arrow_up);
        img_arrow_down = findViewById(R.id.img_arrow_down);
        ll_address_details = findViewById(R.id.ll_address_details);

        img_item_details_arrow_up = findViewById(R.id.img_item_details_arrow_up);
        img_item_details_arrow_down = findViewById(R.id.img_item_details_arrow_down);
        ll_product_details = findViewById(R.id.ll_product_details);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_arrow_down:
                ll_address_details.setVisibility(View.VISIBLE);
                img_arrow_down.setVisibility(View.GONE); img_arrow_up.setVisibility(View.VISIBLE);
                break;

            case R.id.img_arrow_up:
                ll_address_details.setVisibility(View.GONE);
                img_arrow_down.setVisibility(View.VISIBLE); img_arrow_up.setVisibility(View.GONE);
                break;


            case R.id.img_item_details_arrow_down:
                ll_product_details.setVisibility(View.VISIBLE);
                img_item_details_arrow_down.setVisibility(View.GONE); img_item_details_arrow_up.setVisibility(View.VISIBLE);
                break;

            case R.id.img_item_details_arrow_up:
                ll_product_details.setVisibility(View.GONE);
                img_item_details_arrow_down.setVisibility(View.VISIBLE); img_item_details_arrow_up.setVisibility(View.GONE);
                break;
        }
    }


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final OnitemClick click;
        private ArrayList<CartModel> product_list;

        public void updateProductList(ArrayList<CartModel> product_lis) {
            this.product_list = product_lis;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<CartModel> product_lis, OnitemClick clic) {
            this.product_list = product_lis;
            this.click = clic;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_track_list_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final CartModel detail = product_list.get(position);
            final Uri uri = Uri.parse(BASE_URL + "productImages/" + detail.getProductDetails().getProductMaster().getProductImageUrl());
            holder.mImageView.setImageURI(uri);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra("ProductMaster", detail.getProductDetails());
                    mContext.startActivity(intent);
                }
            });


        }
        @Override
        public int getItemCount() {
            return product_list.size();
        }
    }


    @Override
    public void itemClick(int pos, String type) {
      /*  if (type.equals("")) {
            CartModel response = cart_list.get(pos);
            removeToCart(response);
        }*/
    }

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