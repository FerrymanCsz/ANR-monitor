package com.example.multimessageaggregationtool1;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;
import android.view.Choreographer;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {
    private final long noInit = -1;//为什么要通过这种方式设置为-1？
    private long startTime = noInit;
    private long tempStartTime = noInit;
    private long cpuTempStartTime = noInit;
    private final float mFrameIntervalNanos = ReflectUtils.reflectLongField( Choreographer.getInstance(), Choreographer.class, "mFrameIntervalNanos", 16000000 )*0.000001f;

    private long monitorMsgId = 0;
    private BlockBoxConfig config = new BlockBoxConfig();
    private MessageInfo messageInfo;//messageInfo类中包含对于 消息 的各种信息数据
    private BoxMessage currentMsg = new BoxMessage();
    private IMainThreadSampleListener samplerManager;
    Handler mainHandler = new Handler(Looper.getMainLooper());


//    List<MessageListInfo> mMessageList = new ArrayList<>();
//    List<MessageInfoObject> listObject = new ArrayList<>();
      List<MessageInfo> listObject1 = MessageInfoObject.messageList();
      private MessageInfo tempMsg = new MessageInfo();
//    private MessageInfoObject tempMsg = MessageInfoObject.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showOutcomes = findViewById(R.id.show_outcomes);
        showOutcomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OutComeActivity.class);
                startActivity(intent);
            }
        });



        /**
         * 点击一个按钮，开启子线程向主线程发送消息，模拟耗时操作，及sleep 50ms
         */
        Button test1 = findViewById(R.id.multiMessage);
        test1.setOnClickListener (new View.OnClickListener() {
            //发送多个不是非常严重耗时消息，模拟消息队列繁忙
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 25; i > 0; i--) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //50ms
                                    SystemClock.sleep(50);
                                }
                            });
                        }
                        MessageInfo.sentBroadcast(MainActivity.this);
                    }
                }).start();
            }
        });


