package com.example.nhnent.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nhnent on 2017. 4. 20..
 */

public class CountService extends Service {
    private int currentNumber = 0;
    private Thread countThread = null;

    @Override
    public void onCreate() { // 서비스 생성 시에만 호출 - 서비스 초기화 과정
        super.onCreate();
        Log.d("CountService", "created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // 원하는 서비스 동작
        super.onStartCommand(intent, flags, startId);
        Log.d("CountService", "start command");
        /*while (true) { //ANR
            currentNumber ++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }*/
        if (countThread == null) {
            countThread = new Thread(null, new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        currentNumber ++;
                        if (currentNumber == 10) {
                            stopSelf();
                        }
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
            countThread.interrupt();
            countThread = null;
            currentNumber = 0;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
