package com.example.multimessageaggregationtool1;

import java.io.Serializable;

/**
 * 分发的Message  相关信息
 * */
public class BoxMessage implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 内存地址
     * */
    private String handleName;
    /**
     * 内存地址
     * */
    private String handlerAddress;
    private String callbackName;
    private int messageWhat;
    private long msgId;

    public int getMessageWhat() {

        return messageWhat;
    }
    public String getHandleName() {

        return handleName;
    }

    public String getCallbackName() {

        return callbackName;
    }
    public long getMsgId() {

        return msgId;
    }

    public void setMsgId(long msgId) {

        this.msgId = msgId;
    }


    public BoxMessage() {
    }

    public BoxMessage(String handleName, String callbackName, int messageWhat,String handlerAddress) {
        this.handleName = handleName;
        this.callbackName = callbackName;
        this.messageWhat = messageWhat;
        this.handlerAddress = handlerAddress;
    }

    @Override
    public String toString() {
        return "BoxMessage{" +
                "handleName='" + handleName + '\'' +
                ", handlerAddress='" + handlerAddress + '\'' +
                ", callbackName='" + callbackName + '\'' +
                ", messageWhat=" + messageWhat +
                ", msgId=" + msgId +
                '}';
    }
}