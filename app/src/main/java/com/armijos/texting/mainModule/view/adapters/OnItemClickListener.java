package com.armijos.texting.mainModule.view.adapters;

import com.armijos.texting.common.pojo.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);

    void onAcceptRequest(User user);
    void onDenyRequest(User user);

}
