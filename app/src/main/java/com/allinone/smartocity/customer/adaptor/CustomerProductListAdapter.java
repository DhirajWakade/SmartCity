package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.product.ProductDetail;
import com.allinone.smartocity.retrofit.ApiEndPoint;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CustomerProductListAdapter extends RecyclerView.Adapter<CustomerProductListAdapter.MyViewHolder> {
    private final OnitemClick click;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<ProductDetail> productList;

    //getting the context and product list with constructor
    public CustomerProductListAdapter(Context mCtx, List<ProductDetail> productList, OnitemClick clic) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.click = clic;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.mImageView.getController() != null) {
            holder.mImageView.getController().onDetach();
        }
        if (holder.mImageView.getTopLevelDrawable() != null) {
            holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //getting the product of the specified position
        ProductDetail response = productList.get(position);

        //binding the data with the viewholder views
        holder.txt_product_name.setText(response.getProductMaster().getProductName() + "(" + response.getProductMaster().getProductCode() + ")");
        holder.txt_price.setText("Rs. " + response.getFinalPrice());

        //holder.txt_size.setText("");
        // holder.txt_color.setText(response.getProductMaster().getProductColor());
        // holder.txt_available_quantity.setText(response.getAvailableQty());
        // holder.txt_account_no.setText(response.getBankDetails().getAccountNo());
        // holder.textViewRating.setText(String.valueOf(product.getRating()));
        // holder.textViewPrice.setText(String.valueOf(product.getPrice()));)

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "TopProduct");
                }
            }
        });
        final Uri uri = Uri.parse(ApiEndPoint.BASE_URL + "productImages/" + response.getProductMaster().getProductImageUrl());

        holder.mImageView.setImageURI(uri);
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


        TextView txt_product_name, txt_price;
        //txt_size, txt_color, txt_available_quantity;

        public final SimpleDraweeView mImageView;
        public final LinearLayout mLayoutItem;
        public final ImageView mImageViewWishlist;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_price = itemView.findViewById(R.id.txt_price);
           /* txt_size = itemView.findViewById(R.id.txt_size);
            txt_color = itemView.findViewById(R.id.txt_color);
            txt_available_quantity = itemView.findViewById(R.id.txt_available_quantity);*/

            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.image1);
            mLayoutItem = (LinearLayout) itemView.findViewById(R.id.layout_item);
            mImageViewWishlist = (ImageView) itemView.findViewById(R.id.ic_wishlist);

        }
    }
}
