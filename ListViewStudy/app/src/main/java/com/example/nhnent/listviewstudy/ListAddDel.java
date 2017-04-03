package com.example.nhnent.listviewstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 4. 3..
 */

public class ListAddDel extends Activity {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_add_del);

        items = new ArrayList<>();
        items.add("First");
        items.add("Second");
        items.add("Third");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


    }

    public void mOnClick(View view) {
        EditText editText = (EditText) findViewById(R.id.new_item);
        switch (view.getId()) {
            case R.id.add:
                String text = editText.getText().toString();
                if (text.length() != 0) {
                    items.add(text);
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete:
                int position = list.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    items.remove(position);
                    list.clearChoices();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
