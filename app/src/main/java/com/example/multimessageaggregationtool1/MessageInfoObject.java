package com.example.multimessageaggregationtool1;

import java.util.ArrayList;
import java.util.List;

public class MessageInfoObject {



    private static MessageInfoObject instance = new MessageInfoObject();


    private MessageInfoObject() {

    }

    public static MessageInfoObject getInstance(){
//        MessageInfoObject instance = (MessageInfoObject) MessageInfoObject.instance;
        return instance;

    }

    public static ArrayList<MessageInfo> messageList(){
        ArrayList<MessageInfo> ListObject = new ArrayList<>();
        return ListObject;
    }

//    public List<MessageListInfo> messageListInfos(MessageListInfo) {
//        return messageListInfos();
//    }


//    public static void setInstance(List<MessageInfoObject> instance) {
//        MessageInfoObject.instance = instance;
//    }
}
