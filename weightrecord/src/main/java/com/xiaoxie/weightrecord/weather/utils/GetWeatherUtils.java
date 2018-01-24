package com.xiaoxie.weightrecord.weather.utils;

import android.location.Location;

import com.xcb.network.okhttp.OkHttpUtils;
import com.xcb.network.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xcb on 2018/1/12.
 */

public class GetWeatherUtils {
    /**
     * 获取天气预警
     */
    public static void getAlertWeatherInfo(Location location, StringCallback callback) {
        if (location == null) {
            return;
        }
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("lat", String.valueOf(location.getLatitude()));
        body.put("lon", String.valueOf(location.getLongitude()));
        body.put("token", AddressConstant.TOKEN_WEATHER_ALERT);
        OkHttpUtils.post().url(AddressConstant.URL_WEATHER_ALERT).headers(getHeader()).params(query).params(body).build().execute(callback);
    }

    /**
     * 获取限行数据
     */
    public static void getLimitInfo(Location location, StringCallback callback) {
        if (location == null) {
            return;
        }
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("lat", String.valueOf(location.getLatitude()));
        body.put("lon", String.valueOf(location.getLongitude()));
        body.put("token", AddressConstant.TOKEN_LIMIT);
        OkHttpUtils.post().url(AddressConstant.URL_LIMIT).headers(getHeader()).params(query).params(body).build().execute(callback);
    }

    /**
     * 获取6天精简预报
     */
    public static void get6DaysInfo(Location location, StringCallback callback) {
       /* if (location == null) {
            return;
        }*/
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("lat", "23.0985134834");
        body.put("lon", "113.3666820008");
        body.put("token", AddressConstant.TOKEN_BRIEF_FORECAST_6DAYS);
        OkHttpUtils.post().url(AddressConstant.URL_BRIEF_FORECAST_6DAYS).headers(getHeader()).params(query).params(body).build().execute(callback);
    }

    /**
     * 获取天气预警
     */
    public static void getBriefConditionInfo(Location location, StringCallback callback) {
        if (location == null) {
            return;
        }
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("lat", String.valueOf(location.getLatitude()));
        body.put("lon", String.valueOf(location.getLongitude()));
        body.put("token", AddressConstant.TOKEN_BRIEF_CONDITION);
        OkHttpUtils.post().url(AddressConstant.URL_BRIEF_CONDITION).headers(getHeader()).params(query).params(body).build().execute(callback);
    }

    /**
     * 空气质量
     */
    public static void getAQIInfo(Location location, StringCallback callback) {
        if (location == null) {
            return;
        }
        Map<String, String> query = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("lat", String.valueOf(location.getLatitude()));
        body.put("lon", String.valueOf(location.getLongitude()));
        body.put("token", AddressConstant.TOKEN_AQI);
        OkHttpUtils.post().url(AddressConstant.URL_AQI).headers(getHeader()).params(query).params(body).build().execute(callback);
    }

    public static Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + AddressConstant.APP_CODE);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        return headers;
    }
}
