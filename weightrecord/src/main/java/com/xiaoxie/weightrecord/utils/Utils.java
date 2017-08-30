package com.xiaoxie.weightrecord.utils;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * desc:
 * Created by xiaoxie on 2017/8/29.
 */
public class Utils {
    /**
     * 获取颜色
     */
    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }
}
