package com.xiaoxie.weightrecord.weather.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xiaoxie.weightrecord.BuildConfig;
import com.xiaoxie.weightrecord.R;

/**
 * Created by xcb on 2018/2/6.
 * 圆形进度条
 */

public class CircleProgressView extends View {
    private static final String TAG = CircleProgressView.class.getSimpleName();
    private int mDefaultSize;
    //上下文
    private Context context;
    //是否开启抗锯齿
    private boolean antiAlias;
    //绘制提示
    private TextPaint mHintPaint;
    private CharSequence mHint;
    private int mHintColor;
    private float mHintSize;
    private float mHintOffset;

    //绘制数值
    private TextPaint mValuePaint;
    private float minValue;
    private float maxValue;
    private float mValueOffset;
    private int mPrecision;
    private String mPrecisionFormat;
    private int mValueColor;
    private float mValueSize;
    private int animationTime;

    //绘制单位
    private TextPaint mUnitPaint;
    private CharSequence mUnit;
    private int mUnitColor;
    private float mUnitSize;
    private float mUnitOffset;
    //绘制圆弧
    private Paint mArcPaint;
    private float mArcWidth;
    private float mStartAngle, mSweepAngle;
    private RectF mRectF;
    //渐变的颜色是360度，如果只显示270，那么则会缺失部分颜色
    private SweepGradient mSweepGradient;
    private int[] mGradientColors = {Color.GREEN, Color.YELLOW, Color.RED};
    //当前进度，[0.0f,1.0f]
    private float mPercent;
    //动画时间
    private long mAnimTime;
    //属性动画
    private ValueAnimator mAnimator;

    //绘制背景圆弧
    private Paint mBgArcPaint;
    private int mBgArcColor;
    private float mBgArcWidth;

    //圆心坐标，半径
    private Point mCenterPoint;
    private float mRadius;
    private float mTextOffsetPercentInRadius;

    public CircleProgressView(Context context) {
        super(context);
        init(context);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(attrs);
        initPaint();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttrs(attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        Log.d(TAG, "onSizeChanged: w = " + w + "; h = " + h + "; oldw = " + oldW + "; oldh = " + oldH);
        float maxArcWidth = Math.max(mArcWidth, mBgArcWidth);//求圆弧和背景圆弧的最大宽度

        //求最小值作为实际值 //减去圆弧的宽度，否则会造成部分圆弧绘制在外围
        int minSize = Math.min(w - getPaddingLeft() - getPaddingRight() - 2 * (int) maxArcWidth, h - getPaddingTop() - getPaddingBottom() - 2 * (int) maxArcWidth);
        mRadius = minSize / 2;
        //获取圆的相关参数
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        mRectF.left = mCenterPoint.x - mRadius - maxArcWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - maxArcWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + maxArcWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + maxArcWidth / 2;

        //计算文字绘制时的 baseline
        //由于文字的baseline、descent、ascent等属性只与textSize和typeface有关，所以此时可以直接计算
        //若value、hint、unit由同一个画笔绘制或者需要动态设置文字的大小，则需要在每次更新后再次计算
        mValueOffset = mCenterPoint.y + getBaselineOffsetFromY(mValuePaint);
        mHintOffset = mCenterPoint.y - mRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mHintPaint);
        mUnitOffset = mCenterPoint.y + mRadius * mTextOffsetPercentInRadius + getBaselineOffsetFromY(mUnitPaint);
        updateArcPaint();
        Log.d(TAG, "onSizeChanged: 控件大小 = " + "(" + w + ", " + h + ")"
                + "圆心坐标 = " + mCenterPoint.toString()
                + ";圆半径 = " + mRadius
                + ";圆的外接矩形 = " + mRectF.toString());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Utils.measure(widthMeasureSpec, mDefaultSize);
        int height = Utils.measure(heightMeasureSpec, mDefaultSize);
        Log.d(TAG, "onMeasure: width = " + width + ">>height = " + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw:");
        drawText(canvas);
        drawCircle(canvas);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        this.context = context;
        mDefaultSize = Utils.dipToPx(context, Constant.DEFAULT_SIZE);
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mCenterPoint = new Point();
    }

    /**
     * 初始化资源
     */
    private void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.progressView);
        antiAlias = typedArray.getBoolean(R.styleable.progressView_antiAlias, Constant.ANTIALIAS);

        mHint = typedArray.getString(R.styleable.progressView_hint);
        mHintColor = typedArray.getColor(R.styleable.progressView_hintColor, Color.BLACK);
        mHintSize = typedArray.getDimension(R.styleable.progressView_hintSize, Constant.DEFAULT_HINT_SIZE);

