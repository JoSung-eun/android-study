package com.example.nhnent.exercise5_gallery.network;

import android.graphics.Bitmap;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public interface ImageCallbackListener {
    void onSuccess(Bitmap data);
    void onFail(String data);
}
