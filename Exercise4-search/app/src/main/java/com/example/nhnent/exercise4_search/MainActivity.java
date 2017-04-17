package com.example.nhnent.exercise4_search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.searchmodule.HttpCallbackListener;
import com.example.searchmodule.VolleySearchModule;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    SearchAdapter adapter;
    List<DaumSearchResult.Item> items;

    EditText queryEdit;
    Button searchButton;
    Spinner spinner;

    int pageNo = 1;
    String[] searchOptions;
    String selectedPath;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchOptions = getResources().getStringArray(R.array.search_path);

        queryEdit = (EditText) findViewById(R.id.edit_query);
        searchButton = (Button) findViewById(R.id.button_search);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.search_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        adapter = new SearchAdapter(items);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = queryEdit.getText().toString().trim();
                pageNo = 1;

                /*HttpUrlConnectionModule.requestDaumSearch(selectedPath, query, pageNo, new HttpCallbackListener() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            DaumSearchResult daumSearchResult = new Gson().fromJson(data, DaumSearchResult.class);
                            items = daumSearchResult.getChannel().getItem();
                            adapter = new SearchAdapter(items);
                            recyclerView.setAdapter(adapter);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(String data) {
                        if (adapter != null) {
                            Log.e("MAIN", data);
                            adapter.clear();
                        }
                    }
                });*/

                VolleySearchModule.requestDaumSearch(MainActivity.this, selectedPath, query, pageNo, new HttpCallbackListener() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            DaumSearchResult daumSearchResult = new Gson().fromJson(data, DaumSearchResult.class);
                            items = daumSearchResult.getChannel().getItem();
                            adapter = new SearchAdapter(items);
                            recyclerView.setAdapter(adapter);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = adapter.getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (!adapter.isLastPage() && totalItemCount == lastVisibleItem + 1 && !adapter.isLoading()) {
                    pageNo += 1;
                    adapter.loadMore(recyclerView, selectedPath, query, pageNo);
                    adapter.setLoading(true);
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPath = searchOptions[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedPath = searchOptions[0];
    }
}
