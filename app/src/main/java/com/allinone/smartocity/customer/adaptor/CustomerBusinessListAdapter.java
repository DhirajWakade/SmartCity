package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.retrofit.OnitemClick;

import java.util.ArrayList;
import java.util.List;

public class CustomerBusinessListAdapter extends RecyclerView.Adapter<CustomerBusinessListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<BusinessListResponse> BusinessList;

    //getting the context and product list with constructor
    public CustomerBusinessListAdapter(Context mCtx, List<BusinessListResponse> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.BusinessList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_business_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        BusinessListResponse response = BusinessList.get(position);

        //binding the data with the viewholder views
        holder.txt_business_name.setText(response.getBusinessName());
        holder.txt_pancard_no.setText(response.getPanCardNo());

        holder.txt_gst_no.setText("GAF5565552555");
        holder.txt_ifsc_code.setText(response.getBankDetails().getIfscCode());
        holder.txt_bank_name.setText(response.getBankDetails().getBankName());
        holder.txt_account_no.setText(response.getBankDetails().getAccountNo());
        // holder.textViewRating.setText(String.valueOf(product.getRating()));
        // holder.textViewPrice.setText(String.valueOf(product.getPrice()));)

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click!=null){
                    click.itemClick(position,"");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return BusinessList.size();
    }

    public void updateBusinessList(ArrayList<BusinessListResponse> result) {
        this.BusinessList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        //private  Button btn_add_product;
        TextView txt_business_name, txt_pancard_no, txt_gst_no, txt_ifsc_code,txt_bank_name,txt_account_no;
        ImageView btn_add_product;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_business_name = itemView.findViewById(R.id.txt_business_name);
            txt_pancard_no = itemView.findViewById(R.id.txt_pancard_no);
            txt_gst_no = itemView.findViewById(R.id.txt_gst_no);
            txt_ifsc_code = itemView.findViewById(R.id.txt_ifsc_code);
            txt_bank_name = itemView.findViewById(R.id.txt_bank_name);
            txt_account_no = itemView.findViewById(R.id.txt_account_no);
            btn_add_product = itemView.findViewById(R.id.btn_add_product);
            //imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
