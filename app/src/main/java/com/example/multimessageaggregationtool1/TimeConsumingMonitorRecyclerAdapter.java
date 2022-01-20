package com.example.multimessageaggregationtool1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeConsumingMonitorRecyclerAdapter extends RecyclerView.Adapter<TimeConsumingMonitorViewHolder> {

    List<MessageInfo> messageInfo;




    /**
     * 通过onCreateViewHolder(), RecyclerView知道了每一个子项长什么样子。
     *
     * 通过onBindViewHolder(),让每个子项得以显示正确的数据
     *
     * 通过getItemsCount(), RecyclerView知道了所有子项的数量。
     */

    @NonNull
    @Override
    public TimeConsumingMonitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new TimeConsumingMonitorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_consuming_monitor,parent,false));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_consuming_monitor,parent,false);
        return new TimeConsumingMonitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeConsumingMonitorViewHolder holder, int position) {

        holder.parse(messageInfo.get(position));

    }

    @Override
    public int getItemCount() {
        return messageInfo == null ? 0 : messageInfo.size();
    }





}
