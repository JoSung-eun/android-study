package com.example.nhnent.contentproviderstudy;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity {
    static final String TAG = "MainActivity";
    public StudentsDBManager dbManager = null;

    Button insertButton;
    Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = StudentsDBManager.getInstance(this);

        insertButton = (Button) findViewById(R.id.button_add);
        selectButton = (Button) findViewById(R.id.button_select);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues rowValue = new ContentValues();

                rowValue.put("number", "200012346");
                rowValue.put("name", "john");
                rowValue.put("department", "computer");
                rowValue.put("grade", 3);

                long id = dbManager.insert(rowValue);

                Log.i(TAG, "insert record : " + id);
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {"_id", "number", "name", "department", "grade"};

                Cursor cursor = dbManager.query(columns, null, null, null, null, null);

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
                    Log.i(TAG, result.toString());
                    cursor.close();
                }
            }
        });

    }
}
