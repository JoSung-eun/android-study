package com.example.nhnent.exercise4_search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    SearchAdapter adapter;

    EditText queryEdit;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryEdit = (EditText) findViewById(R.id.edit_query);
        searchButton = (Button) findViewById(R.id.button_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryEdit.getText().toString().trim();

                UrlConnectionModule.requestSearch(MainActivity.this, query, new HttpCallbackListener() {
                    @Override
                    public void onSuccess(String data) {
                        DaumSearchResult daumSearchResult = new Gson().fromJson(data, DaumSearchResult.class);

                        adapter = new SearchAdapter(daumSearchResult.getChannel().getItem());
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFail(String data) {
                        if (adapter != null) {
                            Log.e("MAIN", data);
                            adapter.clear();
                        }
                    }
                });
            }
        });



    }
}
