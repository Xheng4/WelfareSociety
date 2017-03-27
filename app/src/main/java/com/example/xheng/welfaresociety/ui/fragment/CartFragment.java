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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.FuLiApplication;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.CartBean;
import com.example.xheng.welfaresociety.model.bean.GoodsDetailsBean;
import com.example.xheng.welfaresociety.model.bean.MessageBean;
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
        setOnClick();

    }

    private void setOnClick() {
        mAdapter.setMcheckedListener(mCheckedChangeListener);
        mAdapter.setUpDateClickListener(mOnClickListener);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            int count = 0;
            if (v.getTag(R.id.ACTION_CART_ADD) != null) {
                count = (int) v.getTag(R.id.ACTION_CART_ADD);
            }
            if (v.getTag(R.id.ACTION_CART_del) != null) {
                count = (int) v.getTag(R.id.ACTION_CART_del);
            }
            upDateGoodsCount(position, count);
        }
    };

    private void upDateGoodsCount(final int position, final int i) {
        CartBean bean = mList.get(position);
        int action = bean.getCount() + i > 0 ? I.ACTION_CART_UPDATA : I.ACTION_CART_DEL;

        mModel.cartAction(getContext(), action, bean.getCount() + i,
                FuLiApplication.getUser().getMuserName(),
                String.valueOf(bean.getId()), String.valueOf(bean.getGoodsId()),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null) {
                            upDateCartCount(position, i);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        mSRL.setRefreshing(false);
                        Log.e("cart", "onError:" + error);
                    }
                });
    }


    private void upDateCartCount(int position, int i) {
        mList.get(position).setCount(mList.get(position).getCount() + i);
        if (mList.get(position).getCount() + i == 0) {
            mList.remove(position);
        }
        mAdapter.notifyDataSetChanged();

    }

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            mList.get(position).setChecked(isChecked);
            setPriceText();
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CartModel();
        mUser = FuLiApplication.getUser();
        if (mUser != null) {
            initData();
        }
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
                    mAdapter.initData(mList);

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

    private void setPriceText() {
        int sumPrice = 0;
        int rankPrice = 0;
        for (CartBean bean : mList) {
            if (bean.isChecked()) {
                GoodsDetailsBean goods = bean.getGoods();
                if (goods != null) {
                    sumPrice += getPrice(goods.getCurrencyPrice()) * bean.getCount();
                    rankPrice += getPrice(goods.getRankPrice()) * bean.getCount();
                    Log.e("cart", "sumPrice:" + sumPrice);
                }
            }
        }
        mTvCartTotal.setText(sumPrice + "");
        mTvCartDiscount.setText(sumPrice - rankPrice + "");
    }

    private int getPrice(String string) {
        String str = string.substring(string.indexOf("￥") + 1);
        return Integer.valueOf(str);
    }
}
