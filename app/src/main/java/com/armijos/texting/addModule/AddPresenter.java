package com.armijos.texting.addModule;

import com.armijos.texting.addModule.events.AddEvent;

public interface AddPresenter {
    void onShow();
    void onDestroy();

    void addFriend(String email);
    void onEventListener(AddEvent event);
}
