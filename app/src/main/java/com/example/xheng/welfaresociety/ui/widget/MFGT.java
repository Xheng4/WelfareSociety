package com.example.xheng.welfaresociety.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.ui.activity.BoutiqueChildActivity;
import com.example.xheng.welfaresociety.ui.activity.CategoryChildActivity;
import com.example.xheng.welfaresociety.ui.activity.GoodsDescActivity;
import com.example.xheng.welfaresociety.ui.activity.LoginActivity;
import com.example.xheng.welfaresociety.ui.activity.MainActivity;
import com.example.xheng.welfaresociety.ui.activity.MyCollectActivity;
import com.example.xheng.welfaresociety.ui.activity.OrderForGoodsActivity;
import com.example.xheng.welfaresociety.ui.activity.RegisterActivity;
import com.example.xheng.welfaresociety.ui.activity.SettingsActivity;
import com.example.xheng.welfaresociety.ui.activity.SettingsAvatar;
import com.example.xheng.welfaresociety.ui.activity.UpdateNickActivity;

/**
 * Created by xheng on 2017/3/16.
 */

public class MFGT {
    public static void startActivity(Activity activity, Class cla) {
        activity.startActivity(new Intent(activity,cla));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    public static void finish(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        activity.finish();
    }
    public static void gotoMain(Activity activity) {
        startActivity(activity, MainActivity.class);
    }


    public static void gotoBoutiqueChild(Activity activity, int id, String name) {
        activity.startActivity(new Intent(activity, BoutiqueChildActivity.class)
                .putExtra(I.NewAndBoutiqueGoods.CAT_ID, id).putExtra(I.Boutique.NAME,name));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void gotoDesc(Context activity, int id) {
        startActivity((Activity) activity,new Intent(activity,GoodsDescActivity.class)
                .putExtra(I.Goods.KEY_GOODS_ID,id));
    }

    public static void gotoCateChild(Context context, int id, String name) {
        startActivity((Activity) context, new Intent(context, CategoryChildActivity.class)
                .putExtra(I.NewAndBoutiqueGoods.CAT_ID, id)
                .putExtra(I.CategoryChild.NAME, name));
    }

    public static void gotoRegister(Activity activity) {
        startActivity(activity, RegisterActivity.class);
        startActivityForResult(activity, new Intent(activity, RegisterActivity.class), I.REQUEST_CODE_REGISTER);
    }

    public static void gotoLogin(Activity activity, int requestCode) {
        startActivityForResult(activity, new Intent(activity, LoginActivity.class), requestCode);
    }

    public static void gotoSettings(Activity activity) {
        startActivity(activity, SettingsActivity.class);
    }

    public static void gotoUpdateNick(Activity activity) {
        startActivity(activity, UpdateNickActivity.class);
    }

    public static void gotoAvatar(Activity activity) {
        startActivity(activity, SettingsAvatar.class);
    }

    public static void gotoMyCollectActivity(Activity activity) {
        startActivity(activity, MyCollectActivity.class);
    }

    public static void gotoOrder(Activity activity) {
        startActivity(activity, OrderForGoodsActivity.class);
    }
}
