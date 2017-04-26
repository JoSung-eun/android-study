package com.example.nhnent.exercise5_gallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.nhnent.exercise5_gallery.network.ImageCallbackListener;
import com.example.nhnent.exercise5_gallery.network.ImageRequestModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity { // TODO: 2017. 4. 24. glide 
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    List<String> imageKeys = new ArrayList<>();

    RequestManager glideRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        initImageKeys();
//        galleryAdapter = new GalleryAdapter(this, imageKeys);
        glideRequestManager = Glide.with(this);
        galleryAdapter = new GalleryAdapter(imageKeys, glideRequestManager);
        recyclerView.setAdapter(galleryAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


    }

    private void initImageKeys() {
        for (int i = 0; i < 1000; i ++) {
            imageKeys.add("https://unsplash.it/400?image=" + String.valueOf(i));
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        galleryAdapter.close();
//    }
}
