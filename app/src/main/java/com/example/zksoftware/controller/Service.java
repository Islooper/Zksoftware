package com.example.zksoftware.controller;

import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.utils.HttpUtils;

/**
 * Created by looper on 2020/9/13.
 */

// TODO 完成该实现类
public class Service extends SensorController {
    @Override
    public void controller(String devId ,String sensorId , int cmd , int param ) {
        HttpUtils.controlSensor(devId , sensorId , cmd , param);
    }
}
