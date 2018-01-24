package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 空气质量的view
 * Created by xcb on 2018/1/19.
 */

public class AQIView extends CircleView {
    public AQIView(Context context) {
        super(context);
    }

    public AQIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AQIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
