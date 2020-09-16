package com.example.zksoftware.controller;

import com.example.zksoftware.bean.Sensor;

/**
 * Created by looper on 2020/9/13.
 */
public abstract class SensorController {
    public abstract void controller (String devId ,String sensorId , int cmd , int param );
}
