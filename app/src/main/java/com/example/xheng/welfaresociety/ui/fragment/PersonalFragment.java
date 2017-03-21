package com.example.xheng.welfaresociety.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_user_qrcode)
    ImageView mIvUserQrcode;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;

    User mUser;
    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.iv_persona_center_msg, R.id.tv_center_settings, R.id.center_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_persona_center_msg:
                break;
            case R.id.tv_center_settings:
                break;
            case R.id.center_top:
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUser = FuLiApplication.getUser();
        if (mUser == null) {
            MFGT.gotoLogin(getActivity());
        } else {
            showUserInfo();
        }
    }

    private void showUserInfo() {

    }
}
