package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;

/**
 * Created by xcb on 2018/1/15.
 */

public class TemperatureView extends LinearLayout {
    private View tempertureView;
    public TemperatureView(Context context) {
        super(context);
        initView(context);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        tempertureView = LayoutInflater.from(context).inflate(R.layout.layout_weather_temperture,null);
        tempertureView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(tempertureView);
    }

}
