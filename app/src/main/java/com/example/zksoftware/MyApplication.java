package com.example.zksoftware;

import android.app.Application;

import com.xuexiang.xui.XUI;

/**
 * Created by looper on 2020/9/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
    }
}
