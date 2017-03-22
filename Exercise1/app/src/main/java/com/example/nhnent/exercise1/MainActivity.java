package com.example.nhnent.exercise1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.nhnent.exercise1.R.id.maxByte;

public class MainActivity extends Activity {
    static final int LIMIT_BYTE = 150;
    EditText msgContent;
    TextView currentByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgContent = (EditText)findViewById(R.id.msgContent);
        currentByte = (TextView)findViewById(R.id.currentByte) ;

        msgContent.addTextChangedListener(new TextWatcher() {
            String before;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int bytes = s.toString().getBytes().length;
                if(bytes < LIMIT_BYTE) {
                    currentByte.setText(bytes + "");
                }
                else {
                    Toast.makeText(MainActivity.this, "limit", Toast.LENGTH_SHORT).show();
                    System.out.println(before);
                    //msgContent.setText(before);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
