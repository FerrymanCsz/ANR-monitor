package com.example.multimessageaggregationtool1;

public class MessageListInfo extends MessageInfo {

    private long WallTime;
    private long CpuTime;
    private int MsgType;
    private int MsgId;
    private int Count;



    public MessageListInfo(long wallTime, long cpuTime, int msgType, int count) {
        this.WallTime = wallTime;
        this.CpuTime = cpuTime;
        this.MsgType = msgType;
//        MsgId = msgId;
        this.Count = count;
    }

    public MessageListInfo() {

    }


    public long getWallTime() {
        return WallTime;
    }

    public void setWallTime(long wallTime) {
        WallTime = wallTime;
    }

    public long getCpuTime() {
        return CpuTime;
    }

    public void setCpuTime(long cpuTime) {
        CpuTime = cpuTime;
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
    }

//    public int getMsgId() {
//        return MsgId;
//    }
//
//    public void setMsgId(int msgId) {
//        MsgId = msgId;
//    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String toString(){
        return "temp wallTime : "+getWallTime() +"  cpuTime "+getCpuTime()+"   MSG_TYPE : "+getMsgType()+" count"+ getCount();
}
}
