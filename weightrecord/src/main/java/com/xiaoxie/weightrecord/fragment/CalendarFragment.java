package com.xiaoxie.weightrecord.fragment;

import android.os.Bundle;
import android.view.View;

import com.xiaoxie.weightrecord.R;

/**
 * desc:照片fragment
 * Created by xiaoxie on 2017/8/23.
 */
public class CalendarFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_calendar_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
