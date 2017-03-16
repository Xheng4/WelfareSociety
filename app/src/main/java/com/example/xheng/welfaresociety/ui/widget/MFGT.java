package com.example.xheng.welfaresociety.ui.widget;

import android.app.Activity;
import android.content.Intent;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.ui.activity.DoodsDescActivity;
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
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        activity.finish();
    }
    public static void gotoMain(Activity activity) {
        startActivity(activity, MainActivity.class);
    }

    public static void gotoDesc(Activity activity) {
        startActivity(activity, DoodsDescActivity.class);
    }

}
