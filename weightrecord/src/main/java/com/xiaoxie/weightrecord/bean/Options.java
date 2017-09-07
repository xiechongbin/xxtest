package com.xiaoxie.weightrecord.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * desc:
 * Created by xiaoxie on 2017/9/7.
 */
public class Options extends RealmObject {

    /**
     * 0 不选中 1 选中
     */
    @PrimaryKey
    private int id;

    private int dateStatus = 1;
    private int amWeightStatus;
    private int pmWeightStatus;
    private int nightWeightStatus;
    private int averageWeightStatus = 1;
    private int bodyFatStatus;
    private int internalOrgansFatStatus;//内脏脂肪
    private int muscleStatus;//筋肉
    private int boneStatus;//骨头
    private int bodyMoistureStatus;//身体水分
    private int heartRateStatus;//心率
    private int bmrStatus;
    private int bmiStatus;
    private int bicepsStatus;//二头肌
    private int neckStatus;//颈部
    private int waistStatus;//腰
    private int wristStatus;//腕部
    private int forearmStatus;//前臂
    private int buttocksStatus;//臀部
    private int bustStatus;//胸围
    private int thighStatus;//大腿
    private int abdomenStatus;//腹部
    private int chestStatus;//胸部
    private int dietStatus;//饮食 星级
    private int activityStatus;//活动 星级
    private int annotateStatus;//注释

    public int getNightWeightStatus() {
        return nightWeightStatus;
    }

    public void setNightWeightStatus(int nightWeightStatus) {
        this.nightWeightStatus = nightWeightStatus;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }

    public int getAmWeightStatus() {
        return amWeightStatus;
    }

    public void setAmWeightStatus(int amWeightStatus) {
        this.amWeightStatus = amWeightStatus;
    }

    public int getPmWeightStatus() {
        return pmWeightStatus;
    }

    public void setPmWeightStatus(int pmWeightStatus) {
        this.pmWeightStatus = pmWeightStatus;
    }

    public int getAverageWeightStatus() {
        return averageWeightStatus;
    }

    public void setAverageWeightStatus(int averageWeightStatus) {
        this.averageWeightStatus = averageWeightStatus;
    }

    public int getBodyFatStatus() {
        return bodyFatStatus;
    }

    public void setBodyFatStatus(int bodyFatStatus) {
        this.bodyFatStatus = bodyFatStatus;
    }

    public int getInternalOrgansFatStatus() {
        return internalOrgansFatStatus;
    }

    public void setInternalOrgansFatStatus(int internalOrgansFatStatus) {
        this.internalOrgansFatStatus = internalOrgansFatStatus;
    }

    public int getMuscleStatus() {
        return muscleStatus;
    }

    public void setMuscleStatus(int muscleStatus) {
        this.muscleStatus = muscleStatus;
    }

    public int getBoneStatus() {
        return boneStatus;
    }

    public void setBoneStatus(int boneStatus) {
        this.boneStatus = boneStatus;
    }

    public int getBodyMoistureStatus() {
        return bodyMoistureStatus;
    }

    public void setBodyMoistureStatus(int bodyMoistureStatus) {
        this.bodyMoistureStatus = bodyMoistureStatus;
    }

    public int getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(int heartRateStatus) {
        this.heartRateStatus = heartRateStatus;
    }

    public int getBmrStatus() {
        return bmrStatus;
    }

    public void setBmrStatus(int bmrStatus) {
        this.bmrStatus = bmrStatus;
    }

    public int getBmiStatus() {
        return bmiStatus;
    }

    public void setBmiStatus(int bmiStatus) {
        this.bmiStatus = bmiStatus;
    }

    public int getBicepsStatus() {
        return bicepsStatus;
    }

    public void setBicepsStatus(int bicepsStatus) {
        this.bicepsStatus = bicepsStatus;
    }

    public int getNeckStatus() {
        return neckStatus;
    }

    public void setNeckStatus(int neckStatus) {
        this.neckStatus = neckStatus;
    }

    public int getWaistStatus() {
        return waistStatus;
    }

    public void setWaistStatus(int waistStatus) {
        this.waistStatus = waistStatus;
    }

    public int getWristStatus() {
        return wristStatus;
    }

    public void setWristStatus(int wristStatus) {
        this.wristStatus = wristStatus;
    }

    public int getForearmStatus() {
        return forearmStatus;
    }

    public void setForearmStatus(int forearmStatus) {
        this.forearmStatus = forearmStatus;
    }

    public int getButtocksStatus() {
        return buttocksStatus;
    }

    public void setButtocksStatus(int buttocksStatus) {
        this.buttocksStatus = buttocksStatus;
    }

    public int getBustStatus() {
        return bustStatus;
    }

    public void setBustStatus(int bustStatus) {
        this.bustStatus = bustStatus;
    }

    public int getThighStatus() {
        return thighStatus;
    }

    public void setThighStatus(int thighStatus) {
        this.thighStatus = thighStatus;
    }

    public int getAbdomenStatus() {
        return abdomenStatus;
    }

    public void setAbdomenStatus(int abdomenStatus) {
        this.abdomenStatus = abdomenStatus;
    }

    public int getChestStatus() {
        return chestStatus;
    }

    public void setChestStatus(int chestStatus) {
        this.chestStatus = chestStatus;
    }

    public int getDietStatus() {
        return dietStatus;
    }

    public void setDietStatus(int dietStatus) {
        this.dietStatus = dietStatus;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getAnnotateStatus() {
        return annotateStatus;
    }

    public void setAnnotateStatus(int annotateStatus) {
        this.annotateStatus = annotateStatus;
    }
}
