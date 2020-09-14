package com.example.zksoftware;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xui.XUI;

/**
 * Created by looper on 2020/9/7.
 */
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

        mContext = this;
    }

    public static Context getmContext(){
        return mContext;
    }
}
