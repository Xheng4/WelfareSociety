package com.example.xheng.welfaresociety.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by xheng on 2017/3/14.
 */

public class FuLiApplication extends Application {
    static FuLiApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
