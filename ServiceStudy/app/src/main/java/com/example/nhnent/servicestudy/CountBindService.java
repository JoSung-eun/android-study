package com.example.nhnent.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nhnent on 2017. 4. 20..
 */

public class CountBindService extends Service {
    private int currentNumber;
    private Thread countThread = null;

    ICountService.Stub binder = new ICountService.Stub() {
        @Override
        public int getCurrentNumber() throws RemoteException {
            return currentNumber;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("CountService", "start command");

        if (countThread == null) {
            countThread = new Thread(null, new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        countThread.isInterrupted();// TODO: 2017. 4. 21.  
                        currentNumber ++;

                        Log.d("CountService", "current number : " + currentNumber);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }

                }
            }, "CountThread");

            countThread.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("CountService", "destroyed");

        if (countThread != null) {
            countThread.interrupt(); // TODO: 2017. 4. 21.  
            countThread = null;
            currentNumber = 0;
        }

        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("CountService", "bind service");
        return binder;
    }
}
