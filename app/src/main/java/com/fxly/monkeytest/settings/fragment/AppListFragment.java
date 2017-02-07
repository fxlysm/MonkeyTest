package com.fxly.monkeytest.settings.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fxly.monkeytest.R;
import com.fxly.monkeytest.app.AppInfo;
import com.fxly.monkeytest.db.DataAdapter;
import com.fxly.monkeytest.db.DatabaseHandler;
import com.fxly.monkeytest.db.ListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class AppListFragment extends Fragment {

    ListView apps_list;
    private ArrayList<ListDataModel> listdatamodel;
    private List<AppInfo> mlistAppInfo = null;

    private DataAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.activity_applist, container, false);


        apps_list=(ListView)rootview.findViewById(R.id.apps_list);
        mlistAppInfo = new ArrayList<AppInfo>();
        listdatamodel = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<ListDataModel> users = db.getAllAPPs();

        for (ListDataModel data : users) {
            String log = "Id: " + data.getUser_id() + " ,Name: " + data.getName() + " ,Package: " + data.getPackage();
            ListDataModel item = new ListDataModel();
            item.setName(data.getName());
            item.setPackage(data.getPackage());
            item.setIcon(R.mipmap.ic_launcher);
            listdatamodel.add(item);
            Log.d("Name: ", log);
        }

        adapter = new DataAdapter(listdatamodel, getActivity());
        apps_list.setAdapter(adapter);


        return  rootview;


    }
}
