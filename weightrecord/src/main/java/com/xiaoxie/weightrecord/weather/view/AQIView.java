package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * 空气质量的view
 * Created by xcb on 2018/1/19.
 */

public class AQIView extends LinearLayout {
    private TextView tv_title;

    public AQIView(Context context) {
        super(context);
        initView(context);
    }

    public AQIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AQIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_aqi, null);
        this.addView(v);
    }
}
