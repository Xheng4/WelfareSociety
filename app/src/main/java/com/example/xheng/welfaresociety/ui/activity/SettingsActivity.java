package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.dao.DBUser;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.model.utils.L;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.iv_personal_avatar)
    ImageView mIvPersonalAvatar;
    @BindView(R.id.tv_personal_name)
    TextView mTvPersonalName;
    User user;
    @BindView(R.id.tv_personal_nick)
    TextView mTvPersonalNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        user = FuLiApplication.getUser();
        if (user != null) {
            L.e("personal", "SettingsActivity,initData");
            mTvPersonalName.setText(user.getMuserName());
            mTvPersonalNick.setText(user.getMuserNick());
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mIvPersonalAvatar);
        }


    }

    @OnClick({R.id.rl_pc_username,
            R.id.btn_personal_logout,
            R.id.rl_pc_nick,
            R.id.iv_title_back,
            R.id.iv_personal_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_pc_username:
                CommonUtils.showShortToast(R.string.username_connot_be_modify);
                break;
            case R.id.rl_pc_nick:
                L.e("personal");
                MFGT.gotoUpdateNick(SettingsActivity.this);
                break;
            case R.id.btn_personal_logout:
                onLogout();
                break;
            case R.id.iv_title_back:
                MFGT.finish(SettingsActivity.this);
                break;
            case R.id.iv_personal_avatar:
                MFGT.gotoAvatar(SettingsActivity.this);
                break;
        }
    }

    private void onLogout() {
        DBUser.getInstance(SettingsActivity.this).closeDB();
        MFGT.finish(SettingsActivity.this);
        MFGT.gotoLogin(SettingsActivity.this, I.REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = null;
        initData();
    }

    @OnClick()
    public void onClick() {
    }
}
