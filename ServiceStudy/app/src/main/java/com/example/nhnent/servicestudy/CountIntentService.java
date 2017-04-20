package com.example.nhnent.servicestudy;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nhnent on 2017. 4. 20..
 */

public class CountIntentService extends IntentService {
    int number = 0;

    public CountIntentService() {
        super("count service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            number++;
            Log.d("CountIntentService", "current : " + number);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d("CountIntentService", "destroy");
        super.onDestroy();
    }
}
