package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.Result;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.net.IUserModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.net.UserModel;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.model.utils.OnSetAvatarListener;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsAvatar extends AppCompatActivity {


    OnSetAvatarListener mOnSetAvatarListener;
    @BindView(R.id.iv_settings_avatar)
    ImageView mIvSettingsAvatar;
    String avatarName;
    User user = FuLiApplication.getUser();
    IUserModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_avatar);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initData();
    }

    private void initData() {
        User user = FuLiApplication.getUser();
//        mIvSettingsAvatar.setImageResource();
    }

    @OnClick({R.id.iv_title_back, R.id.iv_settings_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                MFGT.finish(SettingsAvatar.this);
                break;
            case R.id.iv_settings_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(SettingsAvatar.this, R.id.rl_avatar_settings,
                        getAvatar(), I.AVATAR_TYPE_USER_PATH);
                break;
        }
    }

    private String getAvatar() {
        user = FuLiApplication.getUser();
        avatarName = user.getMuserName() + System.currentTimeMillis();
        Log.e("avatar", "avatarName:" + avatarName);
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mOnSetAvatarListener.setAvatar(requestCode, data, mIvSettingsAvatar);
            if (requestCode == I.REQUEST_CODE_NICK) {
                CommonUtils.showShortToast(R.string.update_user_nick_success);
            }
            Log.e("avatar", "getAvatar");
            if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
                uploadAvatar();
            }
        }

    }

    private void uploadAvatar() {
//        File file =new File( OnSetAvatarListener.getAvatarPath(SettingsAvatar.this,user.getMuserName()));
        File file = new File(OnSetAvatarListener.getAvatarPath(this,
                user.getMavatarPath() + "/" + user.getMuserName() + user.getMavatarSuffix()));
        Log.e("avatar", "file-:" + file.exists());
        Log.e("avatar", "filePath-:" + file.getAbsolutePath());
        mModel.uploadAvatar(SettingsAvatar.this, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                Result json = ResultUtils.getResultFromJson(result, User.class);
                Log.e("avatar", "result onSuccess():" + result.toString());
                if (json != null) {
                    if (json.isRetMsg()) {
                        User data = (User) json.getRetData();
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(data),
                                SettingsAvatar.this, mIvSettingsAvatar);
                        CommonUtils.showShortToast(R.string.update_user_avatar_success);
                    } else {
                        CommonUtils.showShortToast(R.string.update_user_avatar_fail);
                    }
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(R.string.update_user_avatar_fail);
                Log.e("avatar", "result onError():" + error);
            }
        });
    }
}
