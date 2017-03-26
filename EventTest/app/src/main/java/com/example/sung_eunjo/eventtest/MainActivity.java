package com.example.sung_eunjo.eventtest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    static final String TAG_TOUCH = "TOUCH_EVENT";
    TextView textView;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        nextBtn = (Button) findViewById(R.id.next_btn);

        View view = findViewById(R.id.view1);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                float curX = event.getX();
                float curY = event.getY();

                if (action == event.ACTION_DOWN) {
                    Log.d(TAG_TOUCH, "View1 - ACTION_DOWN " + curX + ", " + curY);
                }
                else if (action == event.ACTION_MOVE) {
                    Log.d(TAG_TOUCH, "View1 - ACTION_MOVE " + curX + ", " + curY);
                }
                else if (action == event.ACTION_UP) {
                    Log.d(TAG_TOUCH, "View1 - ACTION_UP " + curX + ", " + curY);
                }
                return true;
            }

        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG_TOUCH, "Activity - dispatchTouchEvent");
        Log.d(TAG_TOUCH, "Action code : " + ev.getAction());
        Log.d(TAG_TOUCH, "XY position : " + ev.getX() + ", " + ev.getY());
        Log.d(TAG_TOUCH, "Event time : " + ev.getEventTime());
        Log.d(TAG_TOUCH, "Down event time : " + ev.getDownTime());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG_TOUCH, "Activity - onTouchEvent");
        Log.d(TAG_TOUCH, "Action code : " + event.getAction());
        Log.d(TAG_TOUCH, "XY position : " + event.getX() + ", " + event.getY());
        Log.d(TAG_TOUCH, "Event time : " + event.getEventTime());
        Log.d(TAG_TOUCH, "Down event time : " + event.getDownTime());

        return super.onTouchEvent(event);
    }
}
