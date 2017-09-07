package com.xiaoxie.weightrecord.charts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xiaoxie.weightrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:目标 实际 计划图表
 * Created by xiaoxie on 2017/9/5.
 */
public class TargetPlanRealLineChart extends LinearLayout implements OnChartGestureListener, OnChartValueSelectedListener {
    private Context context;
    private View targetCharts;
    private LineChart mChart;

    public TargetPlanRealLineChart(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TargetPlanRealLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TargetPlanRealLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化控件加载布局
     */
    private void initView() {
        targetCharts = LayoutInflater.from(context).inflate(R.layout.layout_target_plan_real_linecharts, this);
        mChart = targetCharts.findViewById(R.id.tpr_chart);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mChart.setOnChartValueSelectedListener(this);
        mChart.setOnChartGestureListener(this);

        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
        mv.setChartView(mChart);
        mChart.setMarker(mv);

        LimitLine ll2 = new LimitLine(50f, "目标");
        ll2.setLineWidth(2f);
        ll2.setLineColor(com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_theme_green));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();//左边y轴
        leftAxis.removeAllLimitLines();
        //leftAxis.addLimitLine(ll1);//添加限制线
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(200f);//最大值
        leftAxis.setAxisMinimum(0);//最小值
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setTextColor(com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_848484));
        leftAxis.setDrawGridLines(false);//不绘制网格
        leftAxis.setDrawLimitLinesBehindData(true);

        XAxis rightAxis = mChart.getXAxis();
        rightAxis.removeAllLimitLines();
        rightAxis.setTextColor(com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_848484));
        rightAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置显示x轴显示的位置
        rightAxis.setDrawGridLines(false);//不绘制网格
        rightAxis.setValueFormatter(new DayAxisValueFormatter(mChart));
        rightAxis.setAxisMaximum(7);
        rightAxis.setAxisMinimum(1);
        rightAxis.setGranularity(1f);//设置刻度与刻度之间的距离
        mChart.getAxisRight().setEnabled(false);//不绘制右边的y轴

        mChart.animateX(2500);
        Legend l = mChart.getLegend();//获取到系统默认图例来自定义图例
        List<LegendEntry> legendEntries = new ArrayList<>();
        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.form = Legend.LegendForm.SQUARE;
        legendEntry1.formColor = com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_theme_blue);
        legendEntry1.label = "实际";
        LegendEntry legendEntry2 = new LegendEntry();
        legendEntry2.form = Legend.LegendForm.SQUARE;
        legendEntry2.formColor = com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_848484);
        legendEntry2.label = "目标";
        LegendEntry legendEntry3 = new LegendEntry();
        legendEntry3.form = Legend.LegendForm.SQUARE;
        legendEntry3.formColor = Color.RED;
        legendEntry3.label = "计划";
        legendEntries.add(legendEntry1);
        legendEntries.add(legendEntry2);
        legendEntries.add(legendEntry3);
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setCustom(legendEntries);

        setData(10, 100);
    }

    private ArrayList<Entry> getData(int count, float range) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.ic_dunpai)));
        }
        return values;
    }

    private void setData(int count, float range) {

        LineDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(getData(count, range));
            set2.setValues(getData(count, range));
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(getData(count, range), "计划"); // create a dataset and give it a type
            set1.setDrawIcons(false);//设置连线的点的图片
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.RED);
            set1.setCircleColor(Color.RED);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            set2 = new LineDataSet(getData(count, range), "实际"); // create a dataset and give it a type
            set2.setDrawIcons(false);//设置连线的点的图片
            set2.enableDashedLine(10f, 5f, 0f);
            set2.enableDashedHighlightLine(10f, 5f, 0f);
            set2.setColor(com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_theme_blue));
            set2.setCircleColor(com.xiaoxie.weightrecord.utils.Utils.getColor(context, R.color.color_theme_blue));
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            set1.setFillColor(Color.BLACK);
            set2.setFillColor(Color.BLACK);


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2); // add the datasets


            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }

    /**
     * **************************图表手势监听回调***************************
     */
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.d("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.d("Entry selected", e.toString());
        Log.d("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.d("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.d("Nothing selected", "Nothing selected.");
    }
}
