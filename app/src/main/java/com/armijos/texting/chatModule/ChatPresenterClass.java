package com.armijos.texting.chatModule;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.armijos.texting.chatModule.events.ChatEvent;
import com.armijos.texting.chatModule.model.ChatInteractor;
import com.armijos.texting.chatModule.model.ChatInteractorClass;
import com.armijos.texting.chatModule.view.ChatView;
import com.armijos.texting.common.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatPresenterClass implements ChatPresenter {
    private ChatView mView;
    private ChatInteractor mInteractor;

    private String mFriendUid, mFriendEmail;

    public ChatPresenterClass(ChatView mView) {
        this.mView = mView;
        this.mInteractor = new ChatInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void onPause() {
        if (mView != null) {
            mInteractor.unsubscribeToFriend(mFriendUid);
            mInteractor.unsubscribeToMessages();
        }
    }

    @Override
    public void onResumen() {
        if (mView != null){
            mInteractor.subscribeToFriend(mFriendUid, mFriendEmail);
            mInteractor.subscribeToMessages();
        }
    }

    @Override
    public void setupFriend(String uid, String email) {
        mFriendEmail = email;
        mFriendUid = uid;
    }

    @Override
    public void sendMessage(String msg) {
        if (mView != null){
            mInteractor.sendMessage(msg);
        }
    }

    @Override
    public void senImage(Activity activity, Uri imgUri) {
        if (mView != null){
            mView.showProgress();
            mInteractor.senImage(activity, imgUri);
        }
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK){
            mView.openDialogPreview(data);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(ChatEvent event) {
        if (mView != null){
            switch (event.getTypeEvent()){
                case ChatEvent.MESSAGE_ADDED:
                    mView.onMessageReceived(event.getMessage());
                    break;
                case ChatEvent.IMAGE_UPLOAD_SUCCESS:
                    mView.hideProgress();
                    break;
                case ChatEvent.GET_STATUS_FRIEND:
                    mView.onStatusUser(event.isConnected(), event.getLastConnection());
                    break;
                case ChatEvent.ERROR_PROCESS_DATA:
                case ChatEvent.IMAGE_UPLOAD_FAIL:
                case ChatEvent.ERROR_SERVER:
                case ChatEvent.ERROR_VOLLEY:
                    mView.hideProgress();
                    mView.onError(event.getResMsg());
                    break;

            }
        }
    }
}
