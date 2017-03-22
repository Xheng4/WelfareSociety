package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.ui.fragment.BoutiqueFragment;
import com.example.xheng.welfaresociety.ui.fragment.CartFragment;
import com.example.xheng.welfaresociety.ui.fragment.CategoryFragment;
import com.example.xheng.welfaresociety.ui.fragment.NewGoodsFragment;
import com.example.xheng.welfaresociety.ui.fragment.PersonalFragment;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rb_new_goods)
    RadioButton mrbNewGoods;
    @BindView(R.id.rb_boutique)
    RadioButton mrbBoutique;
    @BindView(R.id.rb_category)
    RadioButton mrbCategory;
    @BindView(R.id.rb_cart)
    RadioButton mrbCart;
    @BindView(R.id.rb_personal_center)
    RadioButton mrbPersonalCenter;
    Unbinder unbinder;
    int index = 0;
    int oldIndex = 0;
    Fragment[] mFragments;
    RadioButton[] mRadioButtons;
    User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initFragment();
        initRadioButton();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mFragments[0])
                .add(R.id.frame_layout, mFragments[1])
                .add(R.id.frame_layout, mFragments[2])
                .add(R.id.frame_layout, mFragments[3])
                .add(R.id.frame_layout, mFragments[4])
                .hide(mFragments[1])
                .hide(mFragments[2])
                .hide(mFragments[3])
                .hide(mFragments[4])
                .show(mFragments[0])
                .commit();
    }

    private void initRadioButton() {
        mRadioButtons = new RadioButton[5];
        mRadioButtons[0] = (RadioButton) findViewById(R.id.rb_new_goods);
        mRadioButtons[1] = (RadioButton) findViewById(R.id.rb_boutique);
        mRadioButtons[2] = (RadioButton) findViewById(R.id.rb_category);
        mRadioButtons[3] = (RadioButton) findViewById(R.id.rb_cart);
        mRadioButtons[4] = (RadioButton) findViewById(R.id.rb_personal_center);
    }


    private void initFragment() {
        mFragments = new Fragment[5];
        mFragments[0] = new NewGoodsFragment();
        mFragments[1] = new BoutiqueFragment();
        mFragments[2] = new CategoryFragment();
        mFragments[3] = new CartFragment();
        mFragments[4] = new PersonalFragment();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_new_goods:
                index = 0;
                break;
            case R.id.rb_boutique:
                index = 1;
                break;
            case R.id.rb_category:
                index = 2;
                break;
            case R.id.rb_cart:
                mUser = FuLiApplication.getUser();
                if (mUser == null) {
                    MFGT.gotoLogin(MainActivity.this, I.REQUEST_CODE_LOGIN_FROM_CART);
                    return;
                }
                index = 3;
                break;
            case R.id.rb_personal_center:
                mUser = FuLiApplication.getUser();
                if (mUser == null) {
                    MFGT.gotoLogin(MainActivity.this, I.REQUEST_CODE_LOGIN);
                    return;
                }
                index = 4;
                break;
        }
        setTransaction();
    }

    private void setTransaction() {
        if (oldIndex != index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(mFragments[oldIndex])
                    .show(mFragments[index])
                    .commit();
            oldIndex = index;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index == 4 && FuLiApplication.getUser() == null) {
            index = 0;
        }
        setTransaction();
        setRadioButton();

    }

    private void setRadioButton() {
        for (int i = 0; i < mRadioButtons.length; i++) {
            if (i == oldIndex) {
                mRadioButtons[i].setChecked(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }
            setTransaction();
            setRadioButton();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
