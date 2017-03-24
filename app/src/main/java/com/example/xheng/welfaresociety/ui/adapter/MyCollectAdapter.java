package com.example.xheng.welfaresociety.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.application.I;
import com.example.xheng.welfaresociety.model.bean.CollectBean;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.ui.activity.MyCollectActivity;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xheng on 2017/3/23.
 */

public class MyCollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CollectBean> mList;
    boolean isMore, isFooter;
    int mGoodsID = 0;
    MyCollectActivity mContext;

    public MyCollectAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = (MyCollectActivity) context;
        mList = list;
    }

    public int getGoodsID() {
        return mGoodsID;
    }

    private int getFooterString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public void setFooter(boolean footer) {
        isFooter = footer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == I.TYPE_FOOTER) {
            viewHolder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            viewHolder = new CollectViewHolder(View.inflate(mContext, R.layout.item_collect, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.mTvFooter.setText(getFooterString());
            viewHolder.mTvFooter.setVisibility(isFooter ? View.GONE : View.VISIBLE);
            Log.e("footer", "isFooter:" + isFooter);
            return;
        }
        CollectViewHolder viewHolder = (CollectViewHolder) holder;
        final CollectBean bean = mList.get(position);
        viewHolder.mTvName.setText(bean.getGoodsName());

        ImageLoader.downloadImg(mContext, viewHolder.mIvGoods, bean.getGoodsThumb());
        viewHolder.mIvCollectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.refreshCollect(bean.getGoodsId());
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转还没做好
                MFGT.gotoDesc(mContext, bean.getGoodsId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() - 1 == position) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    class FooterViewHolder extends ViewHolder {
        @BindView(R.id.tv_footer)
        TextView mTvFooter;


        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class CollectViewHolder extends ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView mIvGoods;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_collect_delete)
        ImageView mIvCollectDelete;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

