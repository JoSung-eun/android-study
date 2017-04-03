package com.example.nhnent.recyclerviewstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    EditText editText;
    Button addButton;
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.add);
        delButton = (Button) findViewById(R.id.delete);
        editText = (EditText) findViewById(R.id.new_item);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this, 2);
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        String[] dataSet = { "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice" };

        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
