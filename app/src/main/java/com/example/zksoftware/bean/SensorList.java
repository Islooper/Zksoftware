package com.example.zksoftware.bean;

/**
 * Created by looper on 2020/9/14.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 全局唯一传感器列表单列
 */
public class SensorList {

    private List<Sensor> sensors;

    private static volatile SensorList instance;

    private SensorList() {
        //防止外部实例化
        sensors  =new ArrayList<>();
    }


    public static SensorList getInstance() {
        // 同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
        synchronized (SensorList.class) {
            // 未初始化，则初始instance变量
            if (instance == null) {
                instance = new SensorList();
            }
        }
        return instance;
    }


    public void addSensor(Sensor sensor){
        sensors.add(sensor);
    }


    public List<Sensor> getList(){
        return sensors;
    }
}
