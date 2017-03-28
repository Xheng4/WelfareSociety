package com.example.xheng.welfaresociety.application;

import android.app.Application;
import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.User;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xheng on 2017/3/14.
 */

public class FuLiApplication extends Application {
    static FuLiApplication instance;
    static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ShareSDK.initSDK(this);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiApplication.user = user;
    }

    public static Context getInstance() {
        return instance;
    }
}
