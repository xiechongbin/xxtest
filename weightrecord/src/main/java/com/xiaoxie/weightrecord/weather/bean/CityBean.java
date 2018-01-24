package com.xiaoxie.weightrecord.weather.bean;

/**
 * Created by xcb on 2018/1/12.
 * 城市实体类
 */

public class CityBean {
    //城市id
    private String cityId;
    //国家名称
    private String countryName;
    //城市名称
    private String name;
    //省份名称
    private String pName;
    //时区
    private String timezone;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
