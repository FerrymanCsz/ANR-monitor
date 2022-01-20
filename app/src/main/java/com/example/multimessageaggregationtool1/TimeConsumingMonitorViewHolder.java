package com.example.multimessageaggregationtool1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeConsumingMonitorViewHolder extends RecyclerView.ViewHolder{
    /**
     * holder部分
     */


        private TextView tvCollectionType, tvMsgId,tvWallTime, tvCpuTime, tvCounts;

        public TimeConsumingMonitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollectionType = itemView.findViewById(R.id.collection_type);
            tvMsgId = itemView.findViewById(R.id.msg_id);
            tvWallTime = itemView.findViewById(R.id.wall_time);
            tvCpuTime = itemView.findViewById(R.id.cup_time);
            tvCounts = itemView.findViewById(R.id.counts);

        }

        public void parse(MessageInfo messageInfo) {
            //通过
            itemView.setBackgroundResource(getItemBg(messageInfo));
            tvMsgId.setText("msgId: ");
            if(messageInfo.boxMessages != null && messageInfo.boxMessages.size() != 0){
                if(messageInfo.boxMessages.get(0) != null){
                    tvMsgId.setText("msgId: "+messageInfo.boxMessages.get(0).getMsgId());
                }else {
                    tvMsgId.setText("msgId: ");
                }

            }
            tvCollectionType.setText("消息类型："+MessageInfo.msgTypeToString(messageInfo.msgType));
            tvWallTime.setText("wall: "+messageInfo.wallTime);
            tvCpuTime.setText("cpu: "+messageInfo.cpuTime);
            tvCounts.setText("msgCount: "+messageInfo.count);
        }

        private int getItemBg(MessageInfo messageInfo) {
            switch (messageInfo.msgType) {
                case MessageInfo.MSG_TYPE_ANR:
                    return R.drawable.icon_msg_anr_bg;
                case MessageInfo.MSG_TYPE_WARN:
                    return R.drawable.icon_msg_warn_bg;
                case MessageInfo.MSG_TYPE_ACTIVITY_THREAD_H:
                    return R.drawable.icon_msg_activity_thread_h_bg;
            }
            return R.drawable.icon_msg_info_bg;
        }
}
