package com.example.xheng.welfaresociety.ui.adapter;

import android.app.Activity;
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
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    int sortBy = I.SORT_BY_ADDTIME_DESC;

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        sortBy();
    }

    private int getPrice(String pri) {
        return Integer.valueOf(pri.substring(pri.indexOf("￥") + 0));
    }

    private void sortBy() {
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean a, NewGoodsBean z) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (a.getAddTime() - z.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (z.getAddTime() - a.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(a.getCurrencyPrice()) - getPrice(z.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(z.getCurrencyPrice()) - getPrice(a.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }

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
        final NewGoodsBean bean = mList.get(position);
        viewHolder.mTvName.setText(bean.getGoodsName());
        viewHolder.mTvPrice.setText(bean.getShopPrice());
        ImageLoader.downloadImg(context, viewHolder.mIvGoods, bean.getGoodsThumb());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoDesc(context,bean.getGoodsId());
            }
        });
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
//        Log.e("position", "+++" + position);
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
