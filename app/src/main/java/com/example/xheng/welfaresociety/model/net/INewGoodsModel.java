package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.MessageBean;
import com.example.xheng.welfaresociety.model.bean.NewGoodsBean;

/**
 * Created by xheng on 2017/3/15.
 */

public interface INewGoodsModel {
    void loadData(Context context,int catID,int pageID,OnCompleteListener<NewGoodsBean[]> listener);

}
