package com.example.nhnent.exercise5_gallery.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.example.nhnent.exercise5_gallery.network.ImageCallbackListener;
import com.example.nhnent.exercise5_gallery.network.ImageRequestModule;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

/**
 * Created by nhnent on 2017. 4. 19..
 */

public class ImageCache {
    private final int MEMORY_CACHE_SIZE = 1024 * 1024 * 5; // 5MB

    private final Object diskCacheLock = new Object();
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private final String DISK_CACHE_SUBDIR = "image_cache";
    private boolean diskCacheStarting;

    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskCache;

    public ImageCache() {
        memoryCache = new LruCache<String, Bitmap>(MEMORY_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

        diskCacheStarting = true;
        String diskCachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File cacheFile = new File(diskCachePath + File.separator + DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheFile);
    }

    public void loadBitmap(String key, ImageView imageView) {
        //memory check
        if (memoryCache.get(key) != null) {
            imageView.setImageBitmap(getFromMemoryCache(key));
            Log.d("ImageCache", key + " - get from memory cache");
        }
        else {
            new BitmapWorkerTask(imageView).execute(key); //async task
        }
    }

    public void close() {
        try {
            diskCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> imageViewReference;
        private Context context;

        BitmapWorkerTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
            context = imageView.getContext();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            final String key = params[0];
            // check disk cache
            Bitmap bitmap = getFromDiskCache(key);

            if (bitmap == null) {
                ImageRequestModule.getImage(context, key, new ImageCallbackListener() {
                    @Override
                    public void onSuccess(Bitmap data) {
                        addToMemoryCache(key, data);
                        addToDiskCache(key, data);
                        if (imageViewReference.get() != null) {
                            imageViewReference.get().setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onFail(String data) {
                        imageViewReference.clear();
                        Log.e("ImageCache", "error" + data);
                    }
                });
            }
            else {
                addToMemoryCache(key, bitmap);
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference.get() != null && bitmap != null) {
                imageViewReference.get().setImageBitmap(bitmap);
            }
        }
    }

    /*private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> imageViewReference;

        BitmapWorkerTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            final String key = params[0];

            ImageRequestModule.getImage(context, key, new ImageCallbackListener() {
                @Override
                public void onSuccess(Bitmap data) {
                    addToMemoryCache(key, data);
                    addToDiskCache(key, data);
                    imageViewReference.get().setImageBitmap(data);
                }

                @Override
                public void onFail(String data) {
                    Log.e("ImageCache", "error" + data);
                }
            });

            return null;
        }

        *//*@Override
        protected void onPostExecute(Bitmap bitmap) {
            imageViewReference.get().setImageBitmap(bitmap);
        }*//*
    }*/

    private void addToMemoryCache(String key, Bitmap bitmap) {
        if (getFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
            Log.d("ImageCache", key + " - add to memory cache");
        }
    }

    private Bitmap getFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    private class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            try {
                synchronized (diskCacheLock) {
                    File cacheDir = params[0];
                    diskCache = DiskLruCache.open(cacheDir, Build.VERSION.SDK_INT, 1, DISK_CACHE_SIZE);
                    diskCacheStarting = false;
                    diskCacheLock.notifyAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void addToDiskCache(String key, Bitmap bitmap) {
        synchronized (diskCacheLock) {
            // Add to disk cache
            if (diskCache != null) {
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = diskCache.get(key);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = diskCache.edit(key);
                        if (editor != null) {
                            out = editor.newOutputStream(0);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            editor.commit();
                            Log.d("ImageCache", key + " - add to disk cache");
                        }
                    } else {
                        snapshot.getInputStream(0).close();
                    }
                } catch (final IOException e) {
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

    public Bitmap getFromDiskCache(String key) {
        synchronized (diskCacheLock) {
            // Wait while disk cache is started from background thread
            while (diskCacheStarting) {
                try {
                    diskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            if (diskCache != null) {
                InputStream inputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = diskCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(0);
                        if (inputStream != null) {
                            FileDescriptor fileDescriptor = ((FileInputStream) inputStream).getFD();
                            Log.d("ImageCache", key + " - get from disk cache");
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
}
