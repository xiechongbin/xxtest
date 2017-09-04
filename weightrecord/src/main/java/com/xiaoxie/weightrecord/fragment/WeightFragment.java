package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.view.DashBoardView;

/**
 * desc:
 * Created by xiaoxie on 2017/8/23.
 */
public class WeightFragment extends BaseFragment {
    private DashBoardView dashBoardView;
    @Override
    protected int getLayoutId() {
        return R.layout.frag_weight_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        dashBoardView = view.findViewById(R.id.weight_dashBoardView);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
