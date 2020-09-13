package com.example.zksoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity{

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
    }


    /**
     * 初始化SurfaceHolder回调
     */
    private void initSurfaceHolder()
    {
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


    private void findId()
    {
        camera1 = findViewById(R.id.sv_camera1);
        camera2 = findViewById(R.id.sv_camera2);
        camera3 = findViewById(R.id.sv_camera3);
        camera4 = findViewById(R.id.sv_camera4);

        sensorList = findViewById(R.id.sensor_list);
    }


    private void initWidget()
    {

    }

}