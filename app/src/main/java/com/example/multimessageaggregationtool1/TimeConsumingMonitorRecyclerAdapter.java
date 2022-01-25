package com.example.multimessageaggregationtool1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimeConsumingMonitorRecyclerAdapter extends RecyclerView.Adapter<TimeConsumingMonitorViewHolder> {

    ArrayList<MessageInfo> listObject1 = MessageInfoObject.messageList();


    public TimeConsumingMonitorRecyclerAdapter(RecyclerView messageTimeConsumingRecycler) {

    }


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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_consuming_monitor,parent,false);
//        return new TimeConsumingMonitorViewHolder(view);
     return new TimeConsumingMonitorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_consuming_monitor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeConsumingMonitorViewHolder holder, int position) {
        
        // get(position)是按照list中元素的索引进行取值的，如果取得是同一个position的值 当然两个对象是相等的
        holder.parse(listObject1.get(position));


//        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
//            viewHolder.authorIcon.setImageResource(mList.get(i).getImageId());
//            viewHolder.authorName.setText(mList.get(i).getAuthorId());
//            viewHolder.commontContent.setText(mList.get(i).getCommentContent());
//            viewHolder.touchGood.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//        tvCollectionType = itemView.findViewById(R.id.collection_type);
//        tvMsgId = itemView.findViewById(R.id.msg_id);
//        tvWallTime = itemView.findViewById(R.id.wall_time);
//        tvCpuTime = itemView.findViewById(R.id.cup_time);
//        tvCounts = itemView.findViewById(R.id.counts);
//
//        holder.tvCollectionType.setText(messageList.get(position).getMsgType());
//        holder.tvWallTime.setText((int) messageList.get(position).getWallTime());
//        holder.tvCpuTime.setText((int) messageList.get(position).getCpuTime());
//        holder.tvCounts.setText(messageList.get(position).getCount());



    }

    @Override
    public int getItemCount() {
        return listObject1 == null ? 0 : listObject1.size();
    }





}
