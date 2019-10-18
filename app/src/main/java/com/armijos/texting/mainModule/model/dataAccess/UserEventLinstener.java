package com.armijos.texting.mainModule.model.dataAccess;

import com.armijos.texting.common.pojo.User;

public interface UserEventLinstener {
    void onUserAdded(User user);
    void onUserUpdated(User user);
    void onUserRemoved(User user);

    void onError(int resMsg);
}
