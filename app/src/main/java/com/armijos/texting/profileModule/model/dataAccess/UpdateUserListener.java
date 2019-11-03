package com.armijos.texting.profileModule.model.dataAccess;

public interface UpdateUserListener {
    void onSuccesss();
    void onNotifyContacts();
    void onError(int resMsg);
}
