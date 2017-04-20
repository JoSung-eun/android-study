package com.example.nhnent.exercise5_gallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.example.nhnent.exercise5_gallery.network.ImageCallbackListener;
import com.example.nhnent.exercise5_gallery.network.ImageRequestModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    List<String> imageKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        initImageKeys();
        galleryAdapter = new GalleryAdapter(this, imageKeys);
        recyclerView.setAdapter(galleryAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void initImageKeys() {
        for (int i = 0; i < 100; i ++) {
            imageKeys.add(String.valueOf(i));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        galleryAdapter.close();
    }
}
