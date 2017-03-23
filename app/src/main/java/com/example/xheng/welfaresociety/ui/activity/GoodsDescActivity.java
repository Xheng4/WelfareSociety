package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.AlbumsBean;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;
import com.example.xheng.welfaresociety.model.bean.User;
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
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;
    int action = I.ACTION_IS_COLLECT;
    boolean isCollect;
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
        Log.e("goodsID", "" + goodsID);
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
        mSalv.startPlayLoop(mIndicator, getUrl(bean), getCount(bean));
        mWvGoodsDesc.loadDataWithBaseURL(null, bean.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
        action = I.ACTION_IS_COLLECT;
        initCollect(action, FuLiApplication.getUser());
    }

    private void initCollect(final int action, User user) {
        mModle.CollectAction(GoodsDescActivity.this, action, goodsID, user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null) {
                    isCollect = result.isSuccess();
                    isCollect = action == I.ACTION_DELETE_COLLECT ? false : true;
                    Log.e("collect", "onSuccess-isCollect:" + isCollect);
                }

                setCollect();
            }

            @Override
            public void onError(String error) {
                isCollect = false;
                setCollect();
            }
        });
    }

    private void setCollect() {
        if (isCollect) {
            mIvCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvCollect.setImageResource(R.mipmap.bg_collect_in);
        }
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
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[0].getImgUrl();
                }
                return urls;
            }
        }
        return null;
    }

    @OnClick({R.id.iv_back, R.id.iv_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish(GoodsDescActivity.this);
                break;
            case R.id.iv_collect:
                User user = FuLiApplication.getUser();
                if (user == null) {
                    MFGT.gotoLogin(GoodsDescActivity.this, 0);
                    return;
                }
                if (isCollect) {
                    initCollect(I.ACTION_DELETE_COLLECT, user);
                    Log.e("collect", "btn:ACTION_DELETE_COLLECT");
                } else {
                    initCollect(I.ACTION_ADD_COLLECT, user);
                    Log.e("collect", "btn:ACTION_ADD_COLLECT");
                }
                break;
        }
    }
}
