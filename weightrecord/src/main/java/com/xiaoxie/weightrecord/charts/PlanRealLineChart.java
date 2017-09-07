package com.xiaoxie.weightrecord.charts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;

/**
 * desc:实际和计划展示图表
 * Created by xiaoxie on 2017/9/5.
 */
public class PlanRealLineChart extends LinearLayout {
    private Context context;
    private View targetCharts;

    public PlanRealLineChart(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public PlanRealLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public PlanRealLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化控件加载布局
     */
    private void initView() {
        targetCharts = LayoutInflater.from(context).inflate(R.layout.layout_plan_real_charts, this);
    }
}
