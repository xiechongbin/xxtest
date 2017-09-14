package com.xiaoxie.weightrecord.utils;

import android.content.Context;
import android.text.TextUtils;
import android.transition.CircularPropagation;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * desc:健康数据计算工具类
 * Created by xiaoxie on 2017/9/1.
 */
public class CalculationUtils {
    /**
     * 体重范围 标准身高=身高-80）*70% （男）
     * 体重范围 标准身高=身高-70）*60% （女）
     * 体重范围  标准身高上下浮动10%-15%
     *
     * @param context 上下文
     * @return 标准体重范围 0 最小值 1 标准值 2 最大值 3 当前值
     */
    public static float[] calculateNormalWeight(Context context) {
        float normalWeight = 0;
        float minWeight = 0;
        float maxWeight = 0;
        float[] floats = new float[4];
        float height = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_HEIGHT, 0);
        float current = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_WEIGHT, 0);
        String weight_unit = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_WEIGHT_UNIT, "kg");
        String height_unit = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_HEIGHT_UNIT, "cm");
        String sex = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, "");
        if (TextUtils.isEmpty(sex)) {
            return null;
        }
        if (height > 0) {

            if (height_unit.equals("in")) {
                height = height * 2.54f;//将in单位转换为cm
            }
            if (sex.contains("男")) {
                normalWeight = (float) ((height - 80) * 0.7);
                minWeight = (float) (normalWeight - normalWeight * 0.15);
                maxWeight = (float) (normalWeight + normalWeight * 0.15);

            } else if (sex.contains("女")) {
                normalWeight = (float) ((height - 70) * 0.6);
                minWeight = (float) (normalWeight - normalWeight * 0.15);
                maxWeight = (float) (normalWeight + normalWeight * 0.15);
            }
        }
        if (weight_unit.equals("lb")) {
            floats[0] = (float) (Math.round(minWeight * 0.4535924 * 100)) / 100;//保留两位小数
            floats[1] = (float) (Math.round(normalWeight * 0.4535924 * 100)) / 100;
            floats[2] = (float) (Math.round(maxWeight * 0.4535924 * 100)) / 100;
            floats[3] = (float) (Math.round(current * 0.4535924 * 100)) / 100;
        } else if (weight_unit.equals("st")) {
            floats[0] = (float) (Math.round(minWeight * 6.35029318 * 100)) / 100;
            floats[1] = (float) (Math.round(normalWeight * 6.35029318 * 100)) / 100;
            floats[2] = (float) (Math.round(maxWeight * 6.35029318 * 100)) / 100;
            floats[3] = (float) (Math.round(current * 6.35029318 * 100)) / 100;
        } else {
            floats[0] = (float) (Math.round(minWeight * 100)) / 100;
            floats[1] = (float) (Math.round(normalWeight * 100)) / 100;
            floats[2] = (float) (Math.round(maxWeight * 100)) / 100;
            floats[3] = (float) (Math.round(current * 100)) / 100;

        }
        return floats;
    }

    /**
     * 计算bmi公式  BMI = 體重(公斤) / 身高2(公尺2)
     */

    public static float calculateBMI(float weight, float height) {
        height = height / 100;
        float bmi = weight / (height * height);
        Log.d("bmi", "weight = " + weight + ">>height = " + height + ">>bmi = " + bmi);
        return bmi;
    }

    /**
     * 计算体脂
     * 体脂% = (1.20 × BMI) + (0.23 × 年龄) − (10.8 × 性别) − 5.4
     * 男性性别为1，女性为0。
     */
    public static float calculateBodyFat(Context context) {
        float fat = 0f;
        float bmi = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_BMI, 0);
        String sex = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, "");
        if (TextUtils.isEmpty(sex)) {
            return 0;
        }
        int sexNumber = sex.contains("男") ? 1 : 0;
        fat = (float) ((1.20f * bmi) + (0.23 * calculateAge(context)) - (10.8 * sexNumber) - 5.4);
        return fat;
    }

    /**
     * 肥胖状态
     */
    public static String getBmiConclusion(float bmi) {
        if (bmi <= 18.5) {
            return "过轻";
        } else if (bmi > 18.5 && bmi <= 24) {
            return "正常";
        } else if (bmi > 24 && bmi <= 27) {
            return "轻度肥胖";
        } else if (bmi > 27 && bmi <= 30) {
            return "中度肥胖";
        } else if (bmi > 20 && bmi <= 35) {
            return "重度肥胖";
        } else {
            return "无药可救";
        }
    }

    /**
     * 计算年龄
     */
    public static int calculateAge(Context context) {
        String birthday = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_BIRTHDAY, "");
        int currentYear = 0;
        int birthdayYear = 0;
        int currentMonth = 0;
        int birthdayMonth = 0;
        int diffYear;
        int diffMonth;
        if (TextUtils.isEmpty(birthday)) {
            return 0;
        }
        birthdayYear = Integer.valueOf(birthday.substring(0, birthday.indexOf("年")));
        birthdayMonth = Integer.valueOf(birthday.substring(birthday.indexOf("年") + 1, birthday.indexOf("月")));
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);
        if (currentYear < birthdayYear) {
            return 0;
        }
        diffYear = currentYear - birthdayYear;
        if (currentMonth > birthdayMonth) {
            diffMonth = currentMonth - birthdayMonth;
        } else {
            diffMonth = birthdayMonth - currentMonth;
        }
        if (diffMonth >= 6) {
            return diffYear + 1;
        } else {
            return diffYear;
        }
    }
}
