package com.example.xheng.welfaresociety.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.model.bean.CartBean;
import com.example.xheng.welfaresociety.model.bean.User;
import com.example.xheng.welfaresociety.model.net.CartModel;
import com.example.xheng.welfaresociety.model.net.ICartModel;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.adapter.CartAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    ICartModel mModel;
    ArrayList<CartBean> mList;
    CartAdapter mAdapter;
    User mUser;

    @BindView(R.id.tv_cart_total)
    TextView mTvCartTotal;
    @BindView(R.id.tv_cart_discount)
    TextView mTvCartDiscount;
    @BindView(R.id.btn_cart_pay)
    Button mBtnCartPay;
    SwipeRefreshLayout mSRL;
    ListView mListView;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {
        mSRL = (SwipeRefreshLayout) view.findViewById(R.id.cart_refresh_layout);
        mListView = (ListView) view.findViewById(R.id.list_view_cart);
        mSRL.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));

        mList = new ArrayList<>();
        mAdapter = new CartAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CartModel();
        initData();
        setRefresh();
    }

    private void setRefresh() {
        mSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSRL.setRefreshing(true);
                mList.clear();
                initData();
            }
        });
    }

    private void initData() {
        mUser = FuLiApplication.getUser();
        mModel.loadCart(getContext(), mUser.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                mSRL.setRefreshing(false);
                if (result != null) {
                    Log.e("cart", "onSuccess(result):" + result.length);

                    mList = ResultUtils.array2List(result);

                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                Log.e("cart", "onError" + error);
            }
        });
    }

    @OnClick(R.id.btn_cart_pay)
    public void onClick() {
    }
}
