package com.example.xheng.welfaresociety.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.NewGoodsBean;
import com.example.xheng.welfaresociety.model.net.INewGoodsModel;
import com.example.xheng.welfaresociety.model.net.NewGoodsModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.utils.L;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.adapter.NewGoodsAdapter;
import com.example.xheng.welfaresociety.ui.widget.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    private static final String TAG = NewGoodsFragment.class.getSimpleName();

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder bind;

    INewGoodsModel newGoodsModel;
    int mPageID = 1;
    GridLayoutManager mManager;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;


    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mSwipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));
        mManager = new GridLayoutManager(getContext(), I.COLUM_NUM);
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
        mRecyclerView.setHasFixedSize(true);//自动修正大小
        mList = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newGoodsModel = new NewGoodsModel();
        initData(I.ACTION_DOWNLOAD);
        setListener();
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
                    initData(I.ACTION_PULL_UP);
                }

            }
        });
    }

    private void refreshListener() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh(true);
                mPageID = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData(final int action) {
        newGoodsModel.loadData(getActivity(), mPageID, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e(TAG, "onSuccess() result:" + result);
                isRefresh(false);
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mList.clear();
                    }
                    mList.addAll(list);


                    //如果新加载的一列不足十个 设置false
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "onSuccess() error:" + error);
                isRefresh(false);
            }
        });
    }

    private void isRefresh(boolean isRefresh) {
        mSwipeRefresh.setRefreshing(isRefresh);
        mTvRefresh.setVisibility(isRefresh ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
