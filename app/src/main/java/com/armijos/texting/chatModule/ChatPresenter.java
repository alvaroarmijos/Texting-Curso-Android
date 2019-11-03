package com.armijos.texting.chatModule;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.armijos.texting.chatModule.events.ChatEvent;

public interface ChatPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResumen();

    void setupFriend(String uid, String email);

    void sendMessage(String msg);
    void senImage(Activity activity, Uri imgUri);

    void result(int requestCode, int resultCode, Intent data);

    void onEventListener(ChatEvent event);
}
