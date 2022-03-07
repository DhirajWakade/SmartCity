package com.allinone.smartocity.customer.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
     ArrayList<String> title;
    HashMap<String, ArrayList<BusinessTypeModel>> list_item;

    public ExpandableListAdapter(Context context, ArrayList<String> title, HashMap<String, ArrayList<BusinessTypeModel>> list_item) {
        this.context = context;
        this.title = title;
        this.list_item = list_item;
    }

    @Override
    public int getGroupCount() {
        return list_item.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list_item.get(title.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return title.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.title_list, parent, false);
        }

        TextView text;
        ImageView image;

        text = (TextView) convertView.findViewById(R.id.text);
        image = (ImageView) convertView.findViewById(R.id.image);

        text.setText(title.get(groupPosition));

        if (isExpanded) {
            image.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        } else {
            image.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_list, parent, false);
        }

        TextView name;
        ImageView image;

        name = (TextView) convertView.findViewById(R.id.name);
        image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(list_item.get(title.get(groupPosition)).get(childPosition).getBusinessTypeName());

       // image.setImageResource(list_item.get(title.get(groupPosition)).get(childPosition).getImage());

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void updatelist(ArrayList<String> title, HashMap<String, ArrayList<BusinessTypeModel>> list_item) {
        this.title = title;
        this.list_item = list_item;
        notifyDataSetChanged();
    }
}