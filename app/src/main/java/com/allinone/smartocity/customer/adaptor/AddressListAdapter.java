
package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.address.AddressModel;
import com.allinone.smartocity.retrofit.OnitemClick;

import java.util.ArrayList;
import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<AddressModel> BusinessList;

    //getting the context and product list with constructor
    public AddressListAdapter(Context mCtx, List<AddressModel> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.BusinessList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_address_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        AddressModel response = BusinessList.get(position);
        if (response != null) {
            //binding the data with the viewholder views
            holder.txt_pincode.setText("Pincode : "+response.getPinCode());
            holder.txt_address_line1.setText(response.getAddreLine1());
            holder.txt_address_line2.setText(response.getAddreLine2());
            holder.txt_city.setText(response.getCity());
            holder.txt_state.setText(response.getState());


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
        return BusinessList.size();
    }

    public void updateBusinessList(ArrayList<AddressModel> result) {
        this.BusinessList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        //private  Button btn_add_product;
        TextView txt_pincode, txt_address_line1, txt_address_line2, txt_city, txt_state;
        ImageView btn_add_product;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txt_pincode = itemView.findViewById(R.id.txt_pincode);
            txt_address_line1 = itemView.findViewById(R.id.txt_address_line1);
            txt_address_line2 = itemView.findViewById(R.id.txt_address_line2);
            txt_city = itemView.findViewById(R.id.txt_city);
            txt_state = itemView.findViewById(R.id.txt_state);

        }
    }
}
