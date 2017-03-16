package com.example.xheng.welfaresociety.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
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

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> mList) {
        this.context = context;
        this.mList = mList;
        model = new NewGoodsModel();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new GoodsViewHolder(View.inflate(context, R.layout.item_goods, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsViewHolder viewHolder = (GoodsViewHolder) holder;
        NewGoodsBean bean = mList.get(position);
        viewHolder.mTvName.setText(bean.getGoodsName());
        viewHolder.mTvPrice.setText(bean.getShopPrice());
//        ImageLoader.downloadImg(context, viewHolder.mIvGoods, bean.getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class GoodsViewHolder extends ViewHolder{
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
