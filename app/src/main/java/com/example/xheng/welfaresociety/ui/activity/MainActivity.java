package com.example.xheng.welfaresociety.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.xheng.welfaresociety.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, new NewGoodsFragment())
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rb_new_goods:

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
