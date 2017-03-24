package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.model.bean.CartBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;

/**
 * Created by xheng on 2017/3/24.
 */

public interface ICartModel {
    void loadCart(Context context, String userName, OnCompleteListener<CartBean[]> listener);

    void cartAction(Context context, int action, int count, String userName, String cartID,
                    String goodsID, OnCompleteListener<MessageBean> listener);
}
