package com.xiaoxie.weightrecord.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * desc:sp数据存储
 * Created by xiaoxie on 2017/8/21.
 */
public class SharePrefenceUtils {
    public static final String CONFIG_NAME = "weight_record_config";
    public static final String KEY_IS_FIRST_LOAD = "is_first_load";

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }
}
