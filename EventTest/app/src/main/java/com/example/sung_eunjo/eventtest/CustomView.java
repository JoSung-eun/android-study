package com.example.sung_eunjo.eventtest;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sung-EunJo on 2017. 3. 26..
 */

public class CustomView extends View {
    static final String TAG_TOUCH = "TOUCH_EVENT";

    public CustomView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG_TOUCH, "CustomView dispatchTouchEvent() : " + event.getAction());

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG_TOUCH, "CustomView onTouchEvent() : " + event.getAction());

        return true;
    }
}
