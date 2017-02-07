package com.fxly.monkeytest.ui;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fxly.monkeytest.MainActivity;
import com.fxly.monkeytest.R;
import com.fxly.monkeytest.app.AppInfo;
import com.fxly.monkeytest.app.BrowseApplicationInfoAdapter;
import com.fxly.monkeytest.db.DataAdapter;
import com.fxly.monkeytest.db.DatabaseHandler;
import com.fxly.monkeytest.db.ListDataModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;

public class APPlistActivity extends AppCompatActivity  {
    private ListView listView;
    private ArrayList<ListDataModel> listdatamodel;

    private List<AppInfo> mlistAppInfo = null;


    private DataAdapter adapter;

    boolean isFirstIn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);

        listView = (ListView) findViewById(R.id.apps_list);
//        mlistAppInfo = new ArrayList<AppInfo>();
//        listView = (ListView) findViewById(R.id.list_view);
        mlistAppInfo = new ArrayList<AppInfo>();
        listdatamodel = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(this);

//        BrowseApplicationInfoAdapter browseAppAdapter = new BrowseApplicationInfoAdapter(
//                this, mlistAppInfo);
//        listview.setAdapter(browseAppAdapter);
//        listview.setOnItemClickListener(this);



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

        adapter = new DataAdapter(listdatamodel, this);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

                        new AlertDialog.Builder(APPlistActivity.this)
                                .setTitle("提示")
                                .setMessage("确定删除?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        setResult(RESULT_OK);//确定按钮事件
                                        listdatamodel.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //取消按钮事件
                                    }
                                })
                                .show();




                        return false;
                    }



                });


        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"click "+position,Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
//        File dbFile = this.getDatabasePath("appdatabase.db");
//        File file=getExternalCacheDir();
//        if(dbFile.exists() == true){
//
//        }else {
//            queryAppInfo(); // 查询所有应用程序信息
////            finish();
////            Intent intent = new Intent(APPlistActivity.this, APPlistActivity.class);
////            startActivity(intent);
//
//        }
        SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        isFirstIn = user.getBoolean("isFirstIn", true);
        if(isFirstIn){
            queryAppInfo(); // 查询所有应用程序信息

//           final ProgressDialog   proDialog = android.app.ProgressDialog.show(APPlistActivity.this, "提示", "首次进入加载测试数据！请稍后...");
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    proDialog.dismiss();//万万不可少这句，否则会程序会卡死。
                }

            };
            thread.start();
            startActivity(new Intent().setClass(APPlistActivity.this, MainActivity.class));
            finish();
        }else {
//            startActivity(new Intent().setClass(APPlistActivity.this, MainActivity.class));
//            finish();
        }


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.applist, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_sync_app) {
//            queryAppInfo(); // 查询所有应用程序信息
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    // 获得所有启动Activity的信息，类似于Launch界面
    public void queryAppInfo() {
        PackageManager pm = this.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm
                .queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));
        if (mlistAppInfo != null) {
            mlistAppInfo.clear();
            for (ResolveInfo reInfo : resolveInfos) {
                String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
                String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
                String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
                Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
                // 为应用程序的启动Activity 准备Intent
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(pkgName,
                        activityName));
                // 创建一个AppInfo对象，并赋值
                AppInfo appInfo = new AppInfo();
                appInfo.setAppLabel(appLabel);
                appInfo.setPkgName(pkgName);
                appInfo.setAppIcon(icon);
                appInfo.setIntent(launchIntent);
                DatabaseHandler db = new DatabaseHandler(this);
                db.addAppPackage(new ListDataModel(appLabel, pkgName));

                System.out.println("APPNAME: "+appLabel+"\n Package:"+pkgName);
                mlistAppInfo.add(appInfo); // 添加至列表中



            }

            //添加数据成功
            Toast.makeText(getApplicationContext(),"添加数据成功",Toast.LENGTH_SHORT).show();
            SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
            user.edit().putBoolean("isFirstIn", false).commit();
        }

//        else {
//            //添加数据成功
//            Toast.makeText(getApplicationContext(),"添加数据成功",Toast.LENGTH_SHORT).show();
//            SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//            user.edit().putBoolean("isinputdb",false).commit();
//        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//        user.edit().putBoolean("isFirstIn", false).commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.applist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {

//            startActivity(new Intent().setClass(APPlistActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
