package com.armijos.texting.loginModule.view;

import android.content.Intent;

public interface LoginView {
    void showProgress();
    void hideProgresss();

    void openMainActivity();
    void openUILogin();

    void showLoginSuccessfully(Intent data);
    void showMessageStarting();
    void showError(int resMsg);
}
