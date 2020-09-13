package com.example.zksoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zksoftware.R;
import com.example.zksoftware.uiUtils.CircleImageView;
import com.example.zksoftware.error.Error;
import com.github.iielse.switchbutton.SwitchView;

import java.util.List;

/**
 * Created by looper on 2020/9/12.
 */
public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    public Context context;
    public List<String> sensors;

    public SensorAdapter(Context context, List<String> sensors) {
        if (context == null || sensors == null) {
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }

        this.context = context;
        this.sensors = sensors;


    }


    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sensor_items, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sensorName;
        CircleImageView sensorIcon;
        SwitchView isOpen;

        public ViewHolder(View view) {
            super(view);
            // 找到items中的控件
            findId(view);
        }


        public void findId(View view) {

            sensorName = view.findViewById(R.id.tv_sensorName);
            sensorIcon = view.findViewById(R.id.ci_sensorIcon);
            isOpen = view.findViewById(R.id.sv_isOpen);

        }
    }
}
