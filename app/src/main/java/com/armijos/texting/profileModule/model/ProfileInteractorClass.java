package com.armijos.texting.profileModule.model;

import android.app.Activity;
import android.net.Uri;

import com.armijos.texting.common.model.EventErrorTypeListener;
import com.armijos.texting.common.model.StorageUploadImageCallback;
import com.armijos.texting.common.pojo.User;
import com.armijos.texting.profileModule.events.ProfileEvent;
import com.armijos.texting.profileModule.model.dataAccess.Authentication;
import com.armijos.texting.profileModule.model.dataAccess.RealtimeDatabase;
import com.armijos.texting.profileModule.model.dataAccess.Storage;
import com.armijos.texting.profileModule.model.dataAccess.UpdateUserListener;

import org.greenrobot.eventbus.EventBus;

public class ProfileInteractorClass implements ProfileInteractor {
    private Authentication mAuthentication;
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    private User mMyUser;

    public ProfileInteractorClass() {
        mAuthentication = new Authentication();
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    private User getCurrrentuser(){
        if (mMyUser == null){
            mMyUser = mAuthentication.getmAuthenticationAPI().getAuthUser();
        }

        return  mMyUser;
    }

    @Override
    public void updateUsername(String username) {
        User myUser = getCurrrentuser();
        myUser.setUsername(username);
        mDatabase.changeUsername(myUser, new UpdateUserListener() {
            @Override
            public void onSuccesss() {
                mAuthentication.updateUsernameFirebaseProfile(myUser, new EventErrorTypeListener() {
                    @Override
                    public void onError(int typeEvent, int resMsg) {
                        post(typeEvent, null, resMsg);
                    }
                });
            }

            @Override
            public void onNotifyContacts() {
                postUsernameSuccess();
            }

            @Override
            public void onError(int resMsg) {
                post(ProfileEvent.ERROR_USERNAME, null, 0);
            }
        });
    }

    @Override
    public void updateImage(Activity activity, Uri uri, String oldPhotoUrl) {
        mStorage.uploadImageProfile(activity, uri, getCurrrentuser().getEmail(), new StorageUploadImageCallback() {
            @Override
            public void onSuccess(Uri uri) {
                mDatabase.updatePhotoUrl(uri, getCurrrentuser().getUid(), new StorageUploadImageCallback() {
                    @Override
                    public void onSuccess(Uri newUri) {
                        post(ProfileEvent.UPLOAD_IMAGE, newUri.toString(), 0);
                    }

                    @Override
                    public void onError(int resMsg) {
                        post(ProfileEvent.ERROR_SERVER, resMsg);
                    }
                });

                mAuthentication.updateImageFirebaseProfile(uri, new StorageUploadImageCallback() {
                    @Override
                    public void onSuccess(Uri newUri) {
                        mStorage.deleteOldImage(oldPhotoUrl, newUri.toString());
                    }

                    @Override
                    public void onError(int resMsg) {
                        post(ProfileEvent.ERROR_PROFILE, resMsg);
                    }
                });
            }

            @Override
            public void onError(int resMsg) {
                post(ProfileEvent.ERROR_IMAGE, resMsg);
            }
        });
    }

    private void post(int typeEvent, int resMsg) {
        post(typeEvent, null, resMsg);
    }

    private void postUsernameSuccess() {
        post(ProfileEvent.SAVE_USERNAME, null, 0);
    }

    private void post(int typeEvent, String photoUrl, int resMsg) {
        ProfileEvent event = new ProfileEvent();
        event.setPhotoUrl(photoUrl);
        event.setResMsg(resMsg);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }
}
