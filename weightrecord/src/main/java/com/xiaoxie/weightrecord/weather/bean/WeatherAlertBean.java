package com.xiaoxie.weightrecord.weather.bean;

/**
 * Created by xcb on 2018/1/12.
 * 天气预警实体类
 */

public class WeatherAlertBean extends CityBean {

    //内容
    private String alertContent;
    //infoid
    private String infoId;
    //等级
    private String level;
    //名称
    private String alertName;
    //发布时间
    private String pub_time;
    //标题
    private String alertTitle;
    //类型
    private String alertType;

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}
