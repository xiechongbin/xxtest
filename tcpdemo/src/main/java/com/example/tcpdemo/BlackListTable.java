package com.example.tcpdemo;

import org.litepal.crud.DataSupport;

/**
 * 车辆黑名单
 * Created by xcb on 2017/11/7.
 */

public class BlackListTable extends DataSupport {

    private String carId;

    private String carPlate;

    private String carColor;

    private String surveillanceType;

    private String carType;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getSurveillanceType() {
        return surveillanceType;
    }

    public void setSurveillanceType(String surveillanceType) {
        this.surveillanceType = surveillanceType;
    }

}
