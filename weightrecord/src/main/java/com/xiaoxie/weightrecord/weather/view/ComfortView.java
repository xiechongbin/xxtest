package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * 舒适度
 * Created by xcb on 2018/1/19.
 */

public class ComfortView extends LinearLayout {
    private CircleView circleView;

    public ComfortView(Context context) {
        super(context);
        initView(context);
    }

    public ComfortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ComfortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_comfort, null);
        this.addView(v);
        circleView = v.findViewById(R.id.air_wet);
        circleView.setCenterInfo("空气湿度", "", "36%");
    }
}
