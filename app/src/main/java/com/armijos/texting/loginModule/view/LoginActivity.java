package com.armijos.texting.loginModule.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.armijos.texting.R;
import com.armijos.texting.common.Constants;
import com.armijos.texting.loginModule.LoginPresenter;
import com.armijos.texting.loginModule.LoginPresenterClass;
import com.armijos.texting.mainModule.view.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public static final int RC_SIGN_IN = 21;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private LoginPresenter mPresenter;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPresenter = new LoginPresenterClass(this);
        mPresenter.onCreate();
        mPresenter.getStatusAuth();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode, data);
    }

    /*
    * LoginView
    * */

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgresss() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void openUILogin() {
        AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder().build();

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls("www.plicy.cursos.com",
                        "www.pplicy.cursos.com")
                .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                        googleIdp))
                .setTheme(R.style.BaseTheme)
                .setLogo(R.mipmap.ic_launcher)
                .build(), RC_SIGN_IN);
    }

    @Override
    public void showLoginSuccessfully(Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        String email = "";
        String dominio;
        if (response != null){
            email = response.getEmail();

            if (email != null && email.contains("@")){
                dominio = email.substring(email.indexOf("@")+1);
                mFirebaseAnalytics.setUserProperty(Constants.USER_PROPERTY_EMAIL_PROVIDER, dominio);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, null);
                //email provider

            }
        }

        Toast.makeText(this, getString(R.string.login_message_success, email), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showMessageStarting() {
        tvMessage.setText(R.string.login_message_loading);
    }

    @Override
    public void showError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }
}
