package com.example.xheng.welfaresociety.model.net;

import android.content.Context;
import android.util.Log;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.NewGoodsBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

/**
 * Created by xheng on 2017/3/15.
 */

public class NewGoodsModel implements INewGoodsModel {

    @Override
    public void loadData(Context context,int catID, int pageID, OnCompleteListener<NewGoodsBean[]> listener) {
        String url = catID > 0 ? I.REQUEST_FIND_GOODS_DETAILS : I.REQUEST_FIND_NEW_BOUTIQUE_GOODS;
        Log.e("catID", "url：" + url);
        Log.e("catID", "catID：" + catID);
        Log.e("catID", "pageID：" + pageID);
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, String.valueOf(pageID))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }
}
