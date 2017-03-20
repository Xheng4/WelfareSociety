package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.ui.fragment.NewGoodsFragment;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryChildActivity extends AppCompatActivity {
    boolean sortPrice, sortTime;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    NewGoodsFragment mNewGoodsFragment;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_select_price)
    Button mTvSelectPrice;
    @BindView(R.id.tv_select_time)
    Button mTvSelectTime;
    @BindView(R.id.fragment_cc)
    FrameLayout mFragmentCc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_cc, mNewGoodsFragment)
                .commit();
        mTextView.setText(getIntent().getStringExtra(I.CategoryChild.NAME));

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.tv_select_price, R.id.tv_select_time})
    public void onSortBy(View view) {
        switch (view.getId()) {
            case R.id.tv_select_price:
                sortBy = sortPrice ? I.SORT_BY_PRICE_DESC : I.SORT_BY_PRICE_ASC;
                sortPrice = !sortPrice;

                break;
            case R.id.tv_select_time:
                sortBy = sortTime ? I.SORT_BY_ADDTIME_DESC : I.SORT_BY_ADDTIME_ASC;
                sortTime = !sortTime;
                break;
        }
        mNewGoodsFragment.setSort(sortBy);
    }

    @OnClick(R.id.textView)
    public void onPopwindow() {

    }
}
