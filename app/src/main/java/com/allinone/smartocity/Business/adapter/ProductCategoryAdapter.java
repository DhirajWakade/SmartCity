package com.allinone.smartocity.Business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.product.ProductTypeModel;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryAdapter extends ArrayAdapter<ProductTypeModel> {
    private Context mContext;
    private ArrayList<ProductTypeModel> categoryList;



    public ProductCategoryAdapter(Context context, int resource, List<ProductTypeModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.categoryList = (ArrayList<ProductTypeModel>) objects;

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_product_category_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(categoryList.get(position).getProductCatName());


        return convertView;
    }

    public void updatecategory(ArrayList<ProductTypeModel> category_lis) {
        this.categoryList = category_lis;
        notifyDataSetChanged();
    }


    private class ViewHolder {
        private TextView mTextView;

    }
}