package com.allinone.smartocity.Business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<ProductDetail> productList;

    //getting the context and product list with constructor
    public ProductListAdapter(Context mCtx, List<ProductDetail> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_product_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        ProductDetail response = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_product_name.setText(response.getProductMaster().getProductName());
        holder.txt_price.setText("Rs. " + response.getFinalPrice());

        holder.txt_description.setText(response.getProductMaster().getDesciption());
        holder.btn_update_product.setVisibility(View.GONE);
        holder.txt_color.setText(response.getProductMaster().getProductColor());
         holder.txt_available_quantity.setText(response.getAvailableQty());
        // holder.txt_account_no.setText(response.getBankDetails().getAccountNo());
        // holder.textViewRating.setText(String.valueOf(product.getRating()));
        // holder.textViewPrice.setText(String.valueOf(product.getPrice()));)

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.btn_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "delete");
                }
            }
        });

        holder.btn_update_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "edit");
                }
            }
        });


        Glide.with(mCtx)
                .load(BASE_URL +"productImages/" +response.getProductMaster().getProductImageUrl())
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.mipmap.place_holder))
                .into(holder.image_product);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProductList(ArrayList<ProductDetail> result) {
        this.productList = result;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_product_name, txt_price, txt_description, txt_color, txt_available_quantity;
        ImageView image_product, btn_delete_product, btn_update_product;
        LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_color = itemView.findViewById(R.id.txt_color);
            txt_available_quantity = itemView.findViewById(R.id.txt_available_quantity);

            image_product = itemView.findViewById(R.id.image_product);
            btn_delete_product = itemView.findViewById(R.id.btn_delete_product);
            btn_update_product = itemView.findViewById(R.id.btn_update_product);
            ll_item = itemView.findViewById(R.id.ll_item);

        }
    }
}
