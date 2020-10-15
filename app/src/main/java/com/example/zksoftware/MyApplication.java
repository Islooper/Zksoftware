package com.example.zksoftware;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.videogo.openapi.EZOpenSDK;
import com.xuexiang.xui.XUI;

/**
 * Created by looper on 2020/9/7.
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    String appKey = "a550895fa82d400aaf75b3d3e3f814a7";

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

        mContext = this;

        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true);
        /** * 设置是否支持P2P取流,详见api */
        EZOpenSDK.enableP2P(false);

        /** * APP_KEY请替换成自己申请的 */
        EZOpenSDK.initLib(this, appKey);

    }

    public static Context getmContext() {
        return mContext;
    }
}
