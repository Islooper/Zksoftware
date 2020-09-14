package com.example.zksoftware.utils;

import android.content.Intent;

import com.example.zksoftware.MyApplication;

import static com.example.zksoftware.MainActivity.BROADCAST_ACTION;

/**
 * Created by looper on 2020/9/14.
 */
public class BroadcastSender {
    Intent intent = new Intent();
    public void send(String name ,String value)
    {
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(name, value);
        MyApplication.getmContext().sendBroadcast(intent);
    }
}
