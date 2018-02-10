package com.xiaoxie.weightrecord.weather.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoxie.weightrecord.R;

/**
 * Created by xcb on 2018/1/25.
 */

public class SunRiseView extends View {
    private static final int DEFAULT_ARC_WIDTH = 1;
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_DASH_EFFECT_LENGTH = 4;
    private static final int DEFAULT_PICTURE_SIZE = 90;
    private static final int DEFAULT_TEXT_SIZE = 15;
    private Context context;
    private int width;
    private int height;

    private float startAngle = 195f;//起始角度
    private float sweepAngle = 150f;//扫过的角度

    //圆环距离四边的距离
    private float paddingLeft;
    private float paddingTop;
    private float paddingBottom;
    private float paddingRight;

    private float circleWidth;
    private float circleRadius;//圆弧的半径

    private float lineY;//水平线的y坐标
    //圆弧虚线和实线的长度
    private int dashEffectLength;

    private RectF rectF;

    private Paint circlePaint;
    private Paint circleBgPaint;
    private Paint linePaint;
    private Paint textPaint;

    private Bitmap sun;
    private Rect rectLocation;
    private Paint bitmapPaint;
    private int pictureSize;
    private float rectLocationX, rectLocationY;
    private ValueAnimator animation;

    private int riseUpTimeHour, riseUpTimeMin;
    private int riseDownTimeHour, riseDownTimeMin;
    private int textSize;
    private int textOffset = 40;
    private float riseUpTextLocationX;
    private float riseUpTextLocationY;
    private float riseDownTextLocationX;
    private float riseDownTextLocationY;
    private float currentSweepAngle;//实际扫过的角度
    private String riseUpTime, riseDownTime;


    public SunRiseView(Context context) {
        super(context);
        init(context);

    }

    public SunRiseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    /**
     * 测量View的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measures(widthMeasureSpec, dpToPx(context, DEFAULT_WIDTH)),
                measures(widthMeasureSpec, dpToPx(context, DEFAULT_HEIGHT)));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        this.width = w;
        this.height = h;
        rectF.left = paddingLeft;
        rectF.top = paddingTop;
        rectF.right = w - paddingRight;
        rectF.bottom = h - paddingBottom;

        circleRadius = Math.min((rectF.right - rectF.left) / 2, (rectF.bottom - rectF.top) / 2);//取小的那个
        lineY = (float) (rectF.centerY() - (circleRadius * (Math.sin((Math.PI * 15) / 180))));
        riseUpTextLocationX = (float) (rectF.centerX() - (Math.cos(Math.PI * 15 / 180)) * circleRadius);
        riseDownTextLocationX = (float) (rectF.centerX() + (Math.cos(Math.PI * 15 / 180)) * circleRadius);
        riseUpTextLocationY = riseDownTextLocationY = lineY + textOffset;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawArc(rectF, startAngle, sweepAngle, false, circlePaint);

        canvas.drawArc(rectF, startAngle, currentSweepAngle, false, circleBgPaint);
        canvas.drawLine(paddingLeft / 2, lineY, width - paddingLeft / 2, lineY, linePaint);
        if (!TextUtils.isEmpty(riseUpTime)) {
            canvas.drawText(riseUpTime, riseUpTextLocationX, riseUpTextLocationY, textPaint);
        }
        if (!TextUtils.isEmpty(riseDownTime)) {
            canvas.drawText(riseDownTime, riseDownTextLocationX, riseDownTextLocationY, textPaint);
        }
        canvas.restore();
        canvas.drawBitmap(sun, null, rectLocation, bitmapPaint);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        this.context = context;
        rectF = new RectF();

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(circleWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setPathEffect(new DashPathEffect(new float[]{dashEffectLength, dashEffectLength}, 0));

        circleBgPaint = new Paint();
        circleBgPaint.setAntiAlias(true);
        circleBgPaint.setColor(getResources().getColor(R.color.color_ffdc1a, null));
        circleBgPaint.setStrokeWidth(circleWidth);
        circleBgPaint.setStyle(Paint.Style.STROKE);
        circleBgPaint.setPathEffect(new DashPathEffect(new float[]{dashEffectLength, dashEffectLength}, 0));


        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(circleWidth);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setDither(true);//防抖动
        bitmapPaint.setFilterBitmap(true);

        sun = ((BitmapDrawable) getResources().getDrawable(R.drawable.weather_sun, null)).getBitmap();
        rectLocation = new Rect();


    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.sunRise);
        paddingLeft = typedArray.getDimension(R.styleable.sunRise_paddingLeft, 0);
        paddingTop = typedArray.getDimension(R.styleable.sunRise_paddingTop, 0);
        paddingRight = typedArray.getDimension(R.styleable.sunRise_paddingRight, 0);
        paddingBottom = typedArray.getDimension(R.styleable.sunRise_paddingBottom, 0);

        circleWidth = typedArray.getDimension(R.styleable.sunRise_runRiseCircleWidth, dpToPx(context, DEFAULT_ARC_WIDTH));
        dashEffectLength = typedArray.getDimensionPixelSize(R.styleable.sunRise_dashEffectLength, dpToPx(context, DEFAULT_DASH_EFFECT_LENGTH));
        pictureSize = typedArray.getDimensionPixelSize(R.styleable.sunRise_pictureSize, dpToPx(context, DEFAULT_PICTURE_SIZE));
        textSize = typedArray.getDimensionPixelSize(R.styleable.sunRise_textSize, dpToPx(context, DEFAULT_TEXT_SIZE));
        typedArray.recycle();
    }

    /**
     * 日出日落动画
     */
    public void startAnimations() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        riseUpTimeHour = 7;
        riseUpTimeMin = 10;
        riseDownTimeHour = 18;
        riseDownTimeMin = 30;
        if (riseUpTimeHour < 10) {
            riseUpTime = "0" + String.valueOf(riseUpTimeHour) + ":";
        } else {
            riseUpTime = String.valueOf(riseUpTimeHour) + ":";
        }
        if (riseUpTimeMin < 10) {
            riseUpTime = riseUpTime + "0" + riseUpTimeMin;
        } else {
            riseUpTime = riseUpTime + riseUpTimeMin;
        }

