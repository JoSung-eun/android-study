package com.example.nhnent.bitmapstudy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by nhnent on 2017. 4. 17..
 */

public class MemCachingBitmap extends Activity {
    private LruCache<String, Bitmap> memoryCache;
    ImageView imageView;
    Button clearButton;
    Button loadButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        clearButton = (Button) findViewById(R.id.btn_clear);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
            }
        });

        loadButton = (Button) findViewById(R.id.btn_load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadBitmap(R.drawable.big_image, imageView);

            }
        });

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024); //kb
        final int cacheSize = maxMemory / 8;
        Log.d("Cache", "cache size : " + cacheSize);

        memoryCache = new LruCache<String, Bitmap>(cacheSize) { //size = maximum number of entries
            @Override
            protected int sizeOf(String key, Bitmap value) { //sizeOf() Override하면 kb단위로 cache 사용 return 1이면 개수로
                return value.getByteCount() / 1024;
            }
        };
        loadBitmap(R.drawable.sample1, imageView);

    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            Log.d("Cache", "key = " + resId);
            imageView.setImageBitmap(bitmap);
        } else {
            //this.imageView.setImageResource(R.mipmap.ic_launcher);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
        if (memoryCache.size() > 0) {
            Log.d("Cache", "evict: " + memoryCache.snapshot().entrySet().iterator().next().getKey() + "");
            Log.d("Cache", "size: " + memoryCache.size());
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        ImageView imageView;

        BitmapWorkerTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), params[0]);
            Log.d("Cache", params[0] + "");
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