//调用println 是奇数次还是偶数  默认false 偶数  true 奇数
        final AtomicBoolean odd = new AtomicBoolean(false);
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                if(x.contains("<<<<< Finished to") && !odd.get()){
                    return;
                }
                //原来是偶数次，那么这次进来就是奇数
                if (!odd.get()) {
                    msgStart( x );
                } else {
                    msgEnd( x );
                }
                odd.set(!odd.get());
            }
        });

    }





    /**
     * 如何表示重复不断从MQ中提取消息? (模拟？)
     * 消息提取出来以后，调用两个方法：start 和 end
     *
     * start方法得到开始的时间，并判断
     *
     * end方法得到处理消息的耗时，从而判断是否需要聚合并处理 > 300?
     * 如果不需要聚合，判断一下是否 > 3000，类型 - ANR； 或者通过handle方法输出值
     * 需要聚合，则+=时间，直到 >300 ,通过handle方法输出值
     * @param msg
     */

    private void msgStart(String msg) {//希望通过这个方法得到msg开始时候的各种时间，以及自动判断是否该开始处理新的msg

        tempStartTime = SystemClock.elapsedRealtime();//定义了tempStartTime
        cpuTempStartTime = SystemClock.currentThreadTimeMillis();//从设备开机到现在的时间，单位毫秒，含系统深度睡眠时间
        currentMsg = BoxMessageUtils.parseLooperStart( msg );//现在的MSG=MQ中最上层的，被LOOPER调出的MSG
        currentMsg.setMsgId( monitorMsgId );//给这个被调出的CURRENTMSG添加ID

        /**
         * 超过这个时间 就发生anr
         * */


//        if (messageInfo != null) {
//            //不是空就直接handle
//            handleMsg();
//        }
        if (messageInfo == null){

            /**
             * 是空的就new一个，并得到各种相关的时间
             * 仅有名字而没有内存空间的变量的具体内容，Java 引入了关键字 null。 null 表示“空”的意思，是绝对意义上的空，这个空指的是不存在。
             * 一个引用变量（当变量指向一个对象时，这个变量就被称为引用变量）没有通过 new 分配内存空间，这个对象就是空对象，Java 使用关键字 null 表示空对象。
             */

            messageInfo = new MessageInfo();
            startTime = SystemClock.elapsedRealtime();
            tempStartTime = startTime;
            long cupStartTime;
            cupStartTime = SystemClock.currentThreadTimeMillis();
            cpuTempStartTime = cupStartTime;
        }
    }


    private void msgEnd(String msg) {//希望能够自动分辨1. 是否应该停止dispatch（ANR time config）  2、 是否应该继续聚合
        /*
        synchronized
        （1）、原子性：所谓原子性就是指一个操作或者多个操作，要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。被synchronized修饰的类或对象的所有操作都是原子的，因为在执行操作之前必须先获得类或对象的锁，直到执行完才能释放。
        （2）、可见性：**可见性是指多个线程访问一个资源时，该资源的状态、值信息等对于其他线程都是可见的。 **synchronized和volatile都具有可见性，其中synchronized对一个类或对象加锁时，一个线程如果要访问该类或对象必须先获得它的锁，而这个锁的状态对于其他任何线程都是可见的，并且在释放锁之前会将对变量的修改刷新到共享内存当中，保证资源变量的可见性。
        （3）、有序性：有序性值程序执行的顺序按照代码先后执行。 synchronized和volatile都具有有序性，Java允许编译器和处理器对指令进行重排，但是指令重排并不会影响单线程的顺序，它影响的是多线程并发执行的顺序性。
        synchronized保证了每个时刻都只有一个线程访问同步代码块，也就确定了线程执行同步代码块是分先后顺序的，保证了有序性。
         */
        synchronized (MainActivity.class) {
            //得到某个消息处理完毕时的相关时间
            long lastEnd = SystemClock.elapsedRealtime();//从设备开机到现在的时间，单位毫秒，含系统深度睡眠时间
            long lastCpuEnd = SystemClock.currentThreadTimeMillis();//获取当前线程总共运行的时间，单位毫秒。如果再次执行该线程，时间会叠加

            long dealt = lastEnd - tempStartTime;//定义dealt处理时间

            boolean msgActivityThread = BoxMessageUtils.isBoxMessageActivityThread(currentMsg);

            if(messageInfo == null){//在这个位置为空 现阶段的逻辑只有 anr 采集时将原来的 messageInfo 置空了
                messageInfo = new MessageInfo();
            }

//          如果dealt处理时间大于config中定义的warntime
            if (dealt > config.getWarnTime() || msgActivityThread) {
//                if (messageInfo.count > 1) {//先处理原来的信息
//                    messageInfo.msgType = MessageInfo.MSG_TYPE_INFO;
//                    handleMsg();//开始处理msg
//                }
                /**
                 * 两种情况
                 */


                messageInfo = new MessageInfo();
                //几个不同time定义
                messageInfo.wallTime = lastEnd - tempStartTime;
                messageInfo.cpuTime = lastCpuEnd - cpuTempStartTime;
                messageInfo.msgType = MessageInfo.MSG_TYPE_WARN;
                messageInfo.boxMessages.add( currentMsg );
                insertMessage();
                listObject1.add(tempMsg);


                boolean anr = dealt > config.getAnrTime(); //定义什么是anr：当处理时间大于配置中的ANR time时
                if (anr) {
                    messageInfo.msgType = MessageInfo.MSG_TYPE_ANR;
                    insertMessage();
                    listObject1.add(tempMsg);
                    handleMsg();//开始处理
                }else if(msgActivityThread){
                    messageInfo.msgType = MessageInfo.MSG_TYPE_ACTIVITY_THREAD_H;
                    insertMessage();
                    listObject1.add(tempMsg);
                    handleMsg();//开始处理
                }



//                if(anr){//不断循环，直到发生了上面定义的anr，则MQ的dispatch Finish
//                    samplerManager.messageQueueDispatchAnrFinish();
//                }
            } else {//不然就一直叠加
                //统计每一次消息分发耗时 他们的叠加就是总耗时
                messageInfo.wallTime += lastEnd - tempStartTime;
                //生成消息的时候，当前线程总的执行时间
                messageInfo.cpuTime += lastCpuEnd - cpuTempStartTime;
                messageInfo.count++;
                messageInfo.msgType = MessageInfo.MSG_TYPE_INFO;
//                insertMessage();
                messageInfo.boxMessages.add( currentMsg );

                if (messageInfo.wallTime > config.getWarnTime()) {
                    messageInfo.msgType = MessageInfo.MSG_TYPE_WARN;
                    insertMessage();
                    listObject1.add(tempMsg);
                    handleMsg();
                }
            }
        }
        monitorMsgId++;
    }


    /**
     * 输出msg type, wall time, cpu time, count 这四个对应的值，并做成一个横向的recyclerview list，配上不同颜色
     */

    private void handleMsg() {
        if (messageInfo != null) {
            MessageInfo temp = messageInfo;
            long msgId = 0L;//给id设置成l格式

            if (temp.boxMessages != null && temp.boxMessages.size() != 0){//这里的temp就是messageInfo，当boxMessage这个ArrayList不是空，size不是0的时候，msg id等于这个list中排位第一个的msg的id
                msgId = temp.boxMessages.get(0).getMsgId();
            }

//            然后打印一个日志出来，包括这些数据
            Log.d(TAG,"temp wallTime : "+temp.wallTime +"  cpuTime "+temp.cpuTime+"   MSG_TYPE : "+MessageInfo.msgTypeToString(temp.msgType)+"  msgId "+msgId+" count"+ temp.count);
            messageInfo = null;//重置messageInfo？将mssageInfo模板清空，方便引入下一个
        }
        messageInfo = null;
    }



    public MessageInfo insertMessage(){
        tempMsg.setWallTime(messageInfo.wallTime);
        tempMsg.setCpuTime(messageInfo.cpuTime);
        tempMsg.setMsgType(messageInfo.msgType);
        tempMsg.setCount(messageInfo.count);
        return tempMsg;

    }

}