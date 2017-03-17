package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.CategoryChildBean;
import com.example.xheng.welfaresociety.model.bean.CategoryGroupBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

/**
 * Created by xheng on 2017/3/17.
 */

public class CategoryModle implements ICategoryModle {
    @Override
    public void loadData(Context context, OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void loadData(Context context, int parentID, OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(parentID))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
}
