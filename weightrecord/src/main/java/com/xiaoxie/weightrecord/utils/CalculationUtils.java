package com.xiaoxie.weightrecord.utils;

import android.content.Context;
import android.text.TextUtils;
import android.transition.CircularPropagation;
import android.util.Log;

import java.security.PublicKey;
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
    public static float calculateBodyFat(Context context, float currentWeight) {
        float fat = 0f;
        float bmi = 0;
        float height = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_HEIGHT, 0);
        if (currentWeight <= 0) {
            bmi = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_BMI, 0);
        } else {
            bmi = calculateBMI(currentWeight, height);
        }

        String sex = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, "");
        if (TextUtils.isEmpty(sex)) {
            return 0;
        }
        int sexNumber = sex.contains("男") ? 1 : 0;
        fat = (float) ((1.20f * bmi) + (0.23 * calculateAge(context)) - (10.8 * sexNumber) - 5.4);
        fat = (float) (Math.round(fat * 100)) / 100;
        return fat;
    }

    /**
     * 计算BMR
     * 计算公式   女: BMR = 655 + ( 9.6 x 体重kg ) + ( 1.8 x 身高cm ) - ( 4.7 x 年龄years )
     * 男: BMR = 66 + ( 13.7 x 体重kg ) + ( 5 x 身高cm ) - ( 6.8 x 年龄years )
     */
    public static float calculateBmr(Context context, float currentWeight) {
        String sex = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, "");
        if (currentWeight <= 0) {
            currentWeight = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_WEIGHT, 0);
        }
        if (TextUtils.isEmpty(sex)) {
            return 0;
        }
        float bmr = 0;
        float height = SharePrefenceUtils.getFloat(context, SharePrefenceUtils.KEY_INITIAL_HEIGHT, 0);
        float age = calculateAge(context);
        if (sex.contains("男")) {
            bmr = (float) (66 + (13.7f * currentWeight) + (5 + height) - (age * 6.8));
            bmr = (float) (Math.round(bmr * 100)) / 100;
        } else {
            bmr = (float) (655 + (9.6f * currentWeight) + (1.8 + height) - (age * 4.7));
            bmr = (float) (Math.round(bmr * 100)) / 100;
        }
        return bmr;
    }

    /**
     * 通过身体数据测量出身体脂肪含量
     * 公式： 参数 a = 腰围(cm) x 0.74
     * 参数b(女性) = 总体重(kg) x 0.082 + 34.89
     * 参数b (男性)= 总体重(kg) x 0.082 + 44.74
     * 身体脂肪总重量(公斤) = a – b
     */

    public static float calculateBodyFatFromBodyData(Context context, float waist, float weight) {
        String sex = SharePrefenceUtils.getString(context, SharePrefenceUtils.KEY_SEX, "");
        if (TextUtils.isEmpty(sex)) {
            return 0;
        }
        float a = (float) (waist * 0.74);
        float b = 0;
        if (sex.contains("男")) {
            b = (float) (weight * 0.082 + 44.74);
        } else {
            b = (float) (weight * 0.082 + 34.89);
        }
        return (float) (Math.round(((a - b) / weight * 100) * 100)) / 100;
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

    public static long dateToLong(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String year = "", month = "", day = "";
        int indexYear = 0;
        int indexMonth = 0;
        if (str.contains("年")) {
            indexYear = str.indexOf("年");
            year = str.substring(0, indexYear);
        }
        if (str.contains("月")) {
            indexMonth = str.indexOf("月");
            month = str.substring(indexYear + 1, indexMonth);
            if (Integer.valueOf(month) < 10) {
                month = "0" + month;
            }
        }
        if (str.contains("日")) {
            day = str.substring(indexMonth + 1, str.length() - 1);
            if (Integer.valueOf(day) < 10) {
                day = "0" + day;
            }
        }
        return Long.valueOf(year + month + day);
    }
}
