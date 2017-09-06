package com.xiaoxie.weightrecord.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.Utils;

/**
 * desc:
 * Created by xiaoxie on 2017/9/5.
 */
public class CustomProgressBar extends View {

    private int progress = 0;
    private int progress1 = 0;
    private int screenWidth = 0;
    private Paint backgroundPaint;//进度条背景画笔
    private Paint progressPaint;//进度画笔
    private Paint progressPaint1;//进度画笔1
    private Context context;

    public CustomProgressBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int modeW = MeasureSpec.getMode(widthMeasureSpec); //获得宽度MODE
        if (modeW == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);//获得宽度的值
        }
        if (modeW == MeasureSpec.EXACTLY) {
            width = widthMeasureSpec;
        }
        if (modeW == MeasureSpec.UNSPECIFIED) {
            width = 600;
        }

        int modeH = MeasureSpec.getMode(height); //获得高度MODE

        if (modeH == MeasureSpec.AT_MOST) { //获得高度的值
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (modeH == MeasureSpec.EXACTLY) {
            height = heightMeasureSpec;
        }
        if (modeH == MeasureSpec.UNSPECIFIED) { //ScrollView和HorizontalScrollView
            height = 50;
        }
        setMeasuredDimension(width, height);  //设置宽度和高度
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Utils.getColor(context, R.color.color_eaeaea));
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Utils.getColor(context, R.color.color_ecfbf6));
        progressPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint1.setColor(Utils.getColor(context, R.color.color_6bc792));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenWidth = getWidth();

        RectF rect = new RectF(0, 0, screenWidth, 50);
        canvas.drawRoundRect(rect, 10, 10, backgroundPaint);

        RectF rect_progress = new RectF(0, 0, progress, 50);
        canvas.drawRoundRect(rect_progress, 10, 10, progressPaint);

        RectF rect_progress1 = new RectF(0, 0, progress1, 50);
        canvas.drawRoundRect(rect_progress1, 10, 10, progressPaint1);
    }

    public void setProgress(int progress, boolean animation) {
        this.progress = screenWidth * progress / 100;
        if (animation) {
            startAnimation(0, this.progress, 5000);
        } else {
            invalidate();
        }

    }

    private void startAnimation(int start, final int end, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(duration);
        valueAnimator.setTarget(progress);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progress = (int) valueAnimator.getAnimatedValue();
                invalidate();
                if (progress == end) {
                    startAnimation1(0, progress / 2, 5000);
                }
            }
        });
        valueAnimator.start();

    }

    private void startAnimation1(int start, int end, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(duration);
        valueAnimator.setTarget(progress1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progress1 = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

    }
}
