package com.example.nhnent.bitmapstudy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.isExternalStorageRemovable;

/**
 * Created by Sung-EunJo on 2017. 4. 17..
 */

public class DiskCachingBitmap extends Activity {
    private DiskLruCache diskLruCache;
    private final Object diskCacheLock = new Object();
    private boolean diskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "image_cache";

    boolean toggle = true;

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
                if (toggle) {
                    new BitmapWorkerTask().execute(R.drawable.big_image);
                }
                else {
                    new BitmapWorkerTask().execute(R.drawable.sample1);
                }
                toggle = !toggle;
            }
        });

        final String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        File cacheFile = new File(cachePath + File.separator + DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheFile);
        new BitmapWorkerTask().execute(R.drawable.sample1);
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            try {
                synchronized (diskCacheLock) {
                    File cacheDir = params[0];
                    diskLruCache = DiskLruCache.open(cacheDir, Build.VERSION.SDK_INT, 1, DISK_CACHE_SIZE);
                    diskCacheStarting = false; // Finished initialization
                    diskCacheLock.notifyAll(); // Wake any waiting threads
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final String imageKey = String.valueOf(params[0]);

            // Check disk cache in background thread
            Bitmap bitmap = getBitmapFromDiskCache(imageKey);

            if (bitmap == null) { // Not found in disk cache
                // Process as normal
                bitmap = BitmapFactory.decodeResource(getResources(), params[0]);
                // Add final bitmap to caches
                addBitmapToCache(imageKey, bitmap);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        synchronized (diskCacheLock) {
            // Add to disk cache
            if (diskLruCache != null) {
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = diskLruCache.edit(key);
                        if (editor != null) {
                            out = editor.newOutputStream(0);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            editor.commit();
                            out.close();
                            Log.d("Cache", "add to cache");
                        }
                    } else {
                        snapshot.getInputStream(0).close();
                    }
                } catch (final IOException e) {
                    Log.e("Cache", "addBitmapToCache - " + e);
                } catch (Exception e) {
                    Log.e("Cache", "addBitmapToCache - " + e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        synchronized (diskCacheLock) {
            // Wait while disk cache is started from background thread
            while (diskCacheStarting) {
                try {
                    diskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            if (diskLruCache != null) {
                InputStream inputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(0);
                        if (inputStream != null) {
                            FileDescriptor fileDescriptor = ((FileInputStream) inputStream).getFD();
                            Log.d("Cache", "get from cache");
                            return BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
