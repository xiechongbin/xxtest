package com.xiaoxie.weightrecord.utils;

import android.util.Log;

import com.xiaoxie.weightrecord.bean.BodyData;

/**
 * Created by gt on 2017/9/16.
 */
public class LogUtils {
    public static void printBodyData(String tag, String str, BodyData bodyData) {
        String s = new StringBuilder().append(str)
                .append("date>>>").append(bodyData.getDate()).append("\n")
                .append("amWeight>>>").append(bodyData.getAmWeight()).append("\n")
                .append("pmWeight>>>").append(bodyData.getPmWeight()).append("\n")
                .append("nightWeight>>>").append(bodyData.getNightWeight()).append("\n")
                .append("averageWeight>>>").append(bodyData.getAverageWeight()).append("\n")
                .append("bodyFat>>>").append(bodyData.getBodyFat()).append("\n")
                .append("internalOrgansFat>>>").append(bodyData.getInternalOrgansFat()).append("\n")
                .append("muscle>>>").append(bodyData.getMuscle()).append("\n")
                .append("bone>>>").append(bodyData.getBone()).append("\n")
                .append("bodyMoisture>>>").append(bodyData.getBodyMoisture()).append("\n")
                .append("heartRate>>>").append(bodyData.getHeartRate()).append("\n")
                .append("bmr>>>").append(bodyData.getBmr()).append("\n")
                .append("bmi>>>").append(bodyData.getBmi()).append("\n")
                .append("biceps>>>").append(bodyData.getBiceps()).append("\n")
                .append("neck>>>").append(bodyData.getNeck()).append("\n")
                .append("waist>>>").append(bodyData.getWaist()).append("\n")
                .append("wrist>>>").append(bodyData.getWrist()).append("\n")
                .append("forearm>>>").append(bodyData.getForearm()).append("\n")
                .append("buttocks>>>").append(bodyData.getButtocks()).append("\n")
                .append("bust>>>").append(bodyData.getBust()).append("\n")
                .append("thigh>>>").append(bodyData.getThigh()).append("\n")
                .append("abdomen>>>").append(bodyData.getAbdomen()).append("\n")
                .append("chest>>>").append(bodyData.getChest()).append("\n")
                .append("diet>>>").append(bodyData.getDiet()).append("\n")
                .append("activity>>>").append(bodyData.getActivity()).append("\n")
                .append("annotate>>>").append(bodyData.getAnnotate()).append("\n").toString();

        Log.d(tag, s);
    }
}
