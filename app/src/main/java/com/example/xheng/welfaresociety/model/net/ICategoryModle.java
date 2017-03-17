package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.BoutiqueBean;
import com.example.xheng.welfaresociety.model.bean.CategoryChildBean;
import com.example.xheng.welfaresociety.model.bean.CategoryGroupBean;

/**
 * Created by xheng on 2017/3/16.
 */

public interface ICategoryModle {
    void loadData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);
    void loadData(Context context,int parentID,OnCompleteListener<CategoryChildBean[]> listener);
}
