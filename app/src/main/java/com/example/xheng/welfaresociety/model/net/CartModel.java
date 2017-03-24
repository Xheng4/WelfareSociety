package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.CartBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;
import com.example.xheng.welfaresociety.model.utils.OkHttpUtils;

/**
 * Created by xheng on 2017/3/24.
 */

public class CartModel implements ICartModel {
    @Override
    public void loadCart(Context context, String userName, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(CartBean[].class)
                .execute(listener);
    }

    @Override
    public void cartAction(Context context, int action, int count, String userName, String cartID, String goodsID, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        switch (action) {
            case I.ACTION_CART_ADD:
                addCart(utils, userName, goodsID, listener);
                break;
            case I.ACTION_CART_DEL:
                deleteCart(utils, cartID, listener);
                break;
            case I.ACTION_CART_UPDATA:
                updateCart(utils, cartID, count, listener);
        }

    }

    private void updateCart(OkHttpUtils<MessageBean> utils, String cartID, int count, OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, cartID)
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    private void deleteCart(OkHttpUtils<MessageBean> utils, String cartID, OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, cartID)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    private void addCart(OkHttpUtils<MessageBean> utils, String userName, String goodsID, OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME, userName)
                .addParam(I.Cart.GOODS_ID, goodsID)
                .addParam(I.Cart.COUNT, String.valueOf(1))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(0))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
