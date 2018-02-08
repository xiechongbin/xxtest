package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoxie.weightrecord.R;

/**
 * 风力
 * Created by xcb on 2018/1/19.
 */

public class WindView extends LinearLayout {
    private TextView tv_title;
    private ImageView iv_wind_big, iv_wind_small;
    private int duraion = 3000;
    private RotateAnimation bRotateAnimation;
    private RotateAnimation sRotateAnimation;

    public WindView(Context context) {
        super(context);
        initView(context);
    }

    public WindView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WindView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_wind, null);
        v.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.addView(v);
        iv_wind_big = v.findViewById(R.id.iv_windmill_big);
        iv_wind_small = v.findViewById(R.id.iv_windmill_small);
        startAnimation(context);
    }

    /**
     * 开始旋转动画
     */
    private void startAnimation(Context context) {
        if (bRotateAnimation == null) {
            bRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        bRotateAnimation.reset();
        bRotateAnimation.setRepeatMode(Animation.RESTART);
        bRotateAnimation.setRepeatCount(Animation.INFINITE);
        bRotateAnimation.setInterpolator(context, android.R.anim.linear_interpolator);
        bRotateAnimation.setDuration(duraion);
        iv_wind_big.startAnimation(bRotateAnimation);

        if (sRotateAnimation == null) {
            sRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        sRotateAnimation.reset();
        sRotateAnimation.setRepeatMode(Animation.RESTART);
        sRotateAnimation.setRepeatCount(Animation.INFINITE);
        sRotateAnimation.setInterpolator(context, android.R.anim.linear_interpolator);
        sRotateAnimation.setDuration(duraion);
        iv_wind_small.startAnimation(sRotateAnimation);
    }

    public void setWindSpeed(Context context, int speed) {
        this.duraion = speed;
        startAnimation(context);
    }
}
