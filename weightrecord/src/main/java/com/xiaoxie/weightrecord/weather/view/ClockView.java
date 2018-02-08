package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoxie.weightrecord.R;

/**
 * 自定义view
 * Created by xcb on 2018/2/8.
 */

public class ClockView extends View {
    private Context context;
    //是否开启抗锯齿
    private boolean antiAlias = true;
    //是否平滑转动秒针
    private boolean smooth = false;
    private Drawable background;
    //表盘的边距
    private float wb_padding;
    //表盘文字大小
    private float wb_text_size;
    //表盘文字颜色
    private int wb_text_color;
    //时针的宽度
    private float wb_hour_pointer_width;
    //分针的宽度
    private float wb_minute_pointer_width;
    //秒针的宽度
    private float wb_second_pointer_width;
    //时针的高度
    private float wb_hour_pointer_height;
    //分针的高度
    private float wb_minute_pointer_height;
    //秒针的高度
    private float wb_second_pointer_height;
    //指针圆角值
    private float wb_pointer_corner_radius;
    //指针超过中心点的长度
    private float wb_pointer_end_length;
    //时刻刻度颜色
    private int wb_scale_long_color;
    //非时刻刻度颜色
    private int wb_scale_short_color;
    //时针颜色
    private int wb_hour_pointer_color;
    //分针颜色
    private int wb_minute_pointer_color;
    //秒针颜色
    private int wb_second_pointer_color;
    //刻度线宽度
    private int wb_scale_width;
    //短刻度线长度
    private int wb_scale_short_length;
    //长刻度线长度
    private int wb_scale_long_length;
    //中心点的颜色
    private int centerPointColor;
    private int centerPointStokeWidth;

    private Point center;
    private Point pointScale_start;
    private Point pointScale_short_end;
    private Point pointScale_long_end;
    private Point pointText;
    private int radius;

    private Paint bacPaint;//背景画笔
    private Paint scalePaint;//刻度画笔
    private TextPaint textPaint;//文字画笔

    private Paint hourPaint;
    private Paint minPaint;
    private Paint secPaint;
    private Paint centerPaint;

    private RectF rectFHourPoint;
    private RectF rectFMinPoint;
    private RectF rectFSecPoint;


