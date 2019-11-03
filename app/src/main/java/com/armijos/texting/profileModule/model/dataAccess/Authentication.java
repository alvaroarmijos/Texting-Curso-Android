package com.armijos.texting.profileModule.model.dataAccess;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.armijos.texting.R;
import com.armijos.texting.common.model.EventErrorTypeListener;
import com.armijos.texting.common.model.StorageUploadImageCallback;
import com.armijos.texting.common.model.dataAccess.FirebaseAuthenticationAPI;
import com.armijos.texting.common.pojo.User;
import com.armijos.texting.profileModule.events.ProfileEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Authentication {
    private FirebaseAuthenticationAPI mAuthenticationAPI;

    public Authentication() {
        mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    public FirebaseAuthenticationAPI getmAuthenticationAPI() {
        return mAuthenticationAPI;
    }

    public void updateUsernameFirebaseProfile(User myUser, EventErrorTypeListener listener){
        FirebaseUser user = mAuthenticationAPI.getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(myUser.getUsername())
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){
                        listener.onError(ProfileEvent.ERROR_PROFILE, R.string.profile_error_userUpdated);
                    }
                }
            });
        }
    }

    public void updateImageFirebaseProfile(Uri downloadUri, StorageUploadImageCallback callback){
        FirebaseUser user = mAuthenticationAPI.getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(downloadUri)
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        callback.onSuccess(downloadUri);
                    } else {
                        callback.onError(R.string.profile_error_imageUpdated);
                    }
                }
            });
        }
    }
}
