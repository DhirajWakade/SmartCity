package com.allinone.smartocity.Business.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.order.CustomerOrderProductTrackDetailsActivity;
import com.allinone.smartocity.model.order.MyOrderDetailsResponseModel;
import com.allinone.smartocity.retrofit.OnitemClick;

import java.util.ArrayList;
import java.util.List;

public class OrderPendingAdapter extends RecyclerView.Adapter<OrderPendingAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mContext;

    //we are storing all the products in a list
    private List<MyOrderDetailsResponseModel> BusinessList;

    //getting the context and product list with constructor
    public OrderPendingAdapter(Context mCtx, List<MyOrderDetailsResponseModel> productList, OnitemClick clic) {
        this.mContext = mCtx;
        this.BusinessList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_order_pending_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        final MyOrderDetailsResponseModel detail = BusinessList.get(position);
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
        return BusinessList.size();
    }

    public void updateBusinessList(ArrayList<MyOrderDetailsResponseModel> result) {
        this.BusinessList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public final View mView;


        // public final LinearLayout mLayoutItem;
        public final TextView txt_business_name, txt_business_id, txt_business_total_amount, txt_min_order_limit;


        public MyViewHolder(View view) {
            super(view);

            mView = view;

            //   mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
            txt_business_name = view.findViewById(R.id.txt_business_name);
            txt_business_id = view.findViewById(R.id.txt_business_id);
            txt_business_total_amount = view.findViewById(R.id.txt_business_total_amount);
            txt_min_order_limit = view.findViewById(R.id.txt_min_order_limit);

        }
    }
}
