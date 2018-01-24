package com.xiaoxie.weightrecord.weather.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoxie.weightrecord.R;

/**
 * 圆形进度条
 * Created by xcb on 2018/1/19.
 */

public class CircleView extends View {
    private Paint bPaint;
    private Paint pPaint;
    private Paint tPaint;
    private int width, height;
    private float startAngle = 135;
    private float angleLength = 270;
    //所要绘制的当前步数的红色圆弧终点到起点的夹角
    private float currentAngleLength = 0;
    // 圆弧的宽度
    private float borderWidth = 38f;
    //动画时长
    private int animationLength = 3000;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //中心点的x坐标
        float centerX = (getWidth()) / 2;
        //指定圆弧的外轮廓矩形区域
        RectF rectF = new RectF(0 + borderWidth, borderWidth, 2 * centerX - borderWidth, 2 * centerX - borderWidth);
        canvas.drawArc(rectF, startAngle, angleLength, false, bPaint);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, bPaint);
        canvas.drawText("0", centerX, centerX, tPaint);
        canvas.drawText("500", centerX, centerX, tPaint);

    }

    private void init() {
        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint.setStrokeJoin(Paint.Join.ROUND);
        bPaint.setStrokeCap(Paint.Cap.ROUND);
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setStrokeWidth(15);
        bPaint.setAntiAlias(true);
        bPaint.setColor(getResources().getColor(R.color.color_9b9c9b, null));

        pPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pPaint.setStrokeJoin(Paint.Join.ROUND);
        pPaint.setStrokeCap(Paint.Cap.ROUND);
        pPaint.setStyle(Paint.Style.STROKE);
        pPaint.setStrokeWidth(15);
        pPaint.setAntiAlias(true);
        pPaint.setColor(getResources().getColor(R.color.color_27ae60, null));

        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setAntiAlias(true);
        tPaint.setColor(getResources().getColor(R.color.color_9b9c9b, null));
    }

    /**
     * 设置当前进度值
     */
    public void setCurrentValue(float value, float total) {
        if (value >= total) {
            value = total;
        }
        float scale = value / total;
        float currentAngleLength = scale * angleLength;
        //开始执行动画
        setAnimation(0, currentAngleLength, animationLength);
    }

    private void setAnimation(float last, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }
}
