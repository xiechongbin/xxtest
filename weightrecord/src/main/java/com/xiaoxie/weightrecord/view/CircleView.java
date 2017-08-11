package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaoxie.weightrecord.R;

/**
 * desc:圆形view
 * Created by xiaoxie on 2017/8/11.
 */
public class CircleView extends LinearLayout {
    private int color;
    private int alpha;
    private int[] colors;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        Paint mPaint = new Paint();
        mPaint.setARGB(alpha, colors[0], colors[1], colors[2]);
        mPaint.setAntiAlias(true);
        float cirX = width / 2;
        float cirY = width / 2;
        float radius = width / 2;
        canvas.drawCircle(cirX, cirY, radius, mPaint);
        super.onDraw(canvas);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet set) {
        TypedArray array = context.obtainStyledAttributes(set,
                R.styleable.CircleRelativeLayoutLayout);
        color = array.getColor(R.styleable.CircleRelativeLayoutLayout_background_color, 0X0000000);
        alpha = array.getInteger(R.styleable.CircleRelativeLayoutLayout_background_alpha, 100);
        setColors();
        array.recycle();
    }

    public void setColor(int color) {
        this.color = color;
        setColors();
        invalidate();
    }

    public void setAlhpa(int alpha) {
        this.alpha = alpha;
        invalidate();
    }

    public void setColors() {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        this.colors = new int[]{red, green, blue};
    }
}
