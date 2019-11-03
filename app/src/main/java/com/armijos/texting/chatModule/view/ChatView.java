package com.armijos.texting.chatModule.view;

import android.content.Intent;

import com.armijos.texting.common.pojo.Message;

public interface ChatView {
    void showProgress();
    void hideProgress();

    void onStatusUser(boolean connected, long lastConnection);

    void onError(int resMsg);

    void onMessageReceived(Message msg);

    void openDialogPreview(Intent data);
}
