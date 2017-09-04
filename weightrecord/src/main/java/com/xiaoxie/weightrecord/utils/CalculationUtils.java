package com.xiaoxie.weightrecord.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * desc:健康数据计算工具类
 * Created by xiaoxie on 2017/9/1.
 */
public class CalculationUtils {
    /**
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
     * 计算bmi公式
     */

    public static float calculateBMI(float weight, float height) {
        height = height / 100;
        float bmi = weight / (height * height);
        Log.d("bmi", "weight = " + weight + ">>height = " + height + ">>bmi = " + bmi);
        return bmi;
    }

    /**
     * 计算体脂
     */
    public static float calulateBodyFat() {
        return 0f;
    }
}
