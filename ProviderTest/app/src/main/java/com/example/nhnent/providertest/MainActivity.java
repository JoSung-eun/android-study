package com.example.nhnent.providertest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String PROVIDER_URI = "content://com.example.nhnent.StudentsProvider/students";

    Button insertButton;
    Button selectButton;
    Button selectOneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertButton = (Button) findViewById(R.id.button_add);
        selectButton = (Button) findViewById(R.id.button_select);
        selectOneButton = (Button) findViewById(R.id.button_select_one);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues rowValue = new ContentValues();

                rowValue.put("number", "200012346");
                rowValue.put("name", "john");
                rowValue.put("department", "computer");
                rowValue.put("grade", 3);

                getContentResolver().insert(Uri.parse(PROVIDER_URI), rowValue);
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {"_id", "number", "name", "department", "grade"};

                Cursor cursor = getContentResolver().query(Uri.parse(PROVIDER_URI), columns, null, null, null, null);

                if (cursor != null) {
                    StringBuilder result = new StringBuilder();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String number = cursor.getString(1);
                        String name = cursor.getString(2);
                        String department = cursor.getString(3);
                        int grade = cursor.getInt(4);

                        result.append("id : ").append(id).append('\n')
                                .append("number : ").append(number).append('\n')
                                .append("name : ").append(name).append('\n');
                    }

                    result.append("Total : ").append(cursor.getCount());
                    Log.i("MainActivity", result.toString());
                    cursor.close();
                }
            }
        });

        selectOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {"_id", "number", "name", "department", "grade"};

                Cursor cursor = getContentResolver().query(Uri.parse(PROVIDER_URI + "/3"), columns, null, null, null, null);

                if (cursor != null) {
                    StringBuilder result = new StringBuilder();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String number = cursor.getString(1);
                        String name = cursor.getString(2);
                        String department = cursor.getString(3);
                        int grade = cursor.getInt(4);

                        result.append("id : ").append(id).append('\n')
                                .append("number : ").append(number).append('\n')
                                .append("name : ").append(name).append('\n');
                    }

                    result.append("Total : ").append(cursor.getCount());
                    Log.i("MainActivity", result.toString());
                    cursor.close();
                }
            }
        });
    }
}
