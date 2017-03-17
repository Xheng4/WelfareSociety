package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.AlbumsBean;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.net.GoodsDescModel;
import com.example.xheng.welfaresociety.model.net.IGoodsDescModle;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.ui.widget.FlowIndicator;
import com.example.xheng.welfaresociety.ui.widget.MFGT;
import com.example.xheng.welfaresociety.ui.widget.SlideAutoLoopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDescActivity extends AppCompatActivity {

    IGoodsDescModle mModle;
    int goodsID = 0;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_goodsdesc_name)
    TextView mTvGoodsdescName;
    @BindView(R.id.tv_goodsdesc)
    TextView mTvGoodsdesc;
    @BindView(R.id.tv_goods_desc_price)
    TextView mTvGoodsDescPrice;
    @BindView(R.id.tv_goods_desc_price2)
    TextView mTvGoodsDescPrice2;
    @BindView(R.id.salv)
    SlideAutoLoopView mSalv;
    @BindView(R.id.indicator)
    FlowIndicator mIndicator;
    @BindView(R.id.wv_goods_desc)
    WebView mWvGoodsDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_desc);
        ButterKnife.bind(this);
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
                if (result == null) {
                    CommonUtils.showShortToast("没有数据");
                    return;
                }
                addData(result);
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void addData(GoodsDetailsBean bean) {
        mTvGoodsdescName.setText(bean.getGoodsEnglishName());
        mTvGoodsdesc.setText(bean.getGoodsName());
        mTvGoodsDescPrice.setText(bean.getCurrencyPrice());
        mTvGoodsDescPrice2.setText(bean.getShopPrice());
        mSalv.startPlayLoop(mIndicator,getUrl(bean),getCount(bean));
        mWvGoodsDesc.loadDataWithBaseURL(null,bean.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getCount(GoodsDetailsBean bean) {
        if (bean.getProperties() != null && bean.getProperties().length > 0) {
            return bean.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getUrl(GoodsDetailsBean bean) {
        AlbumsBean[] albums = bean.getProperties()[0].getAlbums();
        if (bean.getProperties() != null && bean.getProperties().length > 0) {
            if (albums!= null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0;i<albums.length;i++) {
                    urls[i] = albums[0].getImgUrl();
                }
                return urls;
            }
        }
        return null;
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        MFGT.finish(GoodsDescActivity.this);
    }
}