        minValue = typedArray.getFloat(R.styleable.progressView_minValue, Constant.DEFAULT_VALUE);
        maxValue = typedArray.getFloat(R.styleable.progressView_maxValue, Constant.DEFAULT_MAX_VALUE);
        //内容数值精度格式
        mPrecision = typedArray.getInt(R.styleable.progressView_precision, 0);
        mPrecisionFormat = Utils.getPrecisionFormat(mPrecision);
        mValueColor = typedArray.getColor(R.styleable.progressView_valueColor, Color.BLACK);
        mValueSize = typedArray.getDimension(R.styleable.progressView_valueSize, Constant.DEFAULT_VALUE_SIZE);

        mUnit = typedArray.getString(R.styleable.progressView_unit);
        mUnitColor = typedArray.getColor(R.styleable.progressView_unitColor, Color.BLACK);
        mUnitSize = typedArray.getDimension(R.styleable.progressView_unitSize, Constant.DEFAULT_UNIT_SIZE);

        mArcWidth = typedArray.getDimension(R.styleable.progressView_arcWidth, Constant.DEFAULT_ARC_WIDTH);
        mStartAngle = typedArray.getFloat(R.styleable.progressView_startAngle, Constant.DEFAULT_START_ANGLE);
        mSweepAngle = typedArray.getFloat(R.styleable.progressView_sweepAngle, Constant.DEFAULT_SWEEP_ANGLE);

        mBgArcColor = typedArray.getColor(R.styleable.progressView_bgArcColor, Color.WHITE);
        mBgArcWidth = typedArray.getDimension(R.styleable.progressView_bgArcWidth, Constant.DEFAULT_ARC_WIDTH);
        mTextOffsetPercentInRadius = typedArray.getFloat(R.styleable.progressView_textOffsetPercentInRadius, 0.33f);

        animationTime = typedArray.getInt(R.styleable.progressView_animTime, Constant.DEFAULT_ANIM_TIME);
        int gradientArcColors = typedArray.getResourceId(R.styleable.progressView_arcColors, 0);
        if (gradientArcColors != 0) {
            try {
                int[] gradientColors = getResources().getIntArray(gradientArcColors);
                if (gradientColors.length == 0) {//如果渐变色为数组为0，则尝试以单色读取色值
                    int color = getResources().getColor(gradientArcColors);
                    mGradientColors = new int[2];
                    mGradientColors[0] = color;
                    mGradientColors[1] = color;
                } else if (gradientColors.length == 1) {//如果渐变数组只有一种颜色，默认设为两种相同颜色
                    mGradientColors = new int[2];
                    mGradientColors[0] = gradientColors[0];
                    mGradientColors[1] = gradientColors[0];
                } else {
                    mGradientColors = gradientColors;
                }
            } catch (Resources.NotFoundException e) {
                throw new Resources.NotFoundException("the give resource not found.");
            }
        }
        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mHintPaint = new TextPaint();
        mHintPaint.setAntiAlias(antiAlias); // 设置抗锯齿,会消耗较大资源，绘制图形速度会变慢。
        mHintPaint.setTextSize(mHintSize);  // 设置绘制文字大小
        mHintPaint.setColor(mHintColor); // 设置画笔颜色
        mHintPaint.setTextAlign(Paint.Align.CENTER);   // 从中间向两边绘制，不需要再次计算文字

        mValuePaint = new TextPaint();
        mValuePaint.setAntiAlias(antiAlias);
        mValuePaint.setTextSize(mValueSize);
        mValuePaint.setColor(mValueColor);
        mValuePaint.setTypeface(Typeface.DEFAULT_BOLD); // 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        mUnitPaint = new TextPaint();
        mUnitPaint.setAntiAlias(antiAlias);
        mUnitPaint.setTextSize(mUnitSize);
        mUnitPaint.setColor(mUnitColor);
        mUnitPaint.setTextAlign(Paint.Align.CENTER);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(antiAlias);
        mArcPaint.setStyle(Paint.Style.STROKE);  // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE

