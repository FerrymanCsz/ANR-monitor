package com.example.multimessageaggregationtool1;

/**
 * 这些方法调用都在主线程
 * 注意不要搞耗时操作
 */
public interface IMainThreadSampleListener {


    /**
     * 消息队列中发生anr的消息已经处理完毕
     * */
    void messageQueueDispatchAnrFinish();



}
