package com.example.nhnent.exercise1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    static final int LIMIT_BYTE = 150;
    EditText etMessage;
    TextView tvByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = (EditText)findViewById(R.id.message);
        tvByte = (TextView)findViewById(R.id.cur_byte) ;

        etMessage.addTextChangedListener(new TextWatcher() {
            String beforeChanged;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeChanged = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int bytes = s.toString().getBytes().length;
                if (bytes > LIMIT_BYTE) {
                    Toast.makeText(MainActivity.this, "150byte까지 입력가능합니다.", Toast.LENGTH_SHORT).show();

                    etMessage.setText(beforeChanged);
                    bytes = etMessage.getText().toString().getBytes().length;
                }
                tvByte.setText(bytes + "");
            }
        });
    }
}
