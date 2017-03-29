package com.example.nhnent.exercise1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.Charset;
import java.util.Arrays;

public class MainActivity extends Activity {
    static final int LIMIT_BYTE = 150;
    EditText messageEdit;
    TextView currentByteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEdit = (EditText)findViewById(R.id.edit_message);
        currentByteText = (TextView)findViewById(R.id.text_cur_byte) ;

        messageEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                byte[] bytes = s.toString().getBytes();

                if (bytes.length > LIMIT_BYTE) {
                    String limitedText = new String(Arrays.copyOf(bytes, 150), Charset.forName("UTF-8"));

                    if (limitedText.charAt(limitedText.length() - 1) > 0x7F) {
                        messageEdit.setText(limitedText.substring(0, limitedText.length() - 1));
                    }
                    else {
                        messageEdit.setText(limitedText);
                    }

                    bytes = messageEdit.getText().toString().getBytes();

                    Toast.makeText(MainActivity.this, "150byte까지 입력가능합니다.", Toast.LENGTH_SHORT).show();
                }
                currentByteText.setText(String.valueOf(bytes.length));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
