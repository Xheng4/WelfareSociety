package com.example.xheng.welfaresociety.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.model.bean.BoutiqueBean;
import com.example.xheng.welfaresociety.model.net.BoutiqueModel;
import com.example.xheng.welfaresociety.model.net.IBoutiqueModle;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.ui.activity.BoutiqueChildActivity;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xheng on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    IBoutiqueModle model;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mList) {
        this.mContext = context;
        this.mList = mList;
        model = new BoutiqueModel();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BoutiqueViewHolder viewHolder = (BoutiqueViewHolder) holder;
        final BoutiqueBean bean = mList.get(position);
        viewHolder.mTvBoutiqueTitle.setText(bean.getTitle());
        viewHolder.mTvBoutiqueName.setText(bean.getName());
        viewHolder.mTvBoutiqueDesc.setText(bean.getDescription());
        ImageLoader.downloadImg(mContext,viewHolder.mIvBoutique,bean.getImageurl());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class)
                        .putExtra("a","a").putExtra("aa","aa"));*/
                MFGT.gotoBoutiqueChild((Activity) mContext,bean.getId(),bean.getName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    class BoutiqueViewHolder extends ViewHolder {
        @BindView(R.id.iv_boutique)
        ImageView mIvBoutique;
        @BindView(R.id.tv_boutique_title)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tv_boutique_name)
        TextView mTvBoutiqueName;
        @BindView(R.id.tv_boutique_desc)
        TextView mTvBoutiqueDesc;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

