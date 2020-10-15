package com.example.zksoftware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dlong.rep.dlroundmenuview.DLRoundMenuView;
import com.dlong.rep.dlroundmenuview.Interface.OnMenuClickListener;
import com.example.zksoftware.adapter.SensorAdapter;
import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.bean.SensorList;
import com.example.zksoftware.controller.ControllerFactory;
import com.example.zksoftware.controller.SensorController;
import com.example.zksoftware.error.Error;
import com.example.zksoftware.service.SensorService;
import com.example.zksoftware.utils.HttpUtils;
import com.example.zksoftware.utils.SensorConfig;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    LinearLayout right;

    DLRoundMenuView control;

    ShadowButton jia;
    ShadowButton jian;


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
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        findId();


        // 初始化holder
        initSurfaceHolder();

        // 初始化摄像头
        initCamera();
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


    String uuid = "E53435412";
    String accessToken = "at.5tyle7tb96q5qfzb6hv1fvt25o6slp06-67vu51pd8m-0gyag3j-pqp0hofks";
    private EZPlayer mEZPlayer1 = null;
    private EZPlayer mEZPlayer2 = null;
    private EZPlayer mEZPlayer3 = null;
    private EZPlayer mEZPlayer4 = null;
    private List<EZDeviceInfo> mDeviceInfoList;

    private void initCamera() {
        EZOpenSDK.getInstance().setAccessToken(accessToken);
    }

    private static final int MSG_ON_DEVICE_RESPONSE = 1;

    private void startPlay() {

        EZDeviceInfo deviceInfo = null;
        for (EZDeviceInfo ezDeviceInfo : mDeviceInfoList) {
            Log.d("EZDeviceInfo", ezDeviceInfo.getDeviceName() + ";;SN=" + ezDeviceInfo.getDeviceSerial() + ";; type=" + ezDeviceInfo.getDeviceType());
            if (uuid.equals(ezDeviceInfo.getDeviceSerial())) {
                deviceInfo = ezDeviceInfo;
                break;
            }
        }
        if (deviceInfo == null) {
            return;
        }

        EZCameraInfo cameraInfo = getCameraInfoFromDevice(deviceInfo, 0);
        EZCameraInfo cameraInfo2 = getCameraInfoFromDevice(deviceInfo, 1);
        EZCameraInfo cameraInfo3 = getCameraInfoFromDevice(deviceInfo, 2);
        EZCameraInfo cameraInfo4 = getCameraInfoFromDevice(deviceInfo, 3);
        mEZPlayer1 = EZOpenSDK.getInstance().createPlayer(cameraInfo.getDeviceSerial(), cameraInfo.getCameraNo());
        mEZPlayer2 = EZOpenSDK.getInstance().createPlayer(cameraInfo2.getDeviceSerial(), cameraInfo2.getCameraNo());
        mEZPlayer3 = EZOpenSDK.getInstance().createPlayer(cameraInfo3.getDeviceSerial(), cameraInfo3.getCameraNo());
        mEZPlayer4 = EZOpenSDK.getInstance().createPlayer(cameraInfo4.getDeviceSerial(), cameraInfo4.getCameraNo());


        mEZPlayer1.setHandler(mHandler_camera);
        mEZPlayer1.setSurfaceHold(holder1);
        mEZPlayer1.startRealPlay();

        mEZPlayer2.setHandler(mHandler_camera);
        mEZPlayer2.setSurfaceHold(holder2);
        mEZPlayer2.startRealPlay();

        mEZPlayer3.setHandler(mHandler_camera);
        mEZPlayer3.setSurfaceHold(holder3);
        mEZPlayer3.startRealPlay();

        mEZPlayer4.setHandler(mHandler_camera);
        mEZPlayer4.setSurfaceHold(holder4);
        mEZPlayer4.startRealPlay();
    }


    /**
     * 通过ezdevice 得到其中通道信息
     *
     * @param deviceInfo
     * @return
     */
    public static EZCameraInfo getCameraInfoFromDevice(EZDeviceInfo deviceInfo, int camera_index) {
        if (deviceInfo == null) {
            return null;
        }
        Log.e("camera num = " , String.valueOf(deviceInfo.getCameraNum()));
        if (deviceInfo.getCameraNum() > 0 && deviceInfo.getCameraInfoList() != null && deviceInfo.getCameraInfoList().size() > camera_index) {
            return deviceInfo.getCameraInfoList().get(camera_index);
        }
        return null;
    }



    private Handler mHandler_camera = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ON_DEVICE_RESPONSE:
                    startPlay();
                    break;
                case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
