package com.xiaoxie.weightrecord.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;

/**
 * desc:sp数据存储
 * Created by xiaoxie on 2017/8/21.
 */
public class SharePrefenceUtils {
    public static final String CONFIG_NAME = "weight_record_config";
    public static final String KEY_IS_FIRST_LOAD = "is_first_load";
    public static final String KEY_INITIAL_WEIGHT = "initial_weight";
    public static final String KEY_INITIAL_HEIGHT = "initial_height";
    public static final String KEY_INITIAL_BMI = "initial_bmi";
    public static final String KEY_SEX = "sex";
    public static final String KEY_BIRTHDAY = "birthday";

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
    public static SharedPreferences getSharePrefenses(Context context){
        return context.getSharedPreferences(CONFIG_NAME,Context.MODE_PRIVATE);
    }
}
