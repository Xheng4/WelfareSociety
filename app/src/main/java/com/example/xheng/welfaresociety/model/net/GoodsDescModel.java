package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

/**
 * Created by xheng on 2017/3/16.
 */

public class GoodsDescModel implements IGoodsDescModle {
    @Override
    public void loadData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
}
