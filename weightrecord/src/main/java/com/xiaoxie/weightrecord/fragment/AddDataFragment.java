package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.adapter.AddDataRecycleViewAdapter;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.Utils;
import com.xiaoxie.weightrecord.view.RecycleViewDivider;

/**
 * desc:新增数据的fragment
 * Created by xiaoxie on 2017/9/7.
 */
public class AddDataFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private AddDataRecycleViewAdapter addDataRecycleViewAdapter;
    private Activity activity;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_add_data_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.add_data_recyclerView);

    }

    @Override
    protected void initData() {
        this.activity = getActivity();
        Options options = RealmStorageHelper.getInstance().getOptions().get(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.HORIZONTAL, 2, Utils.getColor(activity, R.color.color_f2f2f2)));
        recyclerView.setLayoutManager(layoutManager);
        addDataRecycleViewAdapter = new AddDataRecycleViewAdapter(activity, options);
        recyclerView.setAdapter(addDataRecycleViewAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
