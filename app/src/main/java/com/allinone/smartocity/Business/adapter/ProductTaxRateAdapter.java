package com.allinone.smartocity.Business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.home.TaxRatesItemModel;

import java.util.ArrayList;
import java.util.List;

public class ProductTaxRateAdapter extends ArrayAdapter<TaxRatesItemModel> {
    private Context mContext;
    private ArrayList<TaxRatesItemModel> list;



    public ProductTaxRateAdapter(Context context, int resource, List<TaxRatesItemModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.list = (ArrayList<TaxRatesItemModel>) objects;

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

        holder.mTextView.setText(list.get(position).getTxRate());


        return convertView;
    }

    public void updatecategory(ArrayList<TaxRatesItemModel> category_lis) {
        this.list = category_lis;
        notifyDataSetChanged();
    }


    private class ViewHolder {
        private TextView mTextView;

    }
}
