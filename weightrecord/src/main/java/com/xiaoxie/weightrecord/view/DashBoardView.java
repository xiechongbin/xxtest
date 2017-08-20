package com.xiaoxie.weightrecord.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.xiaoxie.weightrecord.R;

/**
 * desc:仪表盘view
 * Created by xiaoxie on 2017/8/14.
 */
public class DashBoardView extends LinearLayout {
    private float WIDTH;//可见区域宽度
    private float HEIGHT;//可见区域高度

    private float RECT_WHTDH;//矩形宽
    private float RECT_HEIGHT;//矩形高
    private Canvas canvas;


    /**
     * 第一个方法，一般我们这样使用时会被调用，View view = new View(context);
     */
    public DashBoardView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    /**
     * 第二个方法，当我们在xml布局文件中使用View时，会在inflate布局时被调用
     *
     * @param context
     * @param attrs
     */
    public DashBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
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
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        WIDTH = getWidth();
        HEIGHT = getHeight();
        canvas.drawColor(getResources().getColor(R.color.color_27ae60));//绘制背景
        Paint paint_outside = new Paint();
        paint_outside.setColor(getResources().getColor(R.color.color_6bc792));
        paint_outside.setStrokeWidth(8);
        paint_outside.setStrokeCap(Paint.Cap.ROUND);
        paint_outside.setStrokeJoin(Paint.Join.ROUND);
        paint_outside.setAntiAlias(true);//抗锯齿
        paint_outside.setStyle(Paint.Style.STROKE);

        RectF rectF = new RectF(WIDTH / 6 + 30, 3 + 30, (WIDTH / 3) * 2 + WIDTH / 6 - 30, (WIDTH / 3) * 2 - 30);//绘制矩形区域
        canvas.drawArc(rectF, 150, 240, false, paint_outside);//绘制外圈

        Paint paint_inner = new Paint(paint_outside);
        paint_inner.setColor(getResources().getColor(R.color.color_6bc792));
        paint_inner.setStrokeWidth(13);
        RectF rectF_inner = new RectF(WIDTH / 6 + 50, 53, (WIDTH / 3) * 2 - 50 + WIDTH / 6, (WIDTH / 3) * 2 - 50);//绘制矩形区域
        canvas.drawArc(rectF_inner, 150, 240, false, paint_inner);//绘制内圈
        canvas.save();

        canvas.translate(WIDTH / 2, WIDTH / 3);//将画布移到中心
        float y = 199;
        int count = 60; //总刻度数
        Paint paint_scale = new Paint();
        paint_scale.setStrokeWidth(2);
        paint_scale.setColor(getResources().getColor(R.color.color_aadfce));

        Paint paint_text = new Paint();
        paint_text.setTextSize(25);
        paint_text.setColor(Color.WHITE);
        for (int i = 0; i < count; i++) {
            if (i > 9 && i < 51) {
                if (i % 5 == 0) {
                    canvas.drawText(String.valueOf(i), -10, y - 15, paint_text);
                }
                canvas.drawLine(0, y, 0, y + 12f, paint_scale);//绘制刻度线
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }
        canvas.restore();

    }

    public void setProgess(float value) {
        RectF rectF = new RectF(WIDTH / 6 + 30, 3 + 30, (WIDTH / 3) * 2 + WIDTH / 6 - 30, (WIDTH / 3) * 2 - 30);//绘制矩形区域
        Paint paint_outside = new Paint();
        paint_outside.setColor(getResources().getColor(R.color.color_2ecc71));
        paint_outside.setStrokeWidth(8);
        paint_outside.setStrokeCap(Paint.Cap.ROUND);
        paint_outside.setStrokeJoin(Paint.Join.ROUND);
        paint_outside.setAntiAlias(true);//抗锯齿
        paint_outside.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF, 150, (value/60)*360, false, paint_outside);//绘制外圈
        this.invalidate();
    }

}
