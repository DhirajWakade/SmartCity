package com.allinone.smartocity.customer.options;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.MainCustomerDashboardActivity;
import com.allinone.smartocity.customer.product.ItemDetailsActivity;
import com.allinone.smartocity.model.cart.BusinessDetailsModel;
import com.allinone.smartocity.model.cart.CartListMainResponseModel;
import com.allinone.smartocity.model.cart.CartModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.SharedPref;
import com.facebook.drawee.view.SimpleDraweeView;
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


public class CartListActivity extends AppCompatActivity implements RestCalllback, OnitemClick, View.OnClickListener {
    private static Context mContext;
    private String TAG = "CartListActivity.class";
    private MainLoginResponse mainLoginResponse;
    private ArrayList<CartModel> cart_list;
    private SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    private TextView text_action_bottom1, text_action_bottom2;
    private LinearLayout layoutCartItems, layoutCartPayments, layoutCartNoItems;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        mContext = CartListActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        text_action_bottom1 = findViewById(R.id.text_action_bottom1);
        text_action_bottom2 = findViewById(R.id.text_action_bottom2);
        text_action_bottom1.setVisibility(View.GONE);
        text_action_bottom2.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Show cart layout based on items

        Button bStartShopping = (Button) findViewById(R.id.bAddNew);
        bStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(CartListActivity.this, MainCustomerDashboardActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

        BusinessDetailsModel businessDetailsModel = (BusinessDetailsModel) getIntent().getSerializableExtra("CartItem");
        position = getIntent().getIntExtra("position",0);

        layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        cart_list = new ArrayList<>();
        cart_list.addAll(businessDetailsModel.getCardProducts());
        setCartLayout();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(recyclerView, cart_list, this);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);

        // getCartList();

        text_action_bottom2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_action_bottom2:
                startActivity(new Intent(CartListActivity.this, AddressListActivity.class));
                finish();

                break;
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final OnitemClick click;
        private ArrayList<CartModel> cart_list;

        public void updateProductList(ArrayList<CartModel> product_lis) {
            this.cart_list = product_lis;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove, mLayoutEdit;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<CartModel> product_list, OnitemClick clic) {
            this.cart_list = product_list;
            this.click = clic;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final CartModel detail = cart_list.get(position);
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


            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
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
            });
        }


        @Override
        public int getItemCount() {
            return cart_list.size();
        }
    }


    @Override
    public void itemClick(int pos, String type) {
        if (type.equals("")) {
            CartModel response = cart_list.get(pos);
            removeToCart(response);
        }
    }

    private void removeToCart(CartModel productDetail) {
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

       /* Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());*/
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerRemoveToCart(jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(CartListActivity.this, CartListActivity.this, CUSTOMER_REMOVE_CART_LIST, true));
    }


    protected void setCartLayout() {


        if (cart_list.size() > 0) {
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

         /*   Intent mainIntent = new Intent(CartListActivity.this, MainCustomerDashboardActivity.class);

            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);*/
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    /*    Intent mainIntent = new Intent(CartListActivity.this, MainCustomerDashboardActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);*/
        finish();
    }


    private void getCartList() {

        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getCustomerCartList(mainLoginResponse.getResult().getCustomerId());
        walletDeductDetails.enqueue(new RestProcess<String>(CartListActivity.this, CartListActivity.this, CUSTOMER_CART_LIST, true));
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
             /* case CUSTOMER_CART_LIST:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    CartListMainResponseModel response = new Gson().fromJson(jsonObject.toString(), CartListMainResponseModel.class);
                    cart_list.clear();

                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult().getCardProducts() != null) {
                            if (response.getResult().getCardProducts().size() > 0) {
                                cart_list.addAll(response.getResult().getCardProducts());
                                simpleStringRecyclerViewAdapter.updateProductList(cart_list);
                                text_action_bottom1.setText("Rs." + response.getResult().getTotalAmount());

                            }

                        }


                    } else {

                        Toast.makeText(CartListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    setCartLayout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;*/

            case CUSTOMER_REMOVE_CART_LIST:
                try {
                    MainCustomerDashboardActivity.notificationCountCart--;
                    Toast.makeText(CartListActivity.this, "Item remove from cart.", Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    CartListMainResponseModel response = new Gson().fromJson(jsonObject.toString(), CartListMainResponseModel.class);
                    cart_list.clear();
                    if (response.getStatus().equals("1")) {
                        // Toast.makeText(ProductListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult().getBusinessDetails().get(position).getCardProducts() != null) {
                            if (response.getResult().getBusinessDetails().get(position).getCardProducts().size() > 0) {
                                cart_list.addAll(response.getResult().getBusinessDetails().get(position).getCardProducts());
                                simpleStringRecyclerViewAdapter.updateProductList(cart_list);
                                // text_action_bottom1.setText("Rs." + response.getResult().getTotalAmount());

                            }

                        }


                    } else {
                        Toast.makeText(CartListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    setCartLayout();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }


}
