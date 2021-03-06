package com.xiaoxie.weightrecord.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
    public static final String KEY_HAS_PASSWORD = "has_password";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EXPORT_BMI_AUTO = "export_bmi_auto";//自动生成mbi
    public static final String KEY_EXPORT_BMR_AUTO = "export_bmr_auto";//自动生成mbr
    public static final String KEY_AUDIO = "audio";//声音效果
    public static final String KEY_WEIGHT_UNIT = "weight_unit";//体重单位
    public static final String KEY_HEIGHT_UNIT = "height_unit";//身高单位
    public static final String KEY_WEIGHT_INPUT_TYPE = "weight_input_type";//输入方式
    public static final String KEY_FIRST_DAY_OF_WEEK = "first_day_of_week";//一周的第一天
    public static final String KEY_LANGUAGE = "language";//一周的第一天
    public static final String KEY_REMINDER_REPEAT = "reminder_repeat";//重复提醒
    public static final String KEY_REMINDER_ONTTIME = "reminder_onttime";//单次提醒
    public static final String KEY_REMINDER_WHICH_DAY = "reminder_which_day";//哪几天提醒
    public static final String KEY_REMINDER_TIME = "reminder_time";//具体提醒的时间
    public static final String KEY_PLAN_START_TIME = "plan_start_time";//计划开始的时间
    public static final String KEY_ENABLE_RESET_PLAN = "enable_reset_plan";//是否允许重置计划
    public static final String KEY_TARGET_WEIGHT = "target_weight";//目标体重
    public static final String KEY_TARGET_DATE = "target_date";//目标日期

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }
}
