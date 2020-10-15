package com.example.zksoftware.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zksoftware.R;
import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.error.Error;
import com.github.iielse.switchbutton.SwitchView;

import java.util.List;

/**
 * Created by looper on 2020/9/12.
 */
public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    public Context context;
    public List<Sensor> sensors;

    public SensorAdapter(Context context, List<Sensor> sensors) {
        if (context == null || sensors == null) {
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }

        this.context = context;
        this.sensors = sensors;


    }


    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sensor_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {
        holder.sensorName.setText(sensors.get(position).getSensorName());
        if (sensors.get(position).getValue().equals("1")){
            holder.isOpen.setOpened(true);
        }
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sensorName;
        SwitchView isOpen;

        public ViewHolder(final View view) {
            super(view);
            // 找到items中的控件
            sensorName = view.findViewById(R.id.tv_sensorName);
            isOpen = view.findViewById(R.id.sv_isOpen);

            isOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(view, sensors.get(getLayoutPosition()) , isOpen.isOpened());
                    }
                }
            });
        }

    }


    //点击 RecyclerView 某条的监听
    public interface OnItemClickListener{

        /**
         * 当RecyclerView某个被点击的时候回调
         * @param view 点击item的视图
         * @param sensor 点击得到的数据
         */
        void onItemClick(View view, Sensor sensor , boolean isOpen);

    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置RecyclerView某个的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
