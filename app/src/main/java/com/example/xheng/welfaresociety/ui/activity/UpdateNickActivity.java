package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.model.bean.Result;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.dao.DBUser;
import com.example.xheng.welfaresociety.model.net.IUserModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.net.UserModel;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.L;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNickActivity extends AppCompatActivity {

    @BindView(R.id.et_update_nick)
    EditText mEtUpdateNick;

    User mUser;
    String newNick;
    IUserModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mUser = FuLiApplication.getUser();
        mModel = new UserModel();
        L.e("personal", "UpdateNickActivity");
    }

    @OnClick({R.id.iv_title_back, R.id.btn_update_nick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                MFGT.finish(UpdateNickActivity.this);
                break;
            case R.id.btn_update_nick:
                newNick = mEtUpdateNick.getText().toString().trim();
                L.e("personal", newNick);
                if (!checkEt()) {
                    return;
                }

                updateNick();
                break;
        }
    }

    private void updateNick() {
        mModel.updateNick(UpdateNickActivity.this, mUser.getMuserName(),
                newNick, new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        L.e("personal", "result:" + result.toString());
                        Result json = ResultUtils.getResultFromJson(result, User.class);
                        if (json != null) {
                            if (json.isRetMsg()) {
                                L.e("personal", "json:" + json.toString());
                                updateSuccess(json);
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(R.string.update_fail);
                    }
                });
    }

    private void updateSuccess(Result json) {
        User user = (User) json.getRetData();
        FuLiApplication.setUser(user);
        DBUser.getInstance(UpdateNickActivity.this).saveUserInfo(user);
        MFGT.finish(UpdateNickActivity.this);
    }

    private boolean checkEt() {

        String nick = null;
        if (mUser != null) {
            nick = mUser.getMuserNick();
        } else {
            L.e("personal", "result:mUser");
            return false;
        }
        if (TextUtils.isEmpty(newNick)) {
            mEtUpdateNick.requestFocus();
            mEtUpdateNick.setError(getString(R.string.nick_name_connot_be_empty));
            L.e("personal", "result:newNick" + newNick);
            return false;
        }
        if (newNick.equals(mUser)) {
            mEtUpdateNick.requestFocus();
            mEtUpdateNick.setError(getString(R.string.update_nick_fail_unmodify));
            L.e("personal", "result:mUser");
            return false;
        }
        return true;
    }
}
