package com.example.nhnent.resourcestudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button pressButton;
    Button smileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_lang);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int sum = outMetrics.densityDpi;
        Log.i("Resource", "dpi = " + sum);

        pressButton = (Button) findViewById(R.id.button_press);
        smileButton = (Button) findViewById(R.id.button_smile);

        pressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.multi_toasttext, Toast.LENGTH_SHORT).show();
            }
        });

        smileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getResources().getString(R.string.multi_smile);
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(text)
                        .setTitle("Alert")
                        .show();
            }
        });
    }
}
