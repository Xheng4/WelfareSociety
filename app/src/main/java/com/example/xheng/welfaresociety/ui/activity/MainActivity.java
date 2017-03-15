package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.xheng.welfaresociety.R;

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
    }

    public void onCheckedChange(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}