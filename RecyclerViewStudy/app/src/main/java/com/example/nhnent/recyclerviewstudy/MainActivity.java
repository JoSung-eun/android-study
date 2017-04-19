package com.example.nhnent.recyclerviewstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

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

        //layoutManager = new LinearLayoutManager(this);
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager = new GridLayoutManager(this, 4);
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setClickable(true);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

        String[] dataSet = { "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice", "mike", "angel", "crow", "john",
                "ginnie", "sally", "cohen", "rice" };

        adapter = new MyAdapter(this, dataSet);
        recyclerView.setAdapter(adapter);
    }

}
