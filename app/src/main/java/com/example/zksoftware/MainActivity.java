package com.example.zksoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.zksoftware.adapter.SensorAdapter;
import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.bean.SensorList;
import com.example.zksoftware.controller.ControllerFactory;
import com.example.zksoftware.controller.SensorController;
import com.example.zksoftware.error.Error;
import com.example.zksoftware.service.SensorService;
import com.example.zksoftware.utils.HttpUtils;
import com.example.zksoftware.utils.SensorConfig;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 四台监控
     */
    private SurfaceView camera1;
    private SurfaceView camera2;
    private SurfaceView camera3;
    private SurfaceView camera4;
    private SurfaceHolder holder1;
    private SurfaceHolder holder2;
    private SurfaceHolder holder3;
    private SurfaceHolder holder4;

    /**
     * 传感器列表
     */
    RecyclerView sensorList;


    /**
     * 广播
     */
    public static final String BROADCAST_ACTION = "com.example.corn";
    private BroadcastReceiver mBroadcastReceiver;

    /**
     * 控制类传感器参数
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        findId();

        // 初始化holder
        initSurfaceHolder();
        // 初始化控件
        initWidget();

        // 获取所有的传感器
        getAllSensors();

        // 广播接受
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void getAllSensors() {
        try {
            HttpUtils.getAllSensors(SensorConfig.type, SensorConfig.place ,1);
        } catch (IllegalArgumentException ignored) {

        }
    }


    /**
     * 初始化SurfaceHolder回调
     */
    private void initSurfaceHolder() {
        holder1 = camera1.getHolder();
        holder1.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });


        holder2 = camera2.getHolder();
        holder2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        holder3 = camera3.getHolder();
        holder3.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        holder4 = camera4.getHolder();
        holder4.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }


    private void findId() {
        camera1 = findViewById(R.id.sv_camera1);
        camera2 = findViewById(R.id.sv_camera2);
        camera3 = findViewById(R.id.sv_camera3);
        camera4 = findViewById(R.id.sv_camera4);

        sensorList = findViewById(R.id.sensor_list);
    }


    SensorController controller;
    /**
     * 初始化控件
     */
    private void initWidget() {
        // 初始化控制的实现类
        controller = ControllerFactory.createController("service");

        // 启动读取传感器列表服务
        onStartService();
    }


    private void onStartService(){
        Intent intent = new Intent(MainActivity.this, SensorService.class);
        startService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播
        unregisterReceiver(mBroadcastReceiver);
    }


    /**
     * 广播接受类
     */

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String value = intent.getStringExtra("sensors");

            SensorList sensorList = SensorList.getInstance();
            // 接受数据成功数据
            assert value != null;
            switch (value){
                case "ok":
                    // 渲染传感器列表
                case "refresh":
                    packageSensors(sensorList.getList());
                    break;
            }

        }
    }


    /**
     * 渲染洁面传感器列表
     * @param list : 传感器列表
     */
    public void packageSensors(List<Sensor> list){

        if (list == null){
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }
        SensorAdapter adapter = new SensorAdapter(getApplicationContext() , list);
        sensorList.setAdapter(adapter);
        sensorList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnItemClickListener(new SensorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Sensor sensor, boolean isOpen) {
                // 判断开关状态 发送控制指令
                if (!isOpen) {
                    // 发送关闭的指令
                    controller.controller(sensor.getDevid() , sensor.getSensorId() , 0 , 1);
                }else {
                    // 发送打开的指令
                    controller.controller(sensor.getDevid() , sensor.getSensorId() , 1 , 1);
                }
            }
        });
        adapter.notifyItemRangeInserted(0 , list.size());
    }
}