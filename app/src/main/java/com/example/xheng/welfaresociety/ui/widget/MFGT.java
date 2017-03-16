package com.example.xheng.welfaresociety.ui.widget;

import android.app.Activity;
import android.content.Intent;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.ui.activity.BoutiqueChildActivity;
import com.example.xheng.welfaresociety.ui.activity.GoodsDescActivity;
import com.example.xheng.welfaresociety.ui.activity.MainActivity;

/**
 * Created by xheng on 2017/3/16.
 */

public class MFGT {
    public static void startActivity(Activity activity, Class cla) {
        activity.startActivity(new Intent(activity,cla));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void finish(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        activity.finish();
    }
    public static void gotoMain(Activity activity) {
        startActivity(activity, MainActivity.class);
    }

    public static void gotoDesc(Activity activity) {
        startActivity(activity, GoodsDescActivity.class);
    }

    public static void gotoBoutiqueChild(Activity activity, int id, String name) {
        activity.startActivity(new Intent(activity, BoutiqueChildActivity.class)
                .putExtra(I.NewAndBoutiqueGoods.CAT_ID, id).putExtra(I.Boutique.NAME,name));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
