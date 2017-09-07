package com.xiaoxie.weightrecord.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * desc:身体各项数据封装类
 * Created by xiaoxie on 2017/9/7.
 */
public class BodyData extends RealmObject {
    @PrimaryKey
    private String date;//日期

    private float amWeight;
    private float pmWeight;
    private float nightWeight;
    private float averageWeight;
    private float bodyFat;
    private float internalOrgansFat;//内脏脂肪
    private float muscle;//筋肉
    private float bone;//骨头
    private float bodyMoisture;//身体水分
    private float heartRate;//心率
    private float bmr;
    private float bmi;
    private float biceps;//二头肌
    private float neck;//颈部
    private float waist;//腰
    private float wrist;//腕部
    private float forearm;//前臂
    private float buttocks;//臀部
    private float bust;//胸围
    private float thigh;//大腿
    private float abdomen;//腹部
    private float chest;//胸部
    private int diet;//饮食 星级
    private int activity;//活动 星级
    private String annotate;//注释

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmWeight() {
        return amWeight;
    }

    public void setAmWeight(float amWeight) {
        this.amWeight = amWeight;
    }

    public float getPmWeight() {
        return pmWeight;
    }

    public void setPmWeight(float pmWeight) {
        this.pmWeight = pmWeight;
    }
    
    public float getNightWeight() {
        return nightWeight;
    }

    public void setNightWeight(float nightWeight) {
        this.nightWeight = nightWeight;
    }

    public float getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(float averageWeight) {
        this.averageWeight = averageWeight;
    }

    public float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public float getInternalOrgansFat() {
        return internalOrgansFat;
    }

    public void setInternalOrgansFat(float internalOrgansFat) {
        this.internalOrgansFat = internalOrgansFat;
    }

    public float getMuscle() {
        return muscle;
    }

    public void setMuscle(float muscle) {
        this.muscle = muscle;
    }

    public float getBone() {
        return bone;
    }

    public void setBone(float bone) {
        this.bone = bone;
    }

    public float getBodyMoisture() {
        return bodyMoisture;
    }

    public void setBodyMoisture(float bodyMoisture) {
        this.bodyMoisture = bodyMoisture;
    }

    public float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(float heartRate) {
        this.heartRate = heartRate;
    }

    public float getBmr() {
        return bmr;
    }

    public void setBmr(float bmr) {
        this.bmr = bmr;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getBiceps() {
        return biceps;
    }

    public void setBiceps(float biceps) {
        this.biceps = biceps;
    }

    public float getNeck() {
        return neck;
    }

    public void setNeck(float neck) {
        this.neck = neck;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getWrist() {
        return wrist;
    }

    public void setWrist(float wrist) {
        this.wrist = wrist;
    }

    public float getForearm() {
        return forearm;
    }

    public void setForearm(float forearm) {
        this.forearm = forearm;
    }

    public float getButtocks() {
        return buttocks;
    }

    public void setButtocks(float buttocks) {
        this.buttocks = buttocks;
    }

    public float getBust() {
        return bust;
    }

    public void setBust(float bust) {
        this.bust = bust;
    }

    public float getThigh() {
        return thigh;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public float getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(float abdomen) {
        this.abdomen = abdomen;
    }

    public float getChest() {
        return chest;
    }

    public void setChest(float chest) {
        this.chest = chest;
    }

    public int getDiet() {
        return diet;
    }

    public void setDiet(int diet) {
        this.diet = diet;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getAnnotate() {
        return annotate;
    }

    public void setAnnotate(String annotate) {
        this.annotate = annotate;
    }
}
