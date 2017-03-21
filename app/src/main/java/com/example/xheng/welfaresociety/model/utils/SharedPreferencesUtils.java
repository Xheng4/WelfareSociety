package com.example.xheng.welfaresociety.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.xheng.welfaresociety.application.FuLiApplication;

/**
 * Created by xheng on 2017/3/21.
 */

public class SharedPreferencesUtils {
    private static final String SHARE_PREFRENCE_NAME = "com.example.xheng.welfaresociety.prefrence";
    private static final String USERINFO_NAME = "m_user_username";
    static SharedPreferencesUtils mUtils;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    public SharedPreferencesUtils() {
        mPreferences = FuLiApplication.getInstance().getSharedPreferences(SHARE_PREFRENCE_NAME,
                Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedPreferencesUtils getmUtils() {
        if (mUtils == null) {
            mUtils = new SharedPreferencesUtils();
        }
        return mUtils;
    }

    public void setUserName(String userName) {
        mEditor.putString(USERINFO_NAME, userName).commit();
    }

    public String getUserName() {
        return mPreferences.getString(USERINFO_NAME, null);
    }

    public void removeUser() {
        mEditor.remove(USERINFO_NAME).commit();
    }
}
