package com.example.xheng.welfaresociety.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.model.bean.CategoryChildBean;
import com.example.xheng.welfaresociety.model.bean.CategoryGroupBean;
import com.example.xheng.welfaresociety.model.utils.ImageLoader;
import com.example.xheng.welfaresociety.ui.widget.MFGT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xheng on 2017/3/17.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context context,ArrayList<CategoryGroupBean> list
            ,ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.context = context;
        mGroupList = list;
        mChildList = childList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mGroupList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return mChildList.get(groupPosition).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_parent, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        holder.mIvOpen.setImageResource(isExpanded?R.mipmap.expand_off:R.mipmap.expand_on);
        CategoryGroupBean bean = getGroup(groupPosition);
        holder.mTvCategoryParent.setText(bean.getName());
        ImageLoader.downloadImg(context,holder.mIvCategoryParent,bean.getImageUrl());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CategoryChildBean bean = getChild(groupPosition, childPosition);
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        Log.e("getChildId()", "groupPosition:" + groupPosition + ",childPosition" + childPosition);
        holder.mTvCategoryChild.setText(bean.getName());
        ImageLoader.downloadImg(context,holder.mIvCategoryChild,
                bean.getImageUrl());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoCateChild(context, bean.getId(), bean.getName());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ParentViewHolder {
        @BindView(R.id.iv_category_parent)
        ImageView mIvCategoryParent;
        @BindView(R.id.tv_category_parent)
        TextView mTvCategoryParent;
        @BindView(R.id.iv_open)
        ImageView mIvOpen;

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_category_child)
        ImageView mIvCategoryChild;
        @BindView(R.id.tv_category_child)
        TextView mTvCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
