package com.xiaoxie.weightrecord.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xiaoxie.weightrecord.R;
import com.xiaoxie.weightrecord.utils.Utils;

import java.util.LinkedHashSet;

/**
 * 视图结构为3X3的矩阵点集合，点周围一定区域为有效范围s，这个有效范围不能大于相邻两个点的距离的一半。
 * 手指落在这个有效范围s内，则判定该touch对这个点有效，手势路径就会将这个点计算在内 手指落在点内则会有一个该点逐渐放大然后缩小的动画效果
 * 手指从一个点移出的时候在有效范围s内
 * ，手指到该点之间的线段是不显示的，当手指移出范围s，到手指离该点2s之间过程手指与该点之间的线段逐渐显示,所以综上来说s是小于点点间距的三分之一的
 * 如果手势密码错误则已经连接的线和点都变成橘黄色，并持续三秒钟再复原,如果在这三秒钟内有手势操作则立即复原
 */
public class GestureLockView extends View {
    /**
     * 点选中时候的动画时间
     */
    private static final int ANIM_DURATION = 150;
    /**
     * 错误路径持续时长
     */
    private static final int RECOVER_DURATION = 3000;
    /**
     * 矩阵的边数
     */
    private final int row = 3;
    /**
     * 密码路径
     */
    private PasswordPath passwordPath;
    /**
     * 记录view的边长
     */
    private int width;

    /**
     * 根据view的尺寸确定
     * 两点之间的间距 为边长的三分之一
     */
    private float pDis;
    /**
     * 点的初始半径 为pDis的二十分之一
     */
    private float pointR;
    /**
     * 点的动画最大半径 为点半径的2.5倍
     */
    private float pointREx;
    /**
     * 点的‘领域’半径 为pDis的十分之三
     */
    private float safeDis;
    /**
     * view中点的矩形和view的外边界距离 为宽度的六分之一
     */
    private float mPadding;

    private PasswordPoint[][] points = new PasswordPoint[3][3];
    private boolean isRight = true;
    private Context context;

    private GestureLockCallback callback;
    private Result result = new Result();
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {

        @Override
        public void run() {
            reset();
        }
    };

