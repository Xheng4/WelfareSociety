package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.Result;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.dao.DBUser;
import com.example.xheng.welfaresociety.model.net.IUserModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.net.UserModel;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.MD5;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.model.utils.SharedPreferencesUtils;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    String userName, password;
    IUserModel mUserModel;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mUserModel = new UserModel();
    }

    @OnClick({R.id.iv_title_back, R.id.btn_login, R.id.btn_login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                MFGT.finish(this);
                break;
            case R.id.btn_login:
                onLogin();
                break;
            case R.id.btn_login_register:
                MFGT.gotoRegister(LoginActivity.this);
                break;
        }
    }

    private void onLogin() {
        if (!checkData()) {
            return;
        }
        mUserModel.login(this, userName, MD5.getMessageDigest(password), new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                Result res = ResultUtils.getResultFromJson(result, User.class);
                if (res != null) {
                    if (res.isRetMsg()) {
                        User user = (User) res.getRetData();
                        if (user != null) {
                            loginSuccess(user);
                        }
                    } else {
                        if (res.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showShortToast(R.string.login_fail_unknow_user);
                        }
                        if (res.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showShortToast(R.string.login_fail_error_password);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(R.string.login_fail);
            }
        });
    }

    private void loginSuccess(final User user) {
        Log.d("user", "loginSuccess" + user);
        FuLiApplication.setUser(user);
        SharedPreferencesUtils.getmUtils().setUserName(user.getMuserName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean info = DBUser.getInstance(LoginActivity.this).saveUserInfo(user);
                Log.d("user", "boolean info=" + info);
            }
        }).start();
        setResult(RESULT_OK);
        MFGT.finish(LoginActivity.this);
    }

    private boolean checkData() {
        userName = mEtLoginUsername.getText().toString().trim();
        password = mEtLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            mEtLoginUsername.requestFocus();
            mEtLoginUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (!userName.matches("[a-zA-Z]\\w{8,16}")) {
            mEtLoginUsername.requestFocus();
            mEtLoginUsername.setError(getString(R.string.illegal_user_name));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtLoginPassword.requestFocus();
            mEtLoginPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String s = data.getStringExtra(I.User.USER_NAME);
            mEtLoginUsername.setText(s);
        }
    }
}
