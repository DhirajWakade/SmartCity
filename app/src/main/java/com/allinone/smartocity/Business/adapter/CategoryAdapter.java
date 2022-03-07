package com.allinone.smartocity.Business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<BusinessTypeModel> {
    private Context mContext;
    private ArrayList<BusinessTypeModel> categoryList;
    private CategoryAdapter myAdapter;
  //  private boolean isFromView = false;

    public CategoryAdapter(Context context, int resource, List<BusinessTypeModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.categoryList = (ArrayList<BusinessTypeModel>) objects;
        this.myAdapter = this;
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
            convertView = layoutInflator.inflate(R.layout.spinner_category_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
           /* holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(categoryList.get(position).getBusinessTypeName());

        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.mCheckBox.setChecked(categoryList.get(position).isSelected());
       // isFromView = false;

       /* if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    categoryList.get(position).setSelected(isChecked);
                }
            }
        });*/
        return convertView;
    }

    public void updatecategory(ArrayList<BusinessTypeModel> category_lis) {
        this.categoryList = category_lis;
        notifyDataSetChanged();
    }


    public ArrayList<BusinessTypeModel> getCategoryList() {
        return categoryList;

    }

    private class ViewHolder {
        private TextView mTextView;
       // private CheckBox mCheckBox;
    }
}