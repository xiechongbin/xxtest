package com.xiaoxie.weightrecord.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;

/**
 * desc:仪表盘view
 * Created by xiaoxie on 2017/8/14.
 */
public class DashBoardView extends LinearLayout implements View.OnClickListener {
    private float WIDTH;//可见区域宽度
    private float HEIGHT;//可见区域高度

    private float RECT_WHTDH;//矩形宽
    private float RECT_HEIGHT;//矩形高
    private ImageView imageView;
    private float currentAngle = 0;//绘制进度条圆弧转过的角度
    private float bmi = 0;//bmi的值

    /**
     * 第一个方法，一般我们这样使用时会被调用，View view = new View(context);
     */
    public DashBoardView(Context context) {
        super(context);
        initView();
    }

    /**
     * 第二个方法，当我们在xml布局文件中使用View时，会在inflate布局时被调用
     *
     * @param context
     * @param attrs
     */
    public DashBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 第三个方法，跟第二种类似，但是增加style属性设置，这时inflater布局时会调用第三个构造方法。
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        setWillNotDraw(false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        WIDTH = getWidth();
        HEIGHT = getHeight();
        canvas.drawColor(getResources().getColor(R.color.color_27ae60));//绘制背景
        drawOutSideArc(canvas);//绘制外圈圆弧
        drawProgessArc(canvas);//绘制外圈进度 默认为0
        drawInnerSideArc(canvas);//绘制内圈圆弧
        drawScale(canvas);//绘制刻度
        drawText(canvas, 17, WIDTH / 2, WIDTH / 6, "BMI");
        drawText(canvas, 40, WIDTH / 2, WIDTH * 0.45f, String.valueOf(bmi));

    }

    /**
     * 绘制外圈圆弧
     *
     * @param canvas
     */
    private void drawOutSideArc(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_6bc792));
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(WIDTH / 6 + 20, 3 + 20, (WIDTH / 3) * 2 + WIDTH / 6 - 20, (WIDTH / 3) * 2 - 20);//绘制矩形区域
        canvas.drawArc(rectF, 150, 240, false, paint);//绘制外圈
    }

    /**
     * 绘制进度
     *
     * @param canvas
     */
    private void drawProgessArc(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.yellow));
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);

        RectF rectF = new RectF(WIDTH / 6 + 20, 3 + 20, (WIDTH / 3) * 2 + WIDTH / 6 - 20, (WIDTH / 3) * 2 - 20);//绘制矩形区域
        canvas.drawArc(rectF, 150, currentAngle, false, paint);
    }

    /**
     * 绘制内圈圆弧
     *
     * @param canvas
     */
    private void drawInnerSideArc(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_6bc792));
        paint.setStrokeWidth(13);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF_inner = new RectF(WIDTH / 6 + 40, 43, (WIDTH / 3) * 2 - 40 + WIDTH / 6, (WIDTH / 3) * 2 - 40);//绘制矩形区域
        canvas.drawArc(rectF_inner, 150, 240, false, paint);//绘制内圈
        canvas.save();
    }


    /**
     * 绘制刻度
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_aadfce));
        paint.setStrokeWidth(2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(WIDTH / 2, WIDTH / 3);//将画布移到中心

        float y = WIDTH / 3 - 40 - (13) / 2;//刻度刻画y坐标 为内圈的半径减去线宽
        int count = 60; //总刻度数
        Paint paint_text = new Paint();
        paint_text.setTextSize(25);
        paint_text.setColor(Color.WHITE);
        for (int i = 0; i < count; i++) {
            // if (i > 9 && i < 51) {
            if (i % 5 == 0) {
                canvas.drawText(String.valueOf(i), -10, y - 15, paint_text);
            }
            canvas.drawLine(0, y, 0, y + 10f, paint);//绘制刻度线
            // }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }
        canvas.restore();
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, int textSize, float x, float y, String str) {
        Paint vTextPaint = new Paint();
        vTextPaint.setTextSize(dipToPx(textSize));
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//抗锯齿功能
        vTextPaint.setColor(Color.WHITE);
        Rect bounds = new Rect();
        vTextPaint.getTextBounds(str, 0, str.length(), bounds);
        canvas.drawText(str, x, y, vTextPaint);
    }

    /**
     * @param fontSize 字体大小
     * @return 字体高度
     */
    public int getFontHeight(String str, float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Rect bounds_Number = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds_Number);
        return bounds_Number.height();
    }

    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */

    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 设置当前bmi的值对应的进度
     *
     * @param value
     */
    public void setProgress(float value) {
        float total = 60f;
        bmi = value;
        float endAngle = ((value - 10) / total) * 360f;
        setAnimation(0, endAngle, 3000);
    }

    private void setAnimation(float start, float end, int duration) {
        ValueAnimator p_animator = ValueAnimator.ofFloat(start, end);
        p_animator.setDuration(duration);
        p_animator.setTarget(currentAngle);
        p_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentAngle = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        p_animator.start();
        ValueAnimator t_animator = ValueAnimator.ofFloat(start, bmi);
        t_animator.setDuration(duration);
        t_animator.setTarget(bmi);
        t_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bmi = (float) valueAnimator.getAnimatedValue();
                bmi = (float) (Math.round(bmi * 100)) / 100;
                invalidate();
            }
        });
        t_animator.start();

    }

    @Override
    public void onClick(View view) {
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(10000);//设置动画持续时间
        TranslateAnimation ta = new TranslateAnimation(100, 0, 200, 0);
        ta.setDuration(10000);//设置动画持续时间
/** 常用方法 */
//animation.setRepeatCount(int repeatCount);//设置重复次数
//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间
        imageView.startAnimation(ta);
    }
}
