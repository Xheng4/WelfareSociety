package com.example.xheng.welfaresociety.model.net;

import android.content.Context;

/**
 * Created by xheng on 2017/3/20.
 */

public interface IUserModel {
    void login(Context context, String userName, String password, OnCompleteListener<String> listener);

    void register(Context context, String userName, String nick, String password, OnCompleteListener<String> listener);

    void updateNick(Context context, String userName, String nick, OnCompleteListener<String> listener);
}
