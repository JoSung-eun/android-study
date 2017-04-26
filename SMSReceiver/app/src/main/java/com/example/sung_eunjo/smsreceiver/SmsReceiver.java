package com.example.sung_eunjo.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

import static android.R.attr.format;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Call onReceive()");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender : " + sender);

            String contents = messages[0].getMessageBody();
            Log.i(TAG, "SMS contents : " + contents);

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "SMS received date : " + receivedDate.toString());

            sendToActivity(context, sender, contents, receivedDate);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objects.length];

        int smsCount = objects.length;
        for (int i = 0; i < smsCount; i ++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format); //PDU: Protocol Data Unit, SMS 데이터 포맷 ?
            }
            else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
        }

        return messages;
    }

    private void sendToActivity(Context context, String sender, String contents, Date date) {
        Intent intent = new Intent(context, SmsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("date", date.toString());

        context.startActivity(intent);
    }


}
