package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

/**
 * Created by xheng on 2017/3/15.
 */

public interface INewGoodsModel {
    void loadData(Context context,int pageID,OnCompleteListener listener);
}
