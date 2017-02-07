package com.fxly.monkeytest.db;

/**
 * Created by Lambert Liu on 2017-01-03.
 */

public class ListDataModel {
    private String appname;
    private String apppackage;
    private int appicon;
    private int app_id;

    public ListDataModel(){}
    public ListDataModel(String name, String phone,int user_id,int icon) {
        this.appname = name;
        this.apppackage = phone;
        this.app_id = user_id;
        this.appicon=icon;
    }

    public ListDataModel(String name, String phone) {
        this.appname = name;
        this.apppackage = phone;
    }

    public String getName() {
        return appname;
    }

    public void setName(String name) {
        this.appname = name;
    }

    public String getPackage() {
        return apppackage;
    }

    public void setPackage(String phone) {
        this.apppackage = phone;
    }

    public int getUser_id() {
        return app_id;
    }

    public void setUser_id(int user_id) {
        this.app_id = user_id;
    }

    public int getIcon() {
        return appicon;
    }

    public void setIcon(int icon) {
        this.appicon = icon;
    }
}
