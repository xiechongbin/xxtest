package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.content.pm.ProviderInfo;
import android.opengl.GLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.charts.PlanRealLineChart;
import com.xiaoxie.weightrecord.charts.TargetPlanRealLineChart;
import com.xiaoxie.weightrecord.view.CustomProgressBar;
import com.xiaoxie.weightrecord.view.DashBoardView;
import com.xiaoxie.weightrecord.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by xiaoxie on 2017/8/23.
 */
public class WeightFragment extends BaseFragment implements View.OnClickListener {
    private DashBoardView dashBoardView;
    private CustomProgressBar progressBar;
    private MyViewPager viewPager;
    private List<View> views = new ArrayList<>();
    private TargetPlanRealLineChart targetPlanRealLineChart;
    private PlanRealLineChart planRealLineChart;
    private Activity activity;
    private PagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_weight_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        dashBoardView = view.findViewById(R.id.weight_dashBoardView);
        progressBar = view.findViewById(R.id.progressbar);
        viewPager = view.findViewById(R.id.viewpagerCharts);
        dashBoardView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        this.activity = getActivity();
        targetPlanRealLineChart = new TargetPlanRealLineChart(activity);
        planRealLineChart = new PlanRealLineChart(activity);
        views.add(targetPlanRealLineChart);
        views.add(planRealLineChart);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        viewPager.setAdapter(adapter);
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
