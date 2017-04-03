package com.example.nhnent.listviewstudy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_list_item_1);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        list.setDivider(new ColorDrawable(Color.YELLOW));
        list.setDividerHeight(5);
        list.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String message = "seleted : " + position;
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            };
}
