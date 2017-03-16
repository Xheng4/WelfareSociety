package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.BoutiqueBean;
import com.example.xheng.welfaresociety.model.bean.NewGoodsBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

/**
 * Created by xheng on 2017/3/16.
 */

public class BoutiqueModel implements IBoutiqueModle {
    @Override
    public void loadData(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
