package com.armijos.texting.addModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.armijos.texting.common.model.BasicEventsCallback;
import com.armijos.texting.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.armijos.texting.common.pojo.User;
import com.armijos.texting.common.utils.UtilsCommon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addFriend(String email, User myUser, BasicEventsCallback callback){
        Map<String, Object> myUserMap = new HashMap<>();
        myUserMap.put(User.USERNAME, myUser.getUsername());
        myUserMap.put(User.EMAIL, myUser.getEmail());
        myUserMap.put(User.PHOTO_URL, myUser.getPhotoUrl());

        final String emailEncoded = UtilsCommon.getEmailEncoded(email);
        DatabaseReference userReference = mDatabaseAPI.getRequestReference(emailEncoded);
        userReference.child(myUser.getUid()).updateChildren(myUserMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError();
                    }
                });
    }
}
