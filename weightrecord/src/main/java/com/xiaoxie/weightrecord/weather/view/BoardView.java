package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoxie.weightrecord.R;

/**
 * 自定义表盘
 * Created by xcb on 2018/2/7.
 */

public class BoardView extends View {
    private Context context;
    private static final int DEFAULT_VALUE = 150;
    private static final int DEFAULT_STOKE_WIDTH = 10;
    private static final int DEFAULT_INNER_RADIUS_WIDTH = 10;
    private static final int DEFAULT_OUT_RADIUS_WIDTH = 100;
    private static final int DEFAULT_MIN_CALIBRATION_WIDTH = 5;
    private static final int DEFAULT_MAX_CALIBRATION_RADIUS_WIDTH = 10;
    private static final float TOTAL_ANGLE = 360f;
    private int lineColor;
    private Paint paint;
    private float stokeWidth;
    private Point point;
    private float innerRadius;
    private float outSideRadius;
    private float minCalibration;
    private float maxCalibration;
    private int calibrationCount;
    private Point pointCalibration_start;
    private Point pointCalibration_short_end;
    private Point pointCalibration_long_end;

    private float degress;

    public BoardView(Context context) {
        super(context);
        init(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defaultValue = dipToPx(context, DEFAULT_VALUE);
        int width = measures(widthMeasureSpec, defaultValue);
        int height = measures(heightMeasureSpec, defaultValue);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        point.x = w / 2;
        point.y = h / 2;
        pointCalibration_start.x = point.x;
        pointCalibration_start.y = (int) (point.y - outSideRadius - stokeWidth);
        pointCalibration_short_end.x = point.x;
        pointCalibration_short_end.y = (int) (point.y - outSideRadius - stokeWidth - minCalibration);
        pointCalibration_long_end.x = pointCalibration_short_end.x;
        pointCalibration_long_end.y = (int) (point.y - outSideRadius - stokeWidth - maxCalibration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(point.x, point.y, paint);
        canvas.drawCircle(point.x, point.y, innerRadius, paint);
        canvas.drawCircle(point.x, point.y, outSideRadius, paint);
        drawCalibration(canvas);
        canvas.save();
        degress = degress + TOTAL_ANGLE / 12;
        canvas.rotate(degress, point.x, point.y);
        canvas.drawLine(point.x, point.y, pointCalibration_short_end.x, pointCalibration_short_end.y, paint);
        postInvalidateDelayed(1000);
    }

    /**
     * 画刻度
     */
    private void drawCalibration(Canvas canvas) {
        int count = (int) (TOTAL_ANGLE / calibrationCount);
        canvas.drawLine(pointCalibration_start.x, pointCalibration_start.y, pointCalibration_short_end.x, pointCalibration_short_end.y, paint);
        for (int i = 0; i < calibrationCount; i++) {
            canvas.rotate(count, point.x, point.y);
            if (i % 5 == 0) {
                canvas.drawLine(pointCalibration_start.x, pointCalibration_start.y, pointCalibration_long_end.x, pointCalibration_long_end.y, paint);
            } else {
                canvas.drawLine(pointCalibration_start.x, pointCalibration_start.y, pointCalibration_short_end.x, pointCalibration_short_end.y, paint);
            }

        }
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.BoardView);
        lineColor = array.getColor(R.styleable.BoardView_lineColor, Color.BLUE);
        stokeWidth = array.getDimension(R.styleable.BoardView_stokeWidth, dipToPx(context, DEFAULT_STOKE_WIDTH));
        innerRadius = array.getDimension(R.styleable.BoardView_innerRadius, dipToPx(context, DEFAULT_INNER_RADIUS_WIDTH));
        outSideRadius = array.getDimension(R.styleable.BoardView_outSideRadius, dipToPx(context, DEFAULT_OUT_RADIUS_WIDTH));
        minCalibration = array.getDimension(R.styleable.BoardView_minCalibration, dipToPx(context, DEFAULT_MIN_CALIBRATION_WIDTH));
        maxCalibration = array.getDimension(R.styleable.BoardView_maxCalibration, dipToPx(context, DEFAULT_MAX_CALIBRATION_RADIUS_WIDTH));
        calibrationCount = array.getInt(R.styleable.BoardView_calibrationCount, 60);
        array.recycle();
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(stokeWidth);
        point = new Point();
        pointCalibration_start = new Point();
        pointCalibration_short_end = new Point();
        pointCalibration_long_end = new Point();
    }

    private static int measures(int measureSpec, int defaultValue) {
        int result = defaultValue;
        int mode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(measureSpec, defaultValue);
        }
        return result;
    }

    private static int dipToPx(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