        mArcPaint.setStrokeWidth(mArcWidth); // 设置画笔粗细
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);// 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式  // Cap.ROUND,或方形样式 Cap.SQUARE

        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(antiAlias);
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mBgArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        // 计算文字宽度，由于Paint已设置为居中绘制，故此处不需要重新计算
        // float textWidth = mValuePaint.measureText(mValue.toString());
        // float x = mCenterPoint.x - textWidth / 2;
        canvas.drawText(String.format(mPrecisionFormat, minValue), mCenterPoint.x, mValueOffset, mValuePaint);

        if (mHint != null) {
            canvas.drawText(mHint.toString(), mCenterPoint.x, mHintOffset, mHintPaint);
        }

        if (mUnit != null) {
            canvas.drawText(mUnit.toString(), mCenterPoint.x, mUnitOffset, mUnitPaint);
        }
    }

    /**
     * 绘制圆弧
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        float currentAngle = mSweepAngle * mPercent;
        canvas.rotate(mStartAngle, mCenterPoint.x, mCenterPoint.y);
        canvas.drawArc(mRectF, currentAngle, mSweepAngle - currentAngle + 2, false, mBgArcPaint);
        // 第一个参数 oval 为 RectF 类型，即圆弧显示区域
        // startAngle 和 sweepAngle  均为 float 类型，分别表示圆弧起始角度和圆弧度数
        // 3点钟方向为0度，顺时针递增
        // 如果 startAngle < 0 或者 > 360,则相当于 startAngle % 360
        // useCenter:如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        canvas.drawArc(mRectF, 2, currentAngle, false, mArcPaint);
        canvas.restore();
    }

    /**
     * 更新圆弧画笔
     */
    private void updateArcPaint() {
        // 设置渐变
        mSweepGradient = new SweepGradient(mCenterPoint.x, mCenterPoint.y, mGradientColors, null);
        mArcPaint.setShader(mSweepGradient);
    }

    private float getBaselineOffsetFromY(Paint paint) {
        return Utils.measureTextHeight(paint) / 2;
    }

    public boolean isAntiAlias() {
        return antiAlias;
    }

    public CharSequence getHint() {
        return mHint;
    }

    public void setHint(CharSequence hint) {
        mHint = hint;
    }

    public CharSequence getUnit() {
        return mUnit;
    }

    public void setUnit(CharSequence unit) {
        mUnit = unit;
    }

    public float getValue() {
        return minValue;
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    public void setValue(float value) {
        if (value > maxValue) {
            value = maxValue;
        }
        float start = mPercent;
        float end = value / maxValue;
        startAnimator(start, end, mAnimTime);
    }

    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                minValue = mPercent * maxValue;
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "onAnimationUpdate: percent = " + mPercent
                            + ";currentAngle = " + (mSweepAngle * mPercent)
                            + ";value = " + minValue);
                }
                invalidate();
            }
        });
        mAnimator.start();
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public float getMaxValue() {
        return maxValue;
    }

    /**
     * 设置最大值
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue) {
        maxValue = maxValue;
    }

    /**
     * 获取精度
     *
     * @return
     */
    public int getPrecision() {
        return mPrecision;
    }

    public void setPrecision(int precision) {
        mPrecision = precision;
        mPrecisionFormat = Utils.getPrecisionFormat(precision);
    }

    public int[] getGradientColors() {
        return mGradientColors;
    }

    /**
     * 设置渐变
     *
     * @param gradientColors
     */
    public void setGradientColors(int[] gradientColors) {
        mGradientColors = gradientColors;
        updateArcPaint();
    }

    public long getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(long animTime) {
        mAnimTime = animTime;
    }

    /**
     * 重置
     */
    public void reset() {
        startAnimator(mPercent, 0.0f, 1000L);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //释放资源
    }

    private class Constant {
        static final boolean ANTIALIAS = false;
        static final int DEFAULT_SIZE = 150;
        static final int DEFAULT_START_ANGLE = 270;
        static final int DEFAULT_SWEEP_ANGLE = 360;

        static final int DEFAULT_ANIM_TIME = 1000;

        static final int DEFAULT_MAX_VALUE = 100;
        static final int DEFAULT_VALUE = 50;

        static final int DEFAULT_HINT_SIZE = 15;
        static final int DEFAULT_UNIT_SIZE = 30;
        static final int DEFAULT_VALUE_SIZE = 15;

        static final int DEFAULT_ARC_WIDTH = 15;

        public static final int DEFAULT_WAVE_HEIGHT = 40;
    }

    private static class Utils {
        /**
         * 获取数值精度格式化字符串
         *
         * @param precision
         * @return
         */
        public static String getPrecisionFormat(int precision) {
            return "%." + precision + "f";
        }

        /**
         * 计算控件大小
         */
        public static int measure(int measureSpec, int defaultSize) {
            int result = defaultSize;
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);
            if (specMode == MeasureSpec.EXACTLY) {
                result = specSize;
            } else if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
            return result;
        }

        /**
         * DP转px
         */
        public static int dipToPx(Context context, float dip) {
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
        }

        /**
         * 测量文字高度
         *
         * @param paint
         * @return
         */
        public static float measureTextHeight(Paint paint) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            return (Math.abs(fontMetrics.ascent) - fontMetrics.descent);
        }
    }
}
