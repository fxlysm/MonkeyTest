package com.fxly.monkeytest.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;

/**
 * Created by Lambert Liu on 2017-01-03.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "appdatabase";
    private final static String TABLE_NAME = "appinfo";
    private final static int DATABASE_VERSION = 1;
    private static final String APPLIST_ID = "app_id";
    private static final String APP_NAME = "app_name";
    private static final String APP_PACKAGE = "package_name";


   public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + APPLIST_ID + " INTEGER PRIMARY KEY," + APP_NAME + " TEXT,"
                + APP_PACKAGE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }


    public void addAppPackage(ListDataModel userData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(APP_NAME, userData.getName());
        values.put(APP_PACKAGE, userData.getPackage());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public List<ListDataModel> getAllAPPs() {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ListDataModel data = new ListDataModel();
                data.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(APPLIST_ID))));
                data.setName(cursor.getString(cursor.getColumnIndex(APP_NAME)));
                data.setPackage(cursor.getString(cursor.getColumnIndex(APP_PACKAGE)));
                // Adding contact to list
                userList.add(data);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public void deleteUsers(ListDataModel userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, APPLIST_ID + " = ?",
                new String[]{String.valueOf(userData.getUser_id())});
        db.close();


    }



    //查找
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }


    public void SaveKey( String id ,Context context){
        SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.rawQuery("select * from "+TABLE_NAME+"  where "+APPLIST_ID+" like ?", new String[]{id});
                if(c.moveToFirst()) {
            String appname = c.getString(c.getColumnIndex(APP_PACKAGE));
                    SharedPreferences user = context.getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                    user.edit().putString("test_appname", appname).commit();
                    c.close();
        }
    }

}
