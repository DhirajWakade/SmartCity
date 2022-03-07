package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;


public class HomeTopCategoriesAdaptor extends RecyclerView.Adapter<HomeTopCategoriesAdaptor.ViewHolder> {
    private final OnitemClick click;
    private final Context context;
    private List<BusinessTypeModel> topBusinessList;


    public void update(List<BusinessTypeModel> topBusinessLis) {
        this.topBusinessList = topBusinessLis;
        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final SimpleDraweeView mImageView;
        public final LinearLayout mLayoutItem;
        public final ImageView mImageViewWishlist;
        public final TextView txt_product_name;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
            mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
            mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
            txt_product_name = view.findViewById(R.id.txt_product_name);
        }
    }

    public HomeTopCategoriesAdaptor(Context context, List<BusinessTypeModel> topBusinessLis, OnitemClick clic) {
        this.topBusinessList = topBusinessLis;
        this.context = context;
        this.click = clic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_home_list_item, parent, false);
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


        holder.txt_product_name.setText(topBusinessList.get(position).getBusinessTypeName());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "");
                }
            }
        });

        Glide.with(context)
                .load(BASE_URL + "productImages/" + topBusinessList.get(position).getTopBusinessIconUrl())
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.mipmap.place_holder))
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return topBusinessList.size();
    }
}
