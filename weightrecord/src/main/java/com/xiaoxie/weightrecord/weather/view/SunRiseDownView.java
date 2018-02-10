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
 * 日出日落
 * Created by xcb on 2018/1/19.
 */

public class SunRiseDownView extends LinearLayout {
    private SunRiseView sunRiseView;

    public SunRiseDownView(Context context) {
        super(context);
        initView(context);
    }

    public SunRiseDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SunRiseDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_sunrise_down, null);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(v);
        sunRiseView = v.findViewById(R.id.sunRise);
        sunRiseView.startAnimations();
    }
}
