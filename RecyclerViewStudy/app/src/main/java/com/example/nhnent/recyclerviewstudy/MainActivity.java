package com.example.nhnent.recyclerviewstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        String[] dataSet = { "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice" };
        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

    }
}
