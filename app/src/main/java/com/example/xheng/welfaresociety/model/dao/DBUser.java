package com.example.xheng.welfaresociety.model.dao;

import android.content.Context;

import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.utils.SharedPreferencesUtils;

/**
 * Created by xheng on 2017/3/21.
 */

public class DBUser {

    public static final String USER_TABLE_NAME = "t_fulicenter_user";

    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_AVATAR = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_UPDATE_TIEM = "m_user_avatar_update_time";

    private static DBUser instance;

    public static DBUser getInstance(Context context) {
        if (instance == null) {
            instance = new DBUser(context);
        }
        return instance;
    }

    public DBUser(Context context) {
        DBManager.getInstance().initDB(context);
    }

    public boolean saveUserInfo(User user) {
        return DBManager.getInstance().saveUserInfo(user);
    }

    public User getUserInfo(String userName) {
        if (userName == null) {
            return null;
        } else {
            return DBManager.getInstance().getUserInfo(userName);
        }
    }

    public void closeDB() {
        FuLiApplication.setUser(null);
        SharedPreferencesUtils.getmUtils().removeUser();
        DBManager.getInstance().closeDB();
    }
}
