package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.ui.fragment.BoutiqueFragment;
import com.example.xheng.welfaresociety.ui.fragment.CategoryFragment;
import com.example.xheng.welfaresociety.ui.fragment.NewGoodsFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mFragments[0])
                .add(R.id.frame_layout, mFragments[1])
                .add(R.id.frame_layout, mFragments[2])
                .hide(mFragments[1])
                .hide(mFragments[2])
                .show(mFragments[0])
                .commit();
    }

    private void initFragment() {
        mFragments = new Fragment[3];
        mFragments[0] = new NewGoodsFragment();
        mFragments[1] = new BoutiqueFragment();
        mFragments[2] = new CategoryFragment();
//        mFragments[3] = new BoutiqueFragment();
//        mFragments[4] = new BoutiqueFragment();
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
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
