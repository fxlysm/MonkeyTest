package com.fxly.monkeytest.action;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.audiofx.BassBoost;

import com.fxly.monkeytest.R;
import com.fxly.monkeytest.app.AppInfo;
import com.fxly.monkeytest.db.DatabaseHandler;
import com.fxly.monkeytest.db.ListDataModel;

import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Lambert Liu on 2016-12-29.
 */

public class PublicAction {


    public final static String APPSETTINGS="settings";
    private static List<AppInfo> mlistAppInfo = null;

    public static String emailtype=null;
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }




    // 获得所有启动Activity的信息，类似于Launch界面
    public final static  void queryAppInfo( Context context) {
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
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
                DatabaseHandler db = new DatabaseHandler(context);
                db.addAppPackage(new ListDataModel(appLabel, pkgName));

                System.out.println("APPNAME: "+appLabel+"\n Package:"+pkgName);
                mlistAppInfo.add(appInfo); // 添加至列表中
            }
        }
    }





    public final static boolean getignorecrashes( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("ignore_crashes",false);
    }

    public final static boolean getignoretimeouts( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("ignore_timeouts",false);
    }
    public final static boolean get_ignore_security_exceptions( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("ignore_security_exceptions",false);
    }

    public final static boolean get_ignore_killprocess_aftererrror( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("ignore_killprocess_aftererrror",false);
    }
    public final static boolean getsent_report( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("sent_report",false);
    }
    public final static boolean saveMonkeyLogs( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("save_monkeylog",false);
    }

    public final static boolean saveLogcat( Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getBoolean("savelogcat",false);
    }


    public final static String getEmailAccount(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_account"," ");
    }
    public final static String getEmailPassword(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_password"," ");
    }

    public final static String getEmailSentServer(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_sendserver"," ");
    }
    public final static String getEmailSentPort(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_sendserver_port"," ");
    }
    public final static String getEmailInComingServer(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_incomingserver"," ");
    }
    public final static String getEmailInComingPort(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("email_incomingserver_prot"," ");
    }

    public  final static String getEmailType(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        int type=user.getInt("email_type",0);
        if (type==0){ emailtype="@126.com";}
        else if(type==1){ emailtype="@163.com";}
        else if(type==2){ emailtype="@qq.com";}

        return emailtype;
    }


    public final static String getThrottleMillisec(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("monkey_throttle","500");
    }
    public final static String getAppSwitchPercent(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("monkey_appswitch_percent","50");
    }
    public final static String getEventPercent(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("monkey_anyevent_percent","50");
    }
    public final static String getRunCount(Context context){
        SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        return user.getString("running_count","14400");
    }




//    public final static String QueryById( Context context,String id){
//        SQLiteDatabase db = context.getReadableDatabase();
////        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy, null );
////        Cursor c = db.rawQuery("select * from "+TABLE_NAME+" where username=?",new Stirng[]{id});
////        if(c.moveToFirst()) {
////            String password = c.getString(c.getColumnIndex("password"));
////        }
//        Cursor c = db.rawQuery("select * from "+TABLE_NAME+"  where "+APPLIST_ID+" like ?", new String[]{id});
//        if(c.moveToFirst()) {
//            String appname = c.getString(c.getColumnIndex(APP_NAME));
//        }
//        return  appname;
//    }


    public final static String GetAppName(){
//        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();

        return  "";
    }
}
