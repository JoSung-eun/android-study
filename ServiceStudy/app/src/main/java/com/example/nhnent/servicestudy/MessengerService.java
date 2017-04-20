package com.example.nhnent.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nhnent on 2017. 4. 20..
 */

public class MessengerService extends Service {
    static final int MSG = 1;

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG:
                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger messenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MessengerService", "bind service");
        Toast.makeText(getApplicationContext(), "bind", Toast.LENGTH_LONG).show();
        return messenger.getBinder();
    }
}