//                    播放成功
//                    Bitmap bitmap = takePhoto();
//                    if (bitmap==null){
//                        Log.e("@@@@@@@@@@","nullllll");
//                    }
                    break;
                case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                    //播放失败,得到失败信息
                    ErrorInfo errorinfo = (ErrorInfo) msg.obj;
                    //得到播放失败错误码
                    int code = errorinfo.errorCode;
                    //得到播放失败模块错误码
                    String codeStr = errorinfo.moduleCode;
                    //得到播放失败描述
                    String description = errorinfo.description;
                    Log.e("result_description" , description);
                    //得到播放失败解决方方案
                    String description2 = errorinfo.sulution;
                    Log.e("result_description" , description2);
                case 50:
//                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,list);
//                    faceData.setAdapter(adapter);
                    break;
                case 51:
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };

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
                if (mEZPlayer1 != null) {
                    mEZPlayer1.setSurfaceHold(holder);
                }
                holder1 = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mEZPlayer1 != null) {
                    mEZPlayer1.setSurfaceHold(null);
                }
                holder1 = null;
            }
        });


        holder2 = camera2.getHolder();
        holder2.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mEZPlayer2 != null) {
                    mEZPlayer2.setSurfaceHold(holder);
                }
                holder2 = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mEZPlayer2 != null) {
                    mEZPlayer2.setSurfaceHold(null);
                }
                holder2 = null;
            }
        });

        holder3 = camera3.getHolder();
        holder3.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (mEZPlayer3 != null) {
                    mEZPlayer3.setSurfaceHold(holder);
                }
                holder3 = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                if (mEZPlayer3 != null) {
                    mEZPlayer3.setSurfaceHold(null);
                }
                holder3 = null;
            }
        });

        holder4 = camera4.getHolder();
        holder4.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (mEZPlayer4 != null) {
                    mEZPlayer4.setSurfaceHold(holder);
                }
                holder4 = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mEZPlayer4 != null) {
                    mEZPlayer4.setSurfaceHold(null);
                }
                holder4 = null;
            }
        });


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mDeviceInfoList = EZOpenSDK.getInstance().getDeviceList(0, 20);

                    mHandler_camera.sendEmptyMessage(MSG_ON_DEVICE_RESPONSE);

                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private void findId() {
        camera1 = findViewById(R.id.sv_camera1);
        camera2 = findViewById(R.id.sv_camera2);
        camera3 = findViewById(R.id.sv_camera3);
        camera4 = findViewById(R.id.sv_camera4);

        sensorList = findViewById(R.id.sensor_list);

        right = findViewById(R.id.ll_right);

        control = findViewById(R.id.dl_rmv);
        jia = findViewById(R.id.sb_jia);
        jian = findViewById(R.id.sb_jian);

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


        control.setOnMenuClickListener(new OnMenuClickListener() {
            @Override
            public void OnMenuClick(int position) {
                switch (position){
                    case 0://顶部
                        upMove();
                        break;
                    case 1://右边
                        rightMove();
                        break;
                    case 2://下面
                        downMove();
                        break;
                    case 3://左边
                        leftMove();
                        break;
                }
            }
        });


        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoomOut();
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoomIn();
            }
        });

    }

    /**
     * 镜头放大
     */
    private void ZoomOut() {
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandZoomOut, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandZoomOut, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 镜头拉近
     */
    private void ZoomIn(){
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandZoomIn, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandZoomIn, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 摄像头上移操作
     */

    private void upMove() {
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandUp, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 摄像头右移操作
     */
    private void rightMove(){
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandRight, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 摄像头下移操作
     */
    private void downMove(){
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandDown, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 摄像头左移操作
     */
    private void leftMove(){
        new Thread(){
            @Override
            public void run() {
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTART,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    EZOpenSDK.getInstance().controlPTZ(uuid,1, EZConstants.EZPTZCommand.EZPTZCommandLeft, EZConstants.EZPTZAction.EZPTZActionSTOP,1);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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