package com.example.nhnent.exercise2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button okButton;
    Button cancelButton;
    TextView emailErrorText;
    TextView passwordErrorText;
    EditText emailEdit;
    EditText passwordEdit;

    TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okButton = (Button) findViewById(R.id.btn_ok);
        cancelButton = (Button) findViewById(R.id.btn_cancel);

        emailErrorText = (TextView) findViewById(R.id.text_email_error);
        passwordErrorText = (TextView) findViewById(R.id.text_pwd_error);

        emailEdit = (EditText) findViewById(R.id.edit_email);
        passwordEdit = (EditText) findViewById(R.id.edit_password);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText()).matches()) {
                    setContentView(R.layout.after_login);
                    emailText = (TextView) findViewById(R.id.text_email);
                    emailText.setText(emailEdit.getText());
                }
                else {
                    emailErrorText.setVisibility(View.VISIBLE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
