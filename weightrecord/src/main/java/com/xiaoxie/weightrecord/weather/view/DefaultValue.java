package com.xiaoxie.weightrecord.weather.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by xcb on 2018/2/8.
 */

public class DefaultValue {
    public static final int DEFAULT_SIZE = 230;
    public static final int DEFAULT_WB_PADDING = 10;
    public static final int DEFAULT_WB_TEXT_SIZE = 20;
    public static final int DEFAULT_WB_HOUR_POINTER_WIDTH = 10;
    public static final int DEFAULT_WB_MINUTE_POINTER_WIDTH = 6;
    public static final int DEFAULT_WB_SECOND_POINTER_WIDTH = 2;
    public static final int DEFAULT_WB_HOUR_POINTER_HEIGHT = 150;
    public static final int DEFAULT_WB_MINUTE_POINTER_HEIGHT = 180;
    public static final int DEFAULT_WB_SECOND_POINTER_HEIGHT = 220;
    public static final int DEFAULT_WB_POINTER_CORNER_RADIUS = 10;
    public static final int DEFAULT_WB_POINTER_END_LENGTH = 30;
    public static final int DEFAULT_WB_SCALE_WIDTH = 2;
    public static final int DEFAULT_WB_SCALE_SHORT_LENGTH = 10;
    public static final int DEFAULT_WB_SCALE_LONG_LENGTH = 15;
    public static final int DEFAULT_WB_CENTER_POINT_STOKE_WIDTH = 20;
    public static final int TEXT_PADDING_SCALE = 18;//文字距离刻度的距离

    public static int dpToPix(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip > 0 ? 1 : -1));
    }

    public static int getDefaultValues(Context context, int defaultValue) {
        return dpToPix(context, defaultValue);
    }

    public static int getMeasureSize(int measureSpec, int defaultSize) {
        int result = defaultSize;
        Log.d("daddc", " defaultSize = " + result);
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        Log.d("daddc", " specSize = " + result);
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
            Log.d("daddc", "specMode == View.MeasureSpec.EXACTLY result = " + result);
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(specSize, result);
            Log.d("daddc", "specMode == View.MeasureSpec.AT_MOST result = " + result);
        }
        return result;
    }
}
