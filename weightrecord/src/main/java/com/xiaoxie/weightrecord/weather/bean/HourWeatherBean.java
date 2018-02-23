package com.xiaoxie.weightrecord.weather.bean;

/**
 * Created by xcb on 2018/2/11.
 * 小时天气实体
 */

public class HourWeatherBean {
    private String time;
    private String temperatureRange;
    private String weather;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
