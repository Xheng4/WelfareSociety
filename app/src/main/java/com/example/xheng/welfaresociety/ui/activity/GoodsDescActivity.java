package com.example.xheng.welfaresociety.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.net.GoodsDescModel;
import com.example.xheng.welfaresociety.model.net.IGoodsDescModle;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.util.ArrayList;

public class GoodsDescActivity extends AppCompatActivity {

    IGoodsDescModle mModle;
    int goodsID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_desc);
        goodsID = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodsID == 0) {
            MFGT.finish(GoodsDescActivity.this);
        } else {
            mModle = new GoodsDescModel();
            initData();
        }
    }
    private void initData() {
        mModle.loadData(this, goodsID, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
