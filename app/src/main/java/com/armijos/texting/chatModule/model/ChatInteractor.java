package com.armijos.texting.chatModule.model;

import android.app.Activity;
import android.net.Uri;

public interface ChatInteractor {
    void subscribeToFriend(String friendUid, String friendEmail);
    void unsubscribeToFriend(String friendUid);

    void subscribeToMessages();
    void unsubscribeToMessages();

    void sendMessage(String msg);
    void senImage(Activity activity, Uri imgUri);
}
