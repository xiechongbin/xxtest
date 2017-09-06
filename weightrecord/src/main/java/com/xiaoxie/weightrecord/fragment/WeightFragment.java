package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.view.CustomProgressBar;
import com.xiaoxie.weightrecord.view.DashBoardView;

/**
 * desc:
 * Created by xiaoxie on 2017/8/23.
 */
public class WeightFragment extends BaseFragment implements View.OnClickListener {
    private DashBoardView dashBoardView;
    private CustomProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_weight_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        dashBoardView = view.findViewById(R.id.weight_dashBoardView);
        progressBar = view.findViewById(R.id.progressbar);
        dashBoardView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weight_dashBoardView:
                Log.d("dashboard", "click");
                progressBar.setProgress(80, true);
                break;
        }
    }
}
