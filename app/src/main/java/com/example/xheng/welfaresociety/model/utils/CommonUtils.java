package com.example.xheng.welfaresociety.model.utils;

import android.widget.Toast;

import com.example.xheng.welfaresociety.application.FuLiApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(FuLiApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FuLiApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FuLiApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FuLiApplication.getInstance().getString(rId));
    }
}
