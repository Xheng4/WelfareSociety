package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

import java.net.URL;

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

    @Override
    public void CollectAction(Context context, int action, int goodsId, String userName, OnCompleteListener<MessageBean> listener) {
        String url = I.REQUEST_IS_COLLECT;
        switch (action) {
            case I.ACTION_ADD_COLLECT:
                url = I.REQUEST_ADD_COLLECT;
                break;
            case I.ACTION_DELETE_COLLECT:
                url = I.REQUEST_DELETE_COLLECT;
                break;
        }
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
