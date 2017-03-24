package com.example.xheng.welfaresociety.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.CollectBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.net.GoodsDescModel;
import com.example.xheng.welfaresociety.model.net.IGoodsDescModle;
import com.example.xheng.welfaresociety.model.net.IUserModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.net.UserModel;
import com.example.xheng.welfaresociety.model.utils.CommonUtils;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.adapter.MyCollectAdapter;
import com.example.xheng.welfaresociety.ui.widget.MFGT;
import com.example.xheng.welfaresociety.ui.widget.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCollectActivity extends AppCompatActivity {

    IUserModel mModel;
    static IGoodsDescModle mGoodsModle;

    User mUser;
    int mPageID = 1;

    GridLayoutManager mManager;
    ArrayList<CollectBean> mList;
    MyCollectAdapter mAdapter;
    Unbinder bind;

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_collect)
    SwipeRefreshLayout mSrlCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        bind = ButterKnife.bind(this);
        mModel = new UserModel();
        mGoodsModle = new GoodsDescModel();

        initView();
        initCollect(I.ACTION_DOWNLOAD);
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollect(I.ACTION_DOWNLOAD);
    }

    private void setListener() {
        refreshListener();
        footerListener();
    }

    private void footerListener() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mManager.findLastVisibleItemPosition();
                if (mRecyclerView.SCROLL_STATE_IDLE == newState
                        && mAdapter.isMore()
                        && lastPosition == mAdapter.getItemCount() - 1) {
                    mPageID++;
                    initCollect(I.ACTION_PULL_UP);
                }

            }
        });
    }

    private void refreshListener() {
        mSrlCollect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh(true);
                mPageID = 1;
                initCollect(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initCollect(final int action) {

        mUser = FuLiApplication.getUser();
        if (mUser == null) {
            MFGT.finish(this);
            return;
        }
        mModel.findCollects(this, mUser.getMuserName(), mPageID, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                if (result != null && result.length > 0) {
                    isRefresh(false);
                    mAdapter.setMore(true);
                    ArrayList<CollectBean> list = ResultUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    //如果新加载的一列不足十个 设置false
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                    Log.e("footer", "mPageID" + mPageID);
                    mAdapter.setFooter(mPageID == 1);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                Log.e("catID", " error:" + error);
                isRefresh(false);
            }
        });
    }

    private void initView() {
        mSrlCollect.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));
        mManager = new GridLayoutManager(this, I.COLUM_NUM);
        //增加判断优化页脚 居中
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mList.size()) {
                    return I.COLUM_NUM;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(mManager);
        mList = new ArrayList<>();
        mAdapter = new MyCollectAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);//自动修正大小
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
    }

    private void isRefresh(boolean isRefresh) {
        mSrlCollect.setRefreshing(isRefresh);
        mTvRefresh.setVisibility(isRefresh ? View.VISIBLE : View.GONE);
    }

    public void refreshCollect(int goodsID) {
        mGoodsModle.CollectAction(MyCollectActivity.this, I.ACTION_DELETE_COLLECT, goodsID,
                mUser.getMuserName(), new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            CommonUtils.showShortToast("删除成功");
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast("删除失败");
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
