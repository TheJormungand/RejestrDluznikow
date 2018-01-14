package com.dlugi.dziki.rejestrdlugow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class GroupListAdapter extends ArrayAdapter<String> {
 private Activity context;
    private ArrayList<ArrayList<String>> groupList;
    public GroupListAdapter(Activity context, ArrayList<ArrayList<String>> groupList, ArrayList<String> elementCountList) {
        super(context, R.layout.grouplist_element,elementCountList);
        this.context = context;
        this.groupList = groupList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView =  layoutInflater.inflate(R.layout.grouplist_element, null, true);

        Button groupNameButton =  rowView.findViewById(R.id.groupButton);
        ImageButton groupSettings =  rowView.findViewById(R.id.groupSettings);

        groupNameButton.setText(groupList.get(position).get(1));
        if(groupList.get(position).get(2).equals("true")){
            groupSettings.setImageResource(R.drawable.admin_settings_icon);
        }else{
            groupSettings.setImageResource(R.drawable.user_settings_icon);
        }


        return rowView;
    }
}