    public GestureLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public GestureLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public GestureLockView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        passwordPath = new PasswordPath();
    }

    /**
     * 画点
     */
    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                points[i][j].draw(canvas, true);
            }
        }
    }

    /**
     * 重置点的属性
     */
    private void resetPoints(int w) {
        initSizeParams(w);
        initPoints();
    }

    /**
     * 确定上边提到的参数的值
     */
    private void initSizeParams(int w) {
        mPadding = w / 6.0f;
        pDis = w / 3.0f;
        pointR = pDis / 17;
        pointREx = pointR * 2.5f;
        safeDis = pDis * 3.0f / 10;
        float lineW = pointR / 1.8f;//画出的线的宽度 为点半径的二分之一
        passwordPath.setLineWidth(lineW);
    }

    /**
     * 初始化点
     */
    private void initPoints() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                points[i][j] = new PasswordPoint(i * pDis + mPadding, j * pDis + mPadding, pointR);
            }
        }
    }

    /**
     * 的到触点所在的‘领域’内的点, 如果不在领域内则返回null
     */
    private PasswordPoint getControllerPoint(float x, float y) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (points[i][j].isInCtrl(x, y)) {
                    return points[i][j].setIndex(i, j);
                }
            }
        }
        return null;
    }

    /**
     * 复原view状态
     */
    private void reset() {
        passwordPath = new PasswordPath();
        resetPoints(width);
        isRight = true;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Utils.getColor(context, R.color.color_1d2126));
        drawPoints(canvas);
        passwordPath.draw(canvas, isRight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//该view为矩形
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        w = h = Math.min(w, h);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        width = w;
        resetPoints(width);
    }

    /**
     * 在这个点上做运动 o 动画
     */
    private void doAnimation(final PasswordPoint down) {
        ValueAnimator va = ValueAnimator.ofFloat(pointR, pointREx);
        va.setDuration(ANIM_DURATION);
        va.setRepeatCount(1);
        va.setRepeatMode(ValueAnimator.REVERSE);
        va.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                down.r = Float.parseFloat(animation.getAnimatedValue().toString());
                invalidate();
            }
        });
        va.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                down.animating = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down.animating = false;
                down.selected = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        va.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isRight) {
                handler.removeCallbacks(task);
                reset();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eX = event.getX();
        float eY = event.getY();
        int action = event.getAction();
        PasswordPoint down = getControllerPoint(eX, eY);
        if (null != down) {
            if (!down.animating && !down.selected) {
                doAnimation(down);
            }
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (null != down && !down.selected) {
                    passwordPath.moveTo(down);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (null != down && !down.selected) {
                    passwordPath.lineTo(down);
                }
                if (passwordPath.last != null) {

                    passwordPath.startTo(eX, eY);
                }
                invalidate();

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                passwordPath.pathStart.reset();
                if (null != down && !down.selected) {
                    passwordPath.lineTo(down);
                    invalidate();
                }
                if (passwordPath.getPwd().length() > 0) {
                    if (null != callback) {
                        callback.onFinish(passwordPath.getPwd(), result);
                    }
                    if (result.isRight) {
                        reset();
                    } else {
                        isRight = false;
                        invalidate();
                        handler.postDelayed(task, RECOVER_DURATION);
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 显示主体的点的实体类
     */
    private class PasswordPoint {
        float x, y, r;//点横纵坐标和半径
        boolean animating;//是不是正在执行放大缩小动画
        boolean selected;//是不是已经被连入密码path
        Paint nPointpPaint;// 正常点画笔
        Paint ePointpPaint;// 错误的点得画笔

        private PasswordPoint(float x, float y, float r) {
            super();
            this.x = x;
            this.y = y;
            this.r = r;

            nPointpPaint = new Paint();
            nPointpPaint.setColor(Color.WHITE);
            nPointpPaint.setStyle(Style.FILL_AND_STROKE);

            ePointpPaint = new Paint();
            ePointpPaint.setColor(Color.RED);
            ePointpPaint.setStyle(Style.FILL_AND_STROKE);
        }

        private void draw(Canvas canvas, boolean a) {
            canvas.drawCircle(x, y, r, a ? nPointpPaint : ePointpPaint);
        }

        // 判定是否应该连接该点
        private boolean isInCtrl(float x, float y) {
            return (x - this.x) * (x - this.x) + (this.y - y) * (this.y - y) <= safeDis * safeDis;
        }

        /**
         * 设置点在二维数组中的坐标
         */
        private PasswordPoint setIndex(int i, int j) {
            this.i = i;
            this.j = j;
            return this;
        }

        int i, j;//记录的二维数组中的坐标

    }

    /**
     * 手势密码的抽象，包括路径和路径上的点 点集合就是路径上已经‘吸附’的点，路径有两个 一个path 是点与点之间的连接的路径
     * 另外一个pathStart是
     * 手指移动的位置和path最后一个‘吸附’的点之间的路径，这个路径不断随着手指移动而变化,并且还有透明度等需要处理所以单独出来
     */
    private class PasswordPath {
        // ArrayList<PasswordPoint> pwdps;
        Path path;// 正常的连接的路径 即点与点之间的连接path
        Path pathStart;// 从一个点出发 终点任意的path
        PasswordPoint last;
        private Paint nPaint,// 正常颜色线的画笔
                ePaint, // 错误颜色线的画笔
                endPaint;// 手指移动的时候多余部分的线的画笔
        LinkedHashSet<PasswordPoint> pwdps;

        private PasswordPath() {
            pwdps = new LinkedHashSet<>();
            path = new Path();
            pathStart = new Path();

            nPaint = new Paint();
            nPaint.setColor(Color.WHITE);
            nPaint.setStyle(Style.STROKE);

            endPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            endPaint.setColor(Color.WHITE);
            endPaint.setStyle(Style.STROKE);
            endPaint.setStrokeCap(Cap.ROUND);

            ePaint = new Paint();
            ePaint.setColor(Color.RED);
            ePaint.setStyle(Style.STROKE);

        }

        private String getPwd() {
            StringBuilder sb = new StringBuilder();
            for (PasswordPoint p : pwdps) {
                int k = p.i + 1 + p.j * row;
                sb.append(k);
            }
            return sb.toString();
        }

        private void setLineWidth(float lineW) {
            nPaint.setStrokeWidth(lineW);
            ePaint.setStrokeWidth(lineW);
            endPaint.setStrokeWidth(lineW);
        }

        /**
         * 移动到可以吸附的点，两个路径都需要接入这个点，两个路径都需要将这个点设为起始点
         */
        private void moveTo(PasswordPoint p) {
            path.reset();
            path.moveTo(p.x, p.y);
            pwdps = new LinkedHashSet<>();

            exAdd(p);
            pwdps.add(p);

            pathStart.reset();
            pathStart.moveTo(p.x, p.y);
            last = p;
        }

        /**
         * 处理两个连接点之间有一个未连接点得情况
         */
        private void exAdd(PasswordPoint p) {
            int i1 = null == last ? p.i : last.i;
            int i2 = p.i;
            int j1 = null == last ? p.j : last.j;
            int j2 = p.j;
            if (Math.abs(i1 - i2) == 2 && j1 == j2) {
                if (!points[(i1 + i2) / 2][j1].selected) {
                    doAnimation(points[(i1 + i2) / 2][j1]);
                    pwdps.add(points[(i1 + i2) / 2][j1].setIndex((i1 + i2) / 2, j1));
                }
            } else if (Math.abs(j1 - j2) == 2 && i1 == i2) {
                if (!points[i1][(j1 + j2) / 2].selected) {
                    doAnimation(points[i1][(j1 + j2) / 2]);
                    pwdps.add(points[i1][(j1 + j2) / 2].setIndex(i1, (j1 + j2) / 2));
                }
            } else if (Math.abs(i1 - i2) == 2 && Math.abs(j1 - j2) == 2) {
                if (!points[(i1 + i2) / 2][(j1 + j2) / 2].selected) {
                    doAnimation(points[(i1 + i2) / 2][(j1 + j2) / 2]);
                    pwdps.add(points[(i1 + i2) / 2][(j1 + j2) / 2].setIndex((i1 + i2) / 2, (j1 + j2) / 2));
                }
            }
        }

        /**
         * 意思同path的lineTo方法，path需要执行lineTo方法，而pathStart则仍然需要设置成起始点
         */
        private void lineTo(PasswordPoint p) {
            path.lineTo(p.x, p.y);
            exAdd(p);
            pwdps.add(p);
            pathStart.reset();
            pathStart.moveTo(p.x, p.y);
            last = p;
        }

        PasswordPoint touchP;

        /**
         * 这是在吸附了一个点之后，手指移动
         * 这个时候需要重新初始化该path，起始点为pwdpath连接到的最后一个点，终点为传入的触摸事件发生的位置
         */
        private void startTo(float x, float y) {
            pathStart.reset();
            pathStart.moveTo(last.x, last.y);
            pathStart.lineTo(x, y);
            touchP = new PasswordPoint(x, y, 0);
        }

        /**
         * 绘制密码路径,正确的时候不画点，错误的时候画点
         */
        private void draw(Canvas c, boolean isRight) {
            if (isRight) {
                c.drawPath(path, nPaint);
            } else {
                c.drawPath(path, ePaint);
                for (PasswordPoint point : pwdps) {
                    point.draw(c, false);
                }
            }
            endPaint.setColor(Color.WHITE);
            c.drawPath(pathStart, endPaint);
        }
    }

    public class Result {
        private boolean isRight;

        public void setRight(boolean isRight) {
            this.isRight = isRight;
        }

    }

    /**
     * 手势密码回调接口
     *
     * @author monkey-d-wood
     */
    public interface GestureLockCallback {
        void onFinish(String pwdString, Result result);
    }

    public void setCallback(GestureLockCallback callback) {
        this.callback = callback;
    }


}
