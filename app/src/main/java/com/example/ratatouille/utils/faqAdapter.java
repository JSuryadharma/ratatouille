package com.example.ratatouille.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ratatouille.R;

import java.util.ArrayList;
import java.util.HashMap;

public class faqAdapter extends BaseExpandableListAdapter {
    ArrayList<String> faqGroup;
    HashMap<String, ArrayList<String>> faqChild;

    public faqAdapter(ArrayList<String> faqGroup, HashMap<String, ArrayList<String>> faqChild) {
        this.faqGroup = faqGroup;
        this.faqChild = faqChild;
    }

    @Override
    public int getGroupCount() {
        return faqGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return faqChild.get((faqGroup.get(groupPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return faqChild.get(faqGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //Initialize View
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1,
                parent, false);

        //Initialize and assign variable
        TextView textView = convertView.findViewById(android.R.id.text1);
        //Initialize String
        String group = String.valueOf(getGroup(groupPosition));
        //Set text on textView
        textView.setText(group);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLACK);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Initialize view
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_selectable_list_item, parent, false);

        TextView textView = convertView.findViewById(android.R.id.text1);
        String child = String.valueOf(getChild(groupPosition,childPosition));

        textView.setText(child);
        textView.setTextColor(Color.DKGRAY);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
