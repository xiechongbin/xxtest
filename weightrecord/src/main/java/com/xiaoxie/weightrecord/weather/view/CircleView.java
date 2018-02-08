package com.xiaoxie.weightrecord.weather.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private Paint cPaint;
    private Paint vPaint;
    private Paint topPaint;
    private int width, height;
    private float startAngle = 135;
    private float angleLength = 270;
    //所要绘制的当前步数的红色圆弧终点到起点的夹角
    private float currentAngleLength = 0;
    // 圆弧的宽度
    private float borderWidth = 38f;
    //动画时长
    private int animationLength = 3000;
    private Context context;
    private String centerTitle = "";
    private String centerValue = "";
    private int centerTitleSize = 50;
    private int centervalueSize = 35;
    private int scaleSize = 40;
    private String topTitle = "";
    private int topTitleSize = 40;
    private static final int WRAP_WIDTH = 200;
    private static final int WRAP_HEIGHT = 200;

    public CircleView(Context context) {
        super(context);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSpaceMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
/*        if (wSpaceMode == MeasureSpec.AT_MOST && hSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(WRAP_WIDTH, WRAP_HEIGHT);
        } else if (wSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(WRAP_WIDTH, hSize);
        } else if (hSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSize, WRAP_HEIGHT);
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //中心点的x坐标
        float centerX;
        float centerY;
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        centerX = width / 2;
        centerY = height / 2;
        if (height > width) {
            centerX = width;
        } else {
            centerX = height;
        }
        //指定圆弧的外轮廓矩形区域
        RectF rectF = new RectF(centerX / 6, centerX / 6, centerX / 6 * 5, centerX / 6 * 5);
        float raidus = centerX / 3;
        float baseValue = (float) Math.sqrt(2);
        float minScaleX = (2 - baseValue) / 2 * raidus + centerX / 6;
        float maxScaleX = (2 + baseValue) / 2 * raidus + centerX / 6;
        float scaleY = (2 + baseValue) / 2 * raidus + (2 - baseValue) / 4 * raidus + centerX / 6 + scaleSize;
        canvas.drawArc(rectF, startAngle, angleLength, false, bPaint);
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, bPaint);
        canvas.drawText("0", minScaleX, scaleY, tPaint);
        canvas.drawText("500", maxScaleX, scaleY, tPaint);
        canvas.drawText(topTitle, rectF.centerX(), centerX / 12, cPaint);
        canvas.drawText(centerTitle, rectF.centerX(), rectF.centerY() - centervalueSize, cPaint);
        canvas.drawText(centerValue, rectF.centerX(), rectF.centerY() + centervalueSize, vPaint);

    }

    private void init(Context context) {
        this.context = context;
        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint.setStrokeJoin(Paint.Join.ROUND);
        bPaint.setStrokeCap(Paint.Cap.ROUND);
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setStrokeWidth(20);
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
        tPaint.setTextSize(scaleSize);
        tPaint.setTextAlign(Paint.Align.CENTER);
        tPaint.setColor(getResources().getColor(R.color.color_9b9c9b, null));

        cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setAntiAlias(true);
        cPaint.setTextSize(centerTitleSize);
        cPaint.setTextAlign(Paint.Align.CENTER);
        cPaint.setColor(Color.WHITE);

        vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        vPaint.setAntiAlias(true);
        vPaint.setTextSize(centervalueSize);
        vPaint.setTextAlign(Paint.Align.CENTER);
        vPaint.setColor(Color.WHITE);

        topPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topPaint.setAntiAlias(true);
        topPaint.setTextSize(topTitleSize);
        topPaint.setTextAlign(Paint.Align.CENTER);
        topPaint.setColor(Color.WHITE);
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

    public void setCenterInfo(String topTitle, String title, String centerValue) {
        this.topTitle = topTitle;
        this.centerTitle = title;
        this.centerValue = centerValue;
        invalidate();
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
