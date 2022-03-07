package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.allinone.smartocity.R;
import com.allinone.smartocity.model.product.FilterColorPojo;
import com.allinone.smartocity.retrofit.ApiEndPoint;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 13-05-2016.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public static ArrayList<FilterColorPojo> items;
    Context context;
    private OnitemClick itemClic = null;


    public ColorAdapter(Context context, ArrayList<FilterColorPojo> items, OnitemClick itemClick) {
        this.items = items;
        this.context = context;
        this.itemClic = itemClick;

    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_item_view, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // viewHolder.itemView.setSelected(selectedPos == position);
        final FilterColorPojo filterColorPojo = items.get(position);
        if (filterColorPojo.getSelectedPos() == 1) {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

        } else
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);

        viewHolder.txtColorName.setText("" + filterColorPojo.getColorName());

        Picasso.get().load(ApiEndPoint.BASE_URL + items.get(position).getColorImage()).into(viewHolder.imgProduct, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.imgProduct.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }


        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterColorPojo.getSelectedPos() != 1) {
                    if (itemClic != null) {
                        itemClic.itemClick(position, "color");

                    }
                }
            }
        });

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtColorName;
        public ImageView imgProduct;
        public ProgressBar progressBar;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtColorName = (TextView) itemLayoutView.findViewById(R.id.txtColorName);
            imgProduct = (ImageView) itemLayoutView.findViewById(R.id.imgProduct);
            progressBar = (ProgressBar) itemLayoutView.findViewById(R.id.progressBar);
        }


    }

    public void update(ArrayList<FilterColorPojo> sizeList) {
        this.items = sizeList;
        notifyDataSetChanged();
    }


}
