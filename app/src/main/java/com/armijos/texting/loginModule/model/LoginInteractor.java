package com.armijos.texting.loginModule.model;

public interface LoginInteractor {
    void onResume();
    void onPause();

    void getStatusAuth();
}
