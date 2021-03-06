package com.blankj.common.activity;


import com.blankj.base.rv.BaseItemAdapter;
import com.blankj.base.rv.RecycleViewDivider;
import com.blankj.common.R;
import com.blankj.common.item.CommonItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/01
 *     desc  :
 * </pre>
 */
public class CommonActivityItemsView {

    public  AppCompatActivity           mBaseActivity;
    private List<CommonItem>            mItems;
    public  BaseItemAdapter<CommonItem> mCommonItemAdapter;
    public  RecyclerView                mCommonItemRv;

    public CommonActivityItemsView(@NonNull AppCompatActivity activity, @NonNull List<CommonItem> items) {
        mBaseActivity = activity;
        mItems = items;
    }

    public int bindLayout() {
        return R.layout.common_item;
    }

    public void initView() {
        mCommonItemAdapter = new BaseItemAdapter<>();
        mCommonItemAdapter.setItems(mItems);
        mCommonItemRv = mBaseActivity.findViewById(R.id.commonItemRv);
        mCommonItemRv.setAdapter(mCommonItemAdapter);
        mCommonItemRv.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mCommonItemRv.addItemDecoration(new RecycleViewDivider(mBaseActivity, RecycleViewDivider.VERTICAL, R.drawable.common_item_divider));
    }

    public void updateItems(List<CommonItem> data) {
        mCommonItemAdapter.setItems(data);
        mCommonItemAdapter.notifyDataSetChanged();
    }

    public void updateItem(int position) {
        mCommonItemAdapter.notifyItemChanged(position);
    }
}
