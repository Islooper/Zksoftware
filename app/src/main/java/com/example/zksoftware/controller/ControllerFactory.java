package com.example.zksoftware.controller;

import com.example.zksoftware.error.Error;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by looper on 2020/9/13.
 */
public class ControllerFactory {
    private static final Map<String, SensorController> identifyMap = new HashMap<>();

    static {
        identifyMap.put("mqtt", new Mqtt());
        identifyMap.put("service", new Service());
    }

    /**
     * 构造器
     * @param configIdentify : 配置参数
     * @return 识别抽象实例
     */
    public static SensorController createController(String configIdentify) {
        if (configIdentify == null || configIdentify.isEmpty()) {
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }
        SensorController sensorController = identifyMap.get(configIdentify);
        return sensorController;
    }
}
