package com.example.zksoftware.utils;

import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.example.zksoftware.MainActivity;
import com.example.zksoftware.MyApplication;
import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.bean.SensorList;
import com.example.zksoftware.error.Error;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import static com.example.zksoftware.MainActivity.BROADCAST_ACTION;

/**
 * Creater by looper on 2020/9/4.
 */
public class HttpUtils {

    public static void getAllSensors(String types, final String place) {
        if (types.isEmpty()) {
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }
        OkHttpUtils.get().url(Url.URL_DEV + "placeAndTypeSelectSensorData.do")
                .addParams("place", place)
                .addParams("types", types)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        JSONObject data = JSONObject.parseObject(response);
                        int code = data.getInteger("resultCode");
                        if (code == 0) {
                            String sensors = data.getString("result");
                            JsonParser parser = new JsonParser();
                            JsonArray jsonArray = parser.parse(sensors).getAsJsonArray();
                            Gson gson = new Gson();
                            SensorList sensorList = SensorList.getInstance();
                            for (JsonElement sensor : jsonArray) {
                                Sensor sensorDo = gson.fromJson(sensor, Sensor.class);
                                sensorList.addSensor(sensorDo);
                            }

                            // 发送数据成功广播
                            BroadcastSender sender = new BroadcastSender();
                            sender.send("sensors","ok");
                        }

                    }
                });
    }
}
