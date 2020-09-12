package com.example.zksoftware.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zksoftware.R;
import com.example.zksoftware.utils.Error;

import java.util.List;

/**
 * Created by looper on 2020/9/12.
 */
public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {
    public Context context;
    public List<String> sensors;
    public SensorAdapter(Context context , List<String> sensors){
        if (context == null || sensors == null){
            throw new IllegalArgumentException(Error.UNKOW_Para.getDescription());
        }
    }


    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sensor_items,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);

        }
    }
}
