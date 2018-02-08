package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xcb on 2018/1/25.
 */

public class SunRiseView extends View {
    private int width;
    private int height;

    public SunRiseView(Context context) {
        super(context);
    }

    public SunRiseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SunRiseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量View的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//取出宽度的测量模式

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//取出高度的测量模式

        if (widthMode == MeasureSpec.EXACTLY) {//表示父控件已经确切的指定了子View的宽度

        } else if (widthMode == MeasureSpec.AT_MOST) {//表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。

        } else if (widthMode == MeasureSpec.UNSPECIFIED) {//默认值，父控件没有给子view任何限制，子View可以设置为任意大小。

        }
        if (heightMode == MeasureSpec.EXACTLY) {//表示父控件已经确切的指定了子View高度

        } else if (heightMode == MeasureSpec.AT_MOST) {//表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。

        } else if (heightMode == MeasureSpec.UNSPECIFIED) {//默认值，父控件没有给子view任何限制，子View可以设置为任意大小。

        }
        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
