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
import com.example.xheng.welfaresociety.model.net.CartModel;
import com.example.xheng.welfaresociety.model.net.GoodsDescModel;
import com.example.xheng.welfaresociety.model.net.ICartModel;
import com.example.xheng.welfaresociety.model.net.IGoodsDescModle;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.utils.AntiShake;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.ui.widget.FlowIndicator;
import com.example.xheng.welfaresociety.ui.widget.MFGT;
import com.example.xheng.welfaresociety.ui.widget.SlideAutoLoopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class GoodsDescActivity extends AppCompatActivity {

    IGoodsDescModle mModle;
    ICartModel mCartModel;
    int goodsID = 0;
    int action = I.ACTION_IS_COLLECT;
    int cartAction = I.ACTION_CART_ADD;
    boolean isCollect;

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
    @BindView(R.id.iv_cart)
    ImageView mIvCart;


    AntiShake util = new AntiShake();


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
            mCartModel = new CartModel();
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
//        action = I.ACTION_IS_COLLECT;
        User user = FuLiApplication.getUser();
        if (user != null) {
            initCollect(action, user);

        } else {
            isCollect = false;
        }
    }

    private void addCart(User user) {
        mCartModel.cartAction(this, cartAction, 1, user.getMuserName(), null,
                String.valueOf(goodsID), new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            CommonUtils.showShortToast(R.string.add_cart_success);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(R.string.add_cart_fail);
                    }
                });
    }

    private void initCollect(final int action, User user) {
        mModle.CollectAction(GoodsDescActivity.this, action, goodsID, user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null) {
                    isCollect = action == I.ACTION_DELETE_COLLECT ? false : result.isSuccess();
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

    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish(GoodsDescActivity.this);
                break;
            case R.id.iv_collect:

                if (util.check(view.getId())) return;
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
            case R.id.iv_cart:
                user = FuLiApplication.getUser();
                if (user == null) {
                    MFGT.gotoLogin(GoodsDescActivity.this, 0);
                    return;
                }
                addCart(user);
                break;
        }
    }

    @OnClick(R.id.iv_share)
    public void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);

    }
}
