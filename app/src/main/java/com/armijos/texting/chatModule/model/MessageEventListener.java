package com.armijos.texting.chatModule.model;

import com.armijos.texting.common.pojo.Message;

public interface MessageEventListener {
    void onMessageReceived(Message message);
    void onError(int resMsg);
}
