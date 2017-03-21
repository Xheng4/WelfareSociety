package com.example.xheng.welfaresociety.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xheng on 2017/3/21.
 */

public class OpenHelperDB extends SQLiteOpenHelper {

    private static final String CREATE_USER_DATABASE =
            "create table " + DBUser.USER_TABLE_NAME + " ( "
                    + DBUser.USER_COLUMN_NAME + " TEXT primary key,"
                    + DBUser.USER_COLUMN_NICK + " TEXT,"
                    + DBUser.USER_COLUMN_AVATAR + " INTEGER,"
                    + DBUser.USER_COLUMN_AVATAR_PATH + " TEXT,"
                    + DBUser.USER_COLUMN_AVATAR_SUFFIX + " TEXT,"
                    + DBUser.USER_COLUMN_AVATAR_TYPE + " INTEGER,"
                    + DBUser.USER_COLUMN_AVATAR_UPDATE_TIEM + " TEXT );";

    private static final int DATABASE_VERSION = 1;
    private static OpenHelperDB instance;

    private OpenHelperDB getInstance(Context context) {
        if (instance == null) {
            instance = new OpenHelperDB(context);
        }
        return instance;
    }

    public OpenHelperDB(Context context) {
        super(context, getUserDataBaseName(), null, DATABASE_VERSION);
    }

    private static String getUserDataBaseName() {
        return "com.example.xheng.welfaresociety.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (instance != null) {
            instance.close();
        }
        instance = null;
    }
}
