package com.fxly.monkeytest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fxly.monkeytest.action.PublicAction;
import com.fxly.monkeytest.app.AppInfo;
import com.fxly.monkeytest.db.DataAdapter;
import com.fxly.monkeytest.db.DatabaseHandler;
import com.fxly.monkeytest.db.ListDataModel;
import com.fxly.monkeytest.monkey.CMDUtils;
import com.fxly.monkeytest.monkey.GetCommand;
import com.fxly.monkeytest.monkey.MonkeyRunnerThread;
import com.fxly.monkeytest.settings.SettingsFragment;
import com.fxly.monkeytest.ui.APPlistActivity;
import com.fxly.monkeytest.ui.EmailSettingsActivity;
import com.fxly.monkeytest.ui.SettingsUiActivity;
//import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.markushi.ui.CircleButton;

import static android.R.id.list;
import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;
import static com.fxly.monkeytest.action.PublicAction.getThrottleMillisec;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinner_choose_app;

    TextView myset;

    CircleButton start_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        intview();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private ArrayList<ListDataModel> listdatamodel;


    public void intview(){
        spinner_choose_app=(Spinner)findViewById(R.id.spinner_choose_app);
        myset=(TextView)findViewById(R.id.mysetting) ;
        SharedPreferences ss = getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//        spinner_choose_app.setSelectedIndex(ss.getInt("song_list", 0));

        loadData();
        spinner_choose_app.setSelection(ss.getInt("app_list", 0));

        spinner_choose_app.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences myshared = getSharedPreferences(APPSETTINGS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myshared.edit();
                editor.putInt("app_list", position);
//                editor.putString("testpackage","com.android.settings");
                editor.commit();

                String valueOf =String.valueOf(position+1);
                DatabaseHandler databaseHandler =new DatabaseHandler( MainActivity.this);
                databaseHandler.SaveKey((valueOf),MainActivity.this);

                Snackbar.make(view, String.format("点击 "+myshared.getString("test_appname","com.android.settings")), Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(),"未修改任意测试项目",Toast.LENGTH_SHORT).show();
            }
        });

        myset.setText("Monkeylogs: "+PublicAction.saveMonkeyLogs(MainActivity.this)+"\n"+
                "Logcat: "+ss.getBoolean("savelogcat",false)+"\n"+
                "EmailType:"+PublicAction.getEmailType(MainActivity.this)+"\n"
                +getThrottleMillisec(MainActivity.this)
        );


        start_btn= (CircleButton) findViewById(R.id.start_run_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myshared = getSharedPreferences(APPSETTINGS, Activity.MODE_PRIVATE);
                String testpackage=myshared.getString("test_appname","com.android.settings");
                String testcount=PublicAction.getRunCount(MainActivity.this);
                String throttle_time=PublicAction.getThrottleMillisec(MainActivity.this);
                String command="monkey -p "+testpackage+" --throttle "+throttle_time+" -v "+testcount;
                new MonkeyRunnerThread(command);
            }
        });
    }



    public void loadData(){
        DatabaseHandler db  = new DatabaseHandler(getApplicationContext());
        List<String> data = db.getAllLabels();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_choose_app.setAdapter(adapter);
//        ArrayAdapter<String> listadapter  =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);
//        list.setAdapter(listadapter);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_switch_language) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {

            startActivity(new Intent().setClass(MainActivity.this,SettingsFragment.class));
        }
//        else if (id == R.id.nav_email_settings) {
//            startActivity(new Intent().setClass(MainActivity.this,EmailSettingsActivity.class));
//        }
// else if (id == R.id.nav_manage) {
//
//        }
        else if (id == R.id.nav_share) {
            startActivity(new Intent().setClass(MainActivity.this,APPlistActivity.class));
        } else if (id == R.id.nav_send) {

        }else if(id == R.id.nav_about){
            startActivity(new Intent().setClass(MainActivity.this,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        public UiautomatorThread(String command) {

            CMDUtils.CMD_Result rs= CMDUtils.runCMD(command,true,true,MainActivity.this);
        }



        @Override
        public void run() {
            super.run();
//            String command=generateCommand("com.fxly.mytestcase", "TestOne", "demo");

        }

        /**
         * 生成命令
         * @param pkgName 包名
         * @param clsName 类名
         * @param mtdName 方法名
         * @return
         */
        public  String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument  --user 0 -w -r   -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;


            //         adb shell am instrument -w -r   -e debug false -e class com.fxly.mytestcase.TestOne#demo com.fxly.mytestcase.test/android.support.test.runner.AndroidJUnitRunner
        }
    }
}
