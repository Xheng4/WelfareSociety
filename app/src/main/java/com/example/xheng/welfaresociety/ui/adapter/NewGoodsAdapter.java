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
import com.example.xheng.welfaresociety.model.bean.NewGoodsBean;
import com.example.xheng.welfaresociety.model.net.INewGoodsModel;
import com.example.xheng.welfaresociety.model.net.NewGoodsModel;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xheng on 2017/3/15.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> mList;
    INewGoodsModel model;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> mList) {
        this.context = context;
        this.mList = mList;
        model = new NewGoodsModel();
        isMore = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        if (viewType == I.TYPE_FOOTER) {
            viewHolder = new FooterViewHolder(View.inflate(context, R.layout.item_footer, null));
        } else {
            viewHolder = new GoodsViewHolder(View.inflate(context, R.layout.item_goods, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.mTvFooter.setText(getFooterString());
            return;
        }
        GoodsViewHolder viewHolder = (GoodsViewHolder) holder;
        NewGoodsBean bean = mList.get(position);
        viewHolder.mTvName.setText(bean.getGoodsName());
        viewHolder.mTvPrice.setText(bean.getShopPrice());
        ImageLoader.downloadImg(context, viewHolder.mIvGoods, bean.getGoodsThumb());
    }

    private int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("position", "+++" + position);
        if (getItemCount() - 1 == position) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    class GoodsViewHolder extends ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView mIvGoods;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_price)
        TextView mTvPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class FooterViewHolder extends ViewHolder {
        @BindView(R.id.tv_footer)
        TextView mTvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

/*
class GoodsViewHolder extends RecyclerView.ViewHolder {
    TextView tvName, tvPrice;
    ImageView imageView;

    public GoodsViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        imageView = (ImageView) itemView.findViewById(R.id.iv_goods);
    }
}
*/
