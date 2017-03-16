package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.BoutiqueBean;

/**
 * Created by xheng on 2017/3/16.
 */

public interface IBoutiqueModle {
    void loadData(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
