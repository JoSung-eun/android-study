package com.example.nhnent.servicestudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nhnent on 2017. 4. 21..
 */

public class CountCallbackService extends Service {
    int num = 0;

    ICount.Stub binder = new ICount.Stub() {
        @Override
        public void getCurNumber(ICountCallback callback) throws RemoteException {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("Service", "call getCurNumber");
            callback.getCurNumberCallback(num);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service", "bind");
        return binder;
    }
}
