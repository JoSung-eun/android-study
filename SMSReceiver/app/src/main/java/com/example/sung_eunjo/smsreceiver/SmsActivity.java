package com.example.sung_eunjo.smsreceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SmsActivity extends Activity {
    TextView senderText;
    TextView contentsText;
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        senderText = (TextView) findViewById(R.id.text_sender);
        contentsText = (TextView) findViewById(R.id.text_contents);
        dateText = (TextView) findViewById(R.id.text_date);

        Intent intent = getIntent();
        processIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String date = intent.getStringExtra("date");

            senderText.setText(sender);
            contentsText.setText(contents);
            dateText.setText(date);
        }
    }
}
