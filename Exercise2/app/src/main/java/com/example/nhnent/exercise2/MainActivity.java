package com.example.nhnent.exercise2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    static final String PREFS_NAME = "Ex2PrefsFile";
    static final String PREFS_KEY = "LOGIN_EMAIL";

    String loggedInEmail;

    Button okButton;
    Button cancelButton;
    TextView emailErrorText;
    TextView passwordErrorText;
    EditText emailEdit;
    EditText passwordEdit;
    CheckBox autoLoginCheck;

    TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loggedInEmail = sharedPreferences.getString(PREFS_KEY, "");

        if(loggedInEmail.length() != 0) {
            login(loggedInEmail);
        }
        else {
            setContentView(R.layout.activity_main);

            okButton = (Button) findViewById(R.id.btn_ok);
            cancelButton = (Button) findViewById(R.id.btn_cancel);

            emailErrorText = (TextView) findViewById(R.id.text_email_error);
            passwordErrorText = (TextView) findViewById(R.id.text_pwd_error);

            emailEdit = (EditText) findViewById(R.id.edit_email);
            passwordEdit = (EditText) findViewById(R.id.edit_password);

            autoLoginCheck = (CheckBox) findViewById(R.id.check_auto_login);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailEdit.getText().toString();
                    String password = passwordEdit.getText().toString();

                    if (isValidEmail(email)) {
                        login(email);

                        if (autoLoginCheck.isChecked()) {
                            loggedInEmail = email;
                        }
                    } else {
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

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFS_KEY, loggedInEmail);
        editor.commit();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void login(String email) {
        setContentView(R.layout.after_login);

        emailText = (TextView) findViewById(R.id.text_email);
        emailText.setText(email);
    }
    
}
