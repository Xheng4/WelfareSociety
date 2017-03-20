package com.example.xheng.welfaresociety.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.model.bean.CategoryChildBean;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xheng on 2017/3/20.
 */

public class CategoryPopWindow {
    Context context;
    ArrayList<CategoryChildBean> mList;
    View mView;
    ListView mListView;
    MyPopAdapter mAdapter;

    public CategoryPopWindow(Context context, ArrayList<CategoryChildBean> list) {
        this.context = context;
        mList = list;
        mView = LayoutInflater.from(context).inflate(R.layout.category_popupwindow_layout, null);
        mAdapter = new MyPopAdapter();
        mListView = (ListView) mView.findViewById(R.id.pop_list_view);

    }

    public void initPopWindow() {
        PopupWindow window = new PopupWindow();
        window.setContentView(mView);
        mListView.setAdapter(mAdapter);

    }

    class MyPopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CategoryChildBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CategoryChildBean bean = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_category_child, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTvCategoryChild.setText(bean.getName());
            ImageLoader.downloadImg(context, holder.mIvCategoryChild, bean.getImageUrl());
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_category_child)
            ImageView mIvCategoryChild;
            @BindView(R.id.tv_category_child)
            TextView mTvCategoryChild;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
