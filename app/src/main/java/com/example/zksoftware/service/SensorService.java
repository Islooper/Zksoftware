package com.example.zksoftware.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.zksoftware.utils.HttpUtils;
import com.example.zksoftware.utils.SensorConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by looper on 2020/9/16.
 */
public class SensorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Log.e("before service http" , "123");
                getSensorStatus();
            }
        }, 1000,1500);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    public void getSensorStatus(){
        HttpUtils.getAllSensors(SensorConfig.type , SensorConfig.place , 2);

    }
}
