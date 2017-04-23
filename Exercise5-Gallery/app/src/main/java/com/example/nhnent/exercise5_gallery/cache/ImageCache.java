package com.example.nhnent.exercise5_gallery.cache;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.example.nhnent.exercise5_gallery.R;
import com.example.nhnent.exercise5_gallery.network.ImageCallbackListener;
import com.example.nhnent.exercise5_gallery.network.ImageRequestModule;

import java.io.ByteArrayOutputStream;
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
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 20; // 10MB
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
            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(imageView.getResources(), BitmapFactory.decodeResource(imageView.getResources(), R.drawable.empty_photo), bitmapWorkerTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute(key);
        }
    }

    public void cancel(ImageView imageView, String key) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) imageView.getDrawable();
                if (asyncDrawable.getBitmapWorkerTask() != null) {
                    Log.d("ImageCache", "imageView task cancel set");
                    asyncDrawable.getBitmapWorkerTask().cancel(true);
                }
                else {
                    asyncDrawable.getBitmap().recycle();
                }
            }
            ImageRequestModule.cancel(key);
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
        private Bitmap bitmap;
        private String key;

        BitmapWorkerTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
            context = imageView.getContext();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            key = params[0];
            // check disk cache
            bitmap = getFromDiskCache(key);

            if (bitmap == null) {
                if (!isCancelled()) {
                    ImageRequestModule.getImage(context, key, new ImageCallbackListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            if (!isCancelled() && imageViewReference.get() != null) {
                                imageViewReference.get().setImageBitmap(bitmap);

                                addToMemoryCache(key, bitmap);

//                                if (!isCancelled()) {
                                    addToDiskCache(key, bitmap);
//                                }
                                Log.d("ImageCache", "success " + key);
                            }
                        }

                        @Override
                        public void onFail(String data) {
                            imageViewReference.clear();
                            Log.e("ImageCache", "error" + data);
                        }
                    });
                }
            }
            else {
                addToMemoryCache(key, bitmap);
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            handleCancelled(bitmap);
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            handleCancelled(bitmap);
        }

        private void handleCancelled(Bitmap result) {
            ImageRequestModule.cancel(key);
            Log.d("ImageCache", "AsyncTask cancelled " + result);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (!isCancelled() && imageViewReference.get() != null && bitmap != null) {
                imageViewReference.get().setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Log.d("ImageCache", "view size : " + imageViewReference.get().getWidth() + " * " + imageViewReference.get().getHeight());
                Log.d("ImageCache", "bitmap width : " + bitmap.getWidth()+"");
                Log.d("ImageCache", "bitmap " + key + " size : " + stream.toByteArray().length);
            }
        }
    }

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

    private void addToDiskCache(String key, Bitmap bitmap) {
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
                            diskCache.flush();
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

    private Bitmap getFromDiskCache(String key) {
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



    private class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskWeakReference;

        AsyncDrawable(Resources resources, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(resources, bitmap);
            bitmapWorkerTaskWeakReference = new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskWeakReference.get();
        }
    }

}