        if (riseDownTimeHour < 10) {
            riseDownTime = "0" + String.valueOf(riseDownTimeHour) + ":";
        } else {
            riseDownTime = String.valueOf(riseDownTimeHour) + ":";
        }
        if (riseDownTimeMin < 10) {
            riseDownTime = riseDownTime + "0" + riseDownTimeMin;
        } else {
            riseDownTime = riseDownTime + riseDownTimeMin;
        }

        float total = (riseDownTimeHour * 60 + riseDownTimeMin) - (riseUpTimeHour * 60 + riseUpTimeMin);
        float passedTime = (hour * 60 + minute) - (riseUpTimeHour * 60 + riseUpTimeMin);
        float percent = passedTime / total;
        float angle = percent * sweepAngle;
        animation = ValueAnimator.ofFloat(0, angle);
        animation.setDuration(5000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (180 - sweepAngle) / 2 + (float) animation.getAnimatedValue();
                currentSweepAngle = value - 15;
                double sin = Math.sin(Math.PI * value / 180);
                double cos = Math.cos(Math.PI * value / 180);
                float centerX = rectF.centerX();
                float centerY = rectF.centerY();
                rectLocationX = (float) (centerX - cos * circleRadius);
                rectLocationY = (float) (centerY - sin * circleRadius - paddingTop);
                //   Log.d("sun", "value = " + value + ">>sin = " + sin + ">>cos =" + cos + ">>centerX = " + centerX + "centerY = " + centerY + ">>rectLocationY = " + rectLocationY + ">>circleRadius = " + circleRadius);
                rectLocation.left = (int) (rectLocationX - pictureSize / 2);
                rectLocation.top = (int) (rectLocationY - pictureSize / 2);
                rectLocation.right = (int) (rectLocationX + pictureSize / 2);
                rectLocation.bottom = (int) (rectLocationY + pictureSize / 2);

                invalidate();
            }
        });
        animation.start();
    }

    private static int measures(int measureSpec, int defaultValue) {
        int result = defaultValue;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(specSize, defaultValue);
        }
        return result;
    }

    private int dpToPx(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
