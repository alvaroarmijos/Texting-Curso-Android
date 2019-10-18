package com.armijos.texting.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.armijos.texting.mainModule.view.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class UtilsCommon {

    /*
    * Codificar un correo electrónico
    * */

    public static String getEmailEncoded(String email){
        String prekey = email.replace("_", "__");
        return prekey.replace(".", "_");
    }

    /*
    *       Cargar Imagenes basicas
    * */

    public static void loadImage(Context context, String url, ImageView target) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }
}
