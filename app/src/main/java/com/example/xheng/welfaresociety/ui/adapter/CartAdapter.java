package com.example.xheng.welfaresociety.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.model.bean.CartBean;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.model.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xheng on 2017/3/24.
 */

public class CartAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        L.e("CartAdapter");
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CartBean getItem(int position) {

        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        L.e("getView()");
        ViewHolder holder;
        if (convertView == null) {
            View inflate = View.inflate(mContext, R.layout.item_cart, null);
            holder = new ViewHolder(inflate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvCartPrice.setText(getItem(position).getGoods().getShopPrice());
        holder.mTvCartCount.setText(getItem(position).getCount());
        holder.mTvCartGoodName.setText(getItem(position).getGoods().getGoodsName());
        ImageLoader.downloadImg(mContext, holder.mIvCartThumb, getItem(position).getGoods().getGoodsThumb());
        holder.mCbCartSelected.setChecked(getItem(position).isChecked());
        Log.e("cart", "bean" + getItem(position).toString());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
