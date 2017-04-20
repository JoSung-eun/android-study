package com.example.nhnent.servicestudy;

import android.app.Activity;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button startButton;
    Button stopButton;
    Button showButton;

    private ICountService binder = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = ICountService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.btn_start);
        stopButton = (Button) findViewById(R.id.btn_stop);
        showButton = (Button) findViewById(R.id.btn_show);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent service = new Intent(getApplicationContext(), CountService.class); //5.0(LOLLIPOP)부터 서비스는 명시적 인텐트로
                service.setPackage("com.example.nhnent.servicestudy");
                startService(service);*/

                /*Intent service = new Intent(getApplicationContext(), CountBindService.class);
                service.setPackage("com.example.nhnent.servicestudy");
                bindService(service, connection, Context.BIND_AUTO_CREATE); // 암시적 인텐트 가능*/

                //intent service
                Intent service = new Intent(getApplicationContext(), CountIntentService.class);
                service.setPackage("com.example.nhnent.servicestudy");
                startService(service);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent service = new Intent(getApplicationContext(), CountService.class);
                service.setPackage("com.example.nhnent.servicestudy");
                stopService(service); //or selfStop() in service*/

                /*if (binder != null) {
                    Intent service = new Intent(getApplicationContext(), CountBindService.class);
                    service.setPackage("com.example.nhnent.servicestudy");
                    unbindService(connection);
                }*/

                Intent service = new Intent(getApplicationContext(), CountIntentService.class);
                service.setPackage("com.example.nhnent.servicestudy");
                startService(service);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binder != null) {
                    int curNo;
                    try {
                        curNo = binder.getCurrentNumber();

                        Toast.makeText(MainActivity.this, "current : " + curNo, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
