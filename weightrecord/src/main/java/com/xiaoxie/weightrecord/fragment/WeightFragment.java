package com.xiaoxie.weightrecord.fragment;

import android.app.Activity;
import android.content.pm.ProviderInfo;
import android.icu.text.PluralRules;
import android.opengl.GLException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.bean.BodyData;
import com.xiaoxie.weightrecord.bean.Options;
import com.xiaoxie.weightrecord.charts.PlanRealLineChart;
import com.xiaoxie.weightrecord.charts.TargetPlanRealLineChart;
import com.xiaoxie.weightrecord.realm.RealmStorageHelper;
import com.xiaoxie.weightrecord.utils.FragmentUtils;
import com.xiaoxie.weightrecord.utils.LogUtils;
import com.xiaoxie.weightrecord.view.CustomProgressBar;
import com.xiaoxie.weightrecord.view.DashBoardView;
import com.xiaoxie.weightrecord.view.HistoryView;
import com.xiaoxie.weightrecord.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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
    private FrameLayout floatingCircle;
    private AddDataFragment dataFragment;
    private LinearLayout ll_history_items_containers;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_weight_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        dashBoardView = view.findViewById(R.id.weight_dashBoardView);
        progressBar = view.findViewById(R.id.progressbar);
        viewPager = view.findViewById(R.id.viewpagerCharts);
        floatingCircle = view.findViewById(R.id.floatingCircle);
        ll_history_items_containers = view.findViewById(R.id.ll_history_items_containers);
        dashBoardView.setOnClickListener(this);
        floatingCircle.setOnClickListener(this);
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
        initHistoryItems();
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
            case R.id.floatingCircle:
                showAddDataFragment();
                break;
        }
    }

    private void initHistoryItems() {
        RealmResults<BodyData> bodyDatas = getBodyData();
        int size = bodyDatas.size();
        for (int i = 0; i < size; i++) {
            View v = new HistoryView(activity, bodyDatas.get(i));
            ll_history_items_containers.addView(v);
        }
    }

    private void showAddDataFragment() {
        if (dataFragment == null) {
            dataFragment = new AddDataFragment();
        }
        if (dataFragment.isAdded()) {
            FragmentUtils.hideFragment(getActivity(), this);
            FragmentUtils.showFragment(getActivity(), dataFragment);
        } else {
            FragmentUtils.addFragment(getActivity(), R.id.fm_fragment_container_hole, dataFragment, AddDataFragment.class.getSimpleName());
        }
    }

    private RealmResults<BodyData> getBodyData() {
        RealmResults<BodyData> results = RealmStorageHelper.getInstance().getRealm().where(BodyData.class).findAll();
        for (int i = 0; i < results.size(); i++) {
            BodyData data = results.get(i);
            Log.d("results", "date = " + data.getDate() + "\n" + "AverageWeight = " + data.getAverageWeight()
                    + "\nAmweight = " + data.getAmWeight()
                    + "\n pmweight = " + data.getPmWeight()
                    + "\nAcvitvity = " + data.getActivity()
                    + "\nchest = " + data.getChest());
        }
        return results;
    }
}