    public ClockView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTypedArray(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        center.x = w / 2;
        center.y = h / 2;
        radius = Math.min(center.x, center.y);//半径

        pointScale_start.x = center.x;
        pointScale_start.y = (int) wb_padding + center.y - radius;

        pointScale_short_end.x = center.x;
        pointScale_short_end.y = pointScale_start.y + wb_scale_short_length;

        pointScale_long_end.x = center.x;
        pointScale_long_end.y = pointScale_start.y + wb_scale_long_length;

        pointText.x = center.x;
        pointText.y = pointScale_long_end.y + DefaultValue.dpToPix(context, DefaultValue.TEXT_PADDING_SCALE);

        rectFHourPoint.top = center.y + wb_pointer_end_length - wb_hour_pointer_height;
        rectFHourPoint.bottom = center.y + wb_pointer_end_length;
        rectFHourPoint.left = center.x - wb_hour_pointer_width / 2;
        rectFHourPoint.right = center.x + wb_hour_pointer_width / 2;

        rectFMinPoint.top = center.y + wb_pointer_end_length - wb_minute_pointer_height;
        rectFMinPoint.bottom = center.y + wb_pointer_end_length;
        rectFMinPoint.left = center.x - wb_minute_pointer_width / 2;
        rectFMinPoint.right = center.x + wb_minute_pointer_width / 2;

        rectFSecPoint.top = center.y + wb_pointer_end_length - wb_second_pointer_height;
        rectFSecPoint.bottom = center.y + wb_pointer_end_length;
        rectFSecPoint.left = center.x - wb_second_pointer_width / 2;
        rectFSecPoint.right = center.x + wb_second_pointer_width / 2;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(DefaultValue.getMeasureSize(widthMeasureSpec, DefaultValue.DEFAULT_SIZE), DefaultValue.getMeasureSize(heightMeasureSpec, DefaultValue.DEFAULT_SIZE));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.save();
        canvas.drawColor(Color.GREEN);
        drawCircle(canvas);
        canvas.restore();
        drawCalibration(canvas);
        canvas.save();
        canvas.restore();
        drawText(canvas);
        canvas.save();
        canvas.restore();
        drawTimePointer(canvas);
        //刷新
        postInvalidateDelayed(1000);

    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.clockView);
        antiAlias = array.getBoolean(R.styleable.clockView_clock_antiAlias, true);
        smooth = array.getBoolean(R.styleable.clockView_smooth, false);
        background = array.getDrawable(R.styleable.clockView_background);
        wb_padding = array.getDimension(R.styleable.clockView_wb_padding, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_PADDING));
        wb_text_size = array.getDimension(R.styleable.clockView_wb_text_size, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_TEXT_SIZE));

        wb_hour_pointer_width = array.getDimension(R.styleable.clockView_wb_hour_pointer_width, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_HOUR_POINTER_WIDTH));
        wb_minute_pointer_width = array.getDimension(R.styleable.clockView_wb_hour_pointer_width, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_MINUTE_POINTER_WIDTH));
        wb_second_pointer_width = array.getDimension(R.styleable.clockView_wb_second_pointer_width, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_SECOND_POINTER_WIDTH));

        wb_hour_pointer_height = array.getDimension(R.styleable.clockView_wb_hour_pointer_height, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_HOUR_POINTER_HEIGHT));
        wb_minute_pointer_height = array.getDimension(R.styleable.clockView_wb_hour_pointer_height, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_MINUTE_POINTER_HEIGHT));
        wb_second_pointer_height = array.getDimension(R.styleable.clockView_wb_second_pointer_height, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_SECOND_POINTER_HEIGHT));

        wb_pointer_corner_radius = array.getDimension(R.styleable.clockView_wb_pointer_corner_radius, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_POINTER_CORNER_RADIUS));

        wb_pointer_end_length = array.getDimension(R.styleable.clockView_wb_pointer_end_length, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_POINTER_END_LENGTH));
        wb_scale_long_color = array.getColor(R.styleable.clockView_wb_pointer_end_length, Color.BLACK);
        wb_scale_short_color = array.getColor(R.styleable.clockView_wb_scale_short_color, Color.GRAY);
        wb_hour_pointer_color = array.getColor(R.styleable.clockView_wb_hour_pointer_color, Color.BLACK);
        wb_minute_pointer_color = array.getColor(R.styleable.clockView_wb_minute_pointer_color, Color.BLACK);
        wb_second_pointer_color = array.getColor(R.styleable.clockView_wb_minute_pointer_color, Color.RED);

        wb_scale_width = array.getDimensionPixelSize(R.styleable.clockView_wb_scale_width, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_SCALE_WIDTH));
        wb_scale_short_length = array.getDimensionPixelSize(R.styleable.clockView_wb_scale_short_length, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_SCALE_SHORT_LENGTH));
        wb_scale_long_length = array.getDimensionPixelSize(R.styleable.clockView_wb_scale_long_length, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_SCALE_LONG_LENGTH));
        wb_text_color = array.getColor(R.styleable.clockView_wb_text_color, Color.BLACK);

        centerPointColor = array.getColor(R.styleable.clockView_centerPointColor, Color.RED);
        centerPointStokeWidth = array.getColor(R.styleable.clockView_centerPointStokeWidth, DefaultValue.getDefaultValues(context, DefaultValue.DEFAULT_WB_CENTER_POINT_STOKE_WIDTH));

        array.recycle();
    }

    private void init() {
        center = new Point();
        pointScale_start = new Point();
        pointScale_short_end = new Point();
        pointScale_long_end = new Point();
        pointText = new Point();

        rectFHourPoint = new RectF();
        rectFMinPoint = new RectF();
        rectFSecPoint = new RectF();
        initPaint();
    }

    private void initPaint() {
        bacPaint = new Paint();
        bacPaint.setAntiAlias(antiAlias);
        bacPaint.setColor(Color.WHITE);
        bacPaint.setStyle(Paint.Style.FILL);

        scalePaint = new Paint();
        scalePaint.setAntiAlias(antiAlias);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setStrokeCap(Paint.Cap.SQUARE);

        textPaint = new TextPaint();
        textPaint.setColor(wb_text_color);
        textPaint.setAntiAlias(antiAlias);
        textPaint.setTextSize(wb_text_size);
        textPaint.setTextAlign(Paint.Align.CENTER);

        hourPaint = new Paint();
        hourPaint.setColor(wb_hour_pointer_color);
        hourPaint.setStyle(Paint.Style.FILL);

        minPaint = new Paint();
        minPaint.setColor(wb_minute_pointer_color);
        minPaint.setStyle(Paint.Style.FILL);

        secPaint = new Paint();
        secPaint.setColor(wb_second_pointer_color);
        secPaint.setStyle(Paint.Style.FILL);

        centerPaint = new Paint();
        centerPaint.setColor(centerPointColor);
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setStrokeWidth(centerPointStokeWidth);
        centerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 画表盘背景圆
     */
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius, bacPaint);
    }

    /**
     * 画刻度线
     */
    private void drawCalibration(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                scalePaint.setColor(wb_scale_long_color);
                scalePaint.setStrokeWidth(wb_scale_width);
                canvas.drawLine(pointScale_start.x, pointScale_start.y, pointScale_long_end.x, pointScale_long_end.y, scalePaint);
            } else {
                scalePaint.setColor(wb_scale_short_color);
                scalePaint.setStrokeWidth(wb_scale_width - 1);
                canvas.drawLine(pointScale_start.x, pointScale_start.y, pointScale_short_end.x, pointScale_short_end.y, scalePaint);
            }
            canvas.rotate(6, center.x, center.y);
        }

    }

    /**
     * 刻画数字
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < 12; i++) {
            canvas.rotate(30, center.x, center.y);
            canvas.drawText(String.valueOf(i + 1), pointText.x, pointText.y, textPaint);

        }
    }

    /**
     * 刻画指针
     */
    private void drawTimePointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        int angleHour = (hour % 12) * 360 / 12; //时针转过的角度
        int angleMinute = min * 360 / 60; //分针转过的角度
        int angleSecond = sec * 360 / 60; //秒针转过的角度

        canvas.save();
        //刻画时针
        canvas.rotate(angleHour, center.x, center.y);
        canvas.drawRoundRect(rectFHourPoint, wb_pointer_corner_radius, wb_pointer_corner_radius, hourPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(angleMinute, center.x, center.y);
        canvas.drawRoundRect(rectFMinPoint, wb_pointer_corner_radius, wb_pointer_corner_radius, minPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(angleSecond, center.x, center.y);
        canvas.drawRoundRect(rectFSecPoint, wb_pointer_corner_radius, wb_pointer_corner_radius, secPaint);
        canvas.restore();
        //刻画中心小圆点
        canvas.drawPoint(center.x, center.y, centerPaint);

    }
}
