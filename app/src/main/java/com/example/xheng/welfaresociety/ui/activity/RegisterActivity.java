package com.example.xheng.welfaresociety.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.Result;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.net.IUserModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.net.UserModel;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.MD5;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_register_username)
    EditText mEtRegisterUsername;
    @BindView(R.id.et_register_nick)
    EditText mEtRegisterNick;
    @BindView(R.id.et_register_password)
    EditText mEtRegisterPassword;
    @BindView(R.id.et_register_password2)
    EditText mEtRegisterPassword2;
    IUserModel mModel;
    ProgressDialog mDialog;
    String username, password, password2, nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mModel = new UserModel();
    }

    private boolean checkData() {
        username = mEtRegisterUsername.getText().toString().trim();
        nick = mEtRegisterNick.getText().toString().trim();
        password = mEtRegisterPassword.getText().toString().trim();
        password2 = mEtRegisterPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mEtRegisterUsername.requestFocus();
            mEtRegisterUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            mEtRegisterUsername.requestFocus();
            mEtRegisterUsername.setError(getString(R.string.illegal_user_name));
            return false;
        }
        if (TextUtils.isEmpty(nick)) {
            mEtRegisterNick.requestFocus();
            mEtRegisterNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtRegisterPassword.requestFocus();
            mEtRegisterPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password2)) {
            mEtRegisterPassword2.requestFocus();
            mEtRegisterPassword2.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if (!password.equals(password2)) {
            mEtRegisterPassword2.requestFocus();
            mEtRegisterPassword2.setError(getString(R.string.two_input_password));
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_register_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                MFGT.finish(this);
                break;
            case R.id.btn_register:
                onRegister();
                break;
        }
    }

    private void onRegister() {
        if (!checkData()) {
            return;
        }
        mDialog = new ProgressDialog(RegisterActivity.this);
        mDialog.setMessage(getString(R.string.registering));
        mDialog.show();
        mModel.register(this, username, nick, MD5.getMessageDigest(password),
                new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Result json = ResultUtils.getResultFromJson(result, User.class);
                        if (json != null) {
                            if (json.isRetMsg()) {
                                registerSuccess();
                            } else if (json.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                                CommonUtils.showShortToast(R.string.register_fail_exists);
                            } else {
                                CommonUtils.showShortToast(R.string.register_fail);
                            }
                        }
                        mDialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        mDialog.dismiss();
                        CommonUtils.showShortToast(R.string.register_fail);
                    }
                });
    }

    private void registerSuccess() {
        setResult(RESULT_OK, new Intent().putExtra(I.User.USER_NAME, username));
        CommonUtils.showShortToast(R.string.register_success);
        MFGT.finish(RegisterActivity.this);
    }

}
