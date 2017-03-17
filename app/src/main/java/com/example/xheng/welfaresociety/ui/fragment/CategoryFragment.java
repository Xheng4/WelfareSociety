package com.example.xheng.welfaresociety.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.xheng.welfaresociety.R;
import com.example.xheng.welfaresociety.model.bean.CategoryChildBean;
import com.example.xheng.welfaresociety.model.bean.CategoryGroupBean;
import com.example.xheng.welfaresociety.model.net.CategoryModle;
import com.example.xheng.welfaresociety.model.net.ICategoryModle;
import com.example.xheng.welfaresociety.model.net.OnCompleteListener;
import com.example.xheng.welfaresociety.model.utils.ResultUtils;
import com.example.xheng.welfaresociety.ui.adapter.CategoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    ICategoryModle mModle;
    CategoryAdapter mAdapter;
    @BindView(R.id.expandable_list_view)
    ExpandableListView mExpandableListView;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModle = new CategoryModle();
        mAdapter = new CategoryAdapter(getContext(),mGroupList,mChildList);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setAdapter(mAdapter);

        loadGroupData();
    }

    private void loadGroupData() {
        mModle.loadData(getActivity(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length > 0) {
                    Log.e("Group_onSuccess()", "result" + result.length);
                    ArrayList<CategoryGroupBean> list = ResultUtils.array2List(result);
                    mGroupList.clear();
                    mGroupList.addAll(list);
                    for (int i= 0;i<list.size();i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        loadChildData(list.get(i).getId(),i);
                    }
                }


            }

            @Override
            public void onError(String error) {
                Log.e("Group_onError()", "error" + error);
            }
        });
    }

    private void loadChildData(int id,final int i) {
        mModle.loadData(getContext(), id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                if (result != null) {
                    Log.e("Group_onSuccess()", "result" + result.length);
                    ArrayList<CategoryChildBean> been = ResultUtils.array2List(result);
                    mChildList.set(i, been);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("Group_onError()", "error" + error);
            }
        });
    }
}
