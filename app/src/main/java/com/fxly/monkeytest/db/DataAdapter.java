package com.fxly.monkeytest.db;

/**
 * Created by Lambert Liu on 2017-01-03.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxly.monkeytest.R;

import java.util.ArrayList;
public class DataAdapter extends BaseAdapter {
    private ArrayList<ListDataModel> listdata;
    private Activity activity;
    private LayoutInflater inflater;

    public DataAdapter(ArrayList<ListDataModel> listdata, Activity activity) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            view=inflater.inflate(R.layout.list_view,null);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView phone= (TextView)view.findViewById(R.id.phone);
        ImageView icon= (ImageView) view.findViewById(R.id.icon);
        ListDataModel dataModel=listdata.get(position);
        name.setText(dataModel.getName());
        phone.setText(dataModel.getPackage());
        icon.setImageResource(dataModel.getIcon());

        return view;
    }
}
