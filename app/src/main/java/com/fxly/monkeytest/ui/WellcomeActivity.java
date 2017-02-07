package com.fxly.monkeytest.ui;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fxly.monkeytest.MainActivity;
import com.fxly.monkeytest.R;
import com.fxly.monkeytest.app.AppInfo;
import com.fxly.monkeytest.db.DatabaseHandler;
import com.fxly.monkeytest.db.ListDataModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;

public class WellcomeActivity extends AppCompatActivity {
    boolean mFirst ;
    SharedPreferences setting;

    private List<AppInfo> mlistAppInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        setting=getSharedPreferences("setting", 0);
        //打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences

        mFirst = setting.getBoolean("FIRST",true);
        //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        if(mFirst){



            startActivity(new Intent().setClass(WellcomeActivity.this, APPlistActivity.class));
            setting=getSharedPreferences("setting", 0);
            setting.edit().putBoolean("FIRST", false).commit();
            finish();

        }else {


  //          mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,5000);
            startActivity(new Intent().setClass(WellcomeActivity.this, MainActivity.class));
            finish();


        }




    }



    //*************************************************
    // Handler:跳转至不同页面
    //*************************************************
    private final static int SWITCH_MAINACTIVITY = 50;
    private final static int SWITCH_GUIDACTIVITY = 101;
    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SWITCH_MAINACTIVITY:
                    Intent mIntent = new Intent();
                    mIntent.setClass(WellcomeActivity.this, MainActivity.class);
                    WellcomeActivity.this.startActivity(mIntent);
                    WellcomeActivity.this.finish();
                    break;
                case SWITCH_GUIDACTIVITY:
                    mIntent = new Intent();
                    mIntent.setClass(WellcomeActivity.this, LoadDataActivity.class);
                    WellcomeActivity.this.startActivity(mIntent);
                    WellcomeActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };






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
//            SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//            user.edit().putBoolean("isFirstIn", false).commit();


        }

//        else {
//            //添加数据成功
//            Toast.makeText(getApplicationContext(),"添加数据成功",Toast.LENGTH_SHORT).show();
//            SharedPreferences user = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//            user.edit().putBoolean("isinputdb",false).commit();
//        }


    }

}
