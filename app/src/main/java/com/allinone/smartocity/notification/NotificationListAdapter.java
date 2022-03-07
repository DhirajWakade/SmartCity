
package com.allinone.smartocity.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.notification.SubNotificationResultResponse;
import com.allinone.smartocity.retrofit.OnitemClick;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<SubNotificationResultResponse> notificationList;

    //getting the context and product list with constructor
    public NotificationListAdapter(Context mCtx, List<SubNotificationResultResponse> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.notificationList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_notification, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        SubNotificationResultResponse response = notificationList.get(position);

        if (response != null) {
            //binding the data with the viewholder views
            holder.txt_noti_title.setText(response.getNotifId());
            holder.txt_noti_sub_title.setText(response.getNotifText());
            holder.txt_noti_date.setText(response.getInsertTime());
            if (response.getReadstatus().equals("Not Read")) {
                holder.ll_noti.setBackgroundResource(R.color.white);
            } else {
                holder.ll_noti.setBackgroundResource(R.color.white);
            }
        }
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
       /* holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "");
                }
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void updatenotificationList(ArrayList<SubNotificationResultResponse> result) {
        this.notificationList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        //private  Button btn_add_product;
        TextView txt_noti_title, txt_noti_sub_title, txt_noti_date;
        LinearLayout ll_noti;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txt_noti_date = itemView.findViewById(R.id.txt_noti_date);
            txt_noti_sub_title = itemView.findViewById(R.id.txt_noti_sub_title);
            txt_noti_title = itemView.findViewById(R.id.txt_noti_title);
            ll_noti = itemView.findViewById(R.id.ll_noti);


        }
    }
}
