package com.example.sung_eunjo.eventtest;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Sung-EunJo on 2017. 3. 26..
 */

public class CustomViewGroup extends FrameLayout {
    static final String TAG_TOUCH = "TOUCH_EVENT";
    int moveEventCount = 0;

    public CustomViewGroup(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG_TOUCH, "CustomViewGroup dispatchTouchEvent() : " + ev.getAction());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG_TOUCH, "CustomViewGroup onTouchEvent() : " + event.getAction());

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG_TOUCH, "CustomViewGroup onInterceptTouchEvent() : " + ev.getAction());

        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveEventCount = 0;
            case MotionEvent.ACTION_MOVE:
                if (moveEventCount >= 2) {
                    return true;
                }
                else {
                    moveEventCount ++;
                    break;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
