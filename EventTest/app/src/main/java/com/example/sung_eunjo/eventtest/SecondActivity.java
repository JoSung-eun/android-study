package com.example.sung_eunjo.eventtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Sung-EunJo on 2017. 3. 26..
 */

public class SecondActivity extends Activity {
    static final String TAG = "TOUCH_EVENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomViewGroup viewGroup = new CustomViewGroup(this);
        viewGroup.setBackgroundColor(Color.BLUE);
        FrameLayout.LayoutParams viewGroupParams = new FrameLayout.LayoutParams(300, 300);

        CustomView view = new CustomView(this);
        view.setBackgroundColor(Color.YELLOW);
        FrameLayout.LayoutParams viewParams = new FrameLayout.LayoutParams(150, 150);

        viewGroup.addView(view, viewParams);

        setContentView(viewGroup, viewGroupParams);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity - dispatchTouchEvent : " + ev.getAction());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Activity - onTouchEvent : " + event.getAction());

        return super.onTouchEvent(event);
    }


}
