package com.example.zksoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zksoftware.R;
import com.example.zksoftware.bean.Sensor;
import com.example.zksoftware.uiUtils.CircleImageView;
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

    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sensorName;
        ImageView sensorIcon;
        SwitchView isOpen;

        public ViewHolder(View view) {
            super(view);
            // 找到items中的控件
//            findId(view);/
            sensorName = view.findViewById(R.id.tv_sensorName);
            sensorIcon = view.findViewById(R.id.ci_sensorIcon);
            isOpen = view.findViewById(R.id.sv_isOpen);
        }


        public void findId(View view) {



        }
    }
}
