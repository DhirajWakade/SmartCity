
package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.order.SubMyOrderHomeResponseModel;
import com.allinone.smartocity.retrofit.OnitemClick;

import java.util.ArrayList;
import java.util.List;

public class MyOrderHomeListAdapter extends RecyclerView.Adapter<MyOrderHomeListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<SubMyOrderHomeResponseModel> orderList;

    //getting the context and product list with constructor
    public MyOrderHomeListAdapter(Context mCtx, List<SubMyOrderHomeResponseModel> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.orderList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_customer_order_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        SubMyOrderHomeResponseModel response = orderList.get(position);
        if (response != null) {

            holder.txt_order_id.setText("Main Order Id : "+response.getMainOrderCode());
         //  holder.txt_main_order_id.setText(response.getMainOrderId());
            holder.main_order_status.setText(response.getMainOrderStatus());
            holder.txt_order_created_date.setText(response.getCreateDate());



        }
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateBusinessList(ArrayList<SubMyOrderHomeResponseModel> result) {
        this.orderList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        //private  Button btn_add_product;
        TextView txt_order_id, txt_main_order_id, main_order_status, txt_order_created_date;
        ImageView btn_add_product;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
         //   txt_main_order_id = itemView.findViewById(R.id.txt_main_order_id);
            main_order_status = itemView.findViewById(R.id.main_order_status);
            txt_order_created_date = itemView.findViewById(R.id.txt_order_created_date);


        }
    }
}
