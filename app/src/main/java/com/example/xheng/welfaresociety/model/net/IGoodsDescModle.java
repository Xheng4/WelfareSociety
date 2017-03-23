package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.BoutiqueBean;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;

/**
 * Created by xheng on 2017/3/16.
 */

public interface IGoodsDescModle {
    void loadData(Context context,int goodsId, OnCompleteListener<GoodsDetailsBean> listener);

    void loadCollectStatus(Context context, int goodsId, String userName, OnCompleteListener<MessageBean> listener);
}
