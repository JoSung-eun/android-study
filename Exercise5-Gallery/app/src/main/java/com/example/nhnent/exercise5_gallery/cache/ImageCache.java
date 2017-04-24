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
import java.util.stream.Stream;

/**
 * Created by nhnent on 2017. 4. 19..
 */

public class ImageCache {
    private final int MEMORY_CACHE_SIZE = 1024 * 1024 * 10; // 5MB

    private final Object diskCacheLock = new Object();
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 100; // 20MB
    private final String DISK_CACHE_SUBDIR = "image_cache";
    private boolean diskCacheStarting;

    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskCache;

    public ImageCache(Context context) {
        memoryCache = new LruCache<String, Bitmap>(MEMORY_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        //todo context.getCacheDir()
        diskCacheStarting = true;
        String diskCachePath = context.getCacheDir().getAbsolutePath();
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
            Bitmap emptyBitmap = BitmapFactory.decodeResource(imageView.getResources(), R.drawable.empty_photo);
            
            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView); // TODO: 2017. 4. 24.  
            AsyncDrawable asyncDrawable = new AsyncDrawable(imageView.getResources(), emptyBitmap, bitmapWorkerTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, key);
        }
    }

    public void cancel(ImageView imageView, String key) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) imageView.getDrawable();
                if (asyncDrawable.getBitmapWorkerTask() != null) {
                    Log.d("ImageCache", "imageView task cancel() " + key);
                    asyncDrawable.getBitmapWorkerTask().cancel(true);
                }
                imageView.setImageBitmap(BitmapFactory.decodeResource(imageView.getResources(), R.drawable.empty_photo));
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

    private class BitmapWorkerTask extends AsyncTask<String, Void, WeakReference<Bitmap>> {
        private WeakReference<ImageView> imageViewReference;
        private WeakReference<Bitmap> bitmapReference; // TODO: 2017. 4. 24. <bitmap> 추가
        private Context context;
        private String key;

        BitmapWorkerTask(ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
            context = imageView.getContext();
        }

        @Override
        protected WeakReference<Bitmap> doInBackground(String... params) {
            key = params[0];
            // check disk cache
            bitmapReference = new WeakReference<>(getFromDiskCache(key));

//            if (bitmap == null) {
              if (bitmapReference.get() == null) {
                if (!isCancelled()) {
                    ImageRequestModule.getImage(context, key, new ImageCallbackListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            if (!isCancelled() && imageViewReference.get() != null) {
                                imageViewReference.get().setImageBitmap(bitmap);
                                addToMemoryCache(key, bitmap);
                                addToDiskCache(key, bitmap);
                            }
                        }

                        @Override
                        public void onFail(String data) {
                            imageViewReference.clear();
                            bitmapReference.clear();
                            Log.e("ImageCache", "error " + key + " " + data);
                        }
                    });
                }
            }
            else {
                addToMemoryCache(key, bitmapReference.get());
                return bitmapReference;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            handleCancelled(bitmapReference);
        }

        @Override
        protected void onCancelled(WeakReference<Bitmap> bitmapWeakReference) {
            handleCancelled(bitmapWeakReference);
        }

        private void handleCancelled(WeakReference<Bitmap> bitmapReference) {
            if (bitmapReference != null) {
                bitmapReference.clear();
            }
            if (imageViewReference != null) {
                imageViewReference.clear();
            }

            ImageRequestModule.cancel(key);
            Log.d("ImageCache", "AsyncTask cancelled " + key);
        }

        @Override
        protected void onPostExecute(WeakReference<Bitmap> bitmapReference) {
            if (!isCancelled() && imageViewReference != null && bitmapReference != null) {
                imageViewReference.get().setImageBitmap(bitmapReference.get());
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
                OutputStream outputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = diskCache.get(key);
                    if (snapshot == null) { // TODO: 2017. 4. 24. block 깊은 건 - 분리
                        final DiskLruCache.Editor editor = diskCache.edit(key);
                        if (editor != null) { 
                            outputStream = editor.newOutputStream(0);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
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
                    closeOutputStream(outputStream);
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
                    if (snapshot != null) { // TODO: 2017. 4. 24. 분리 
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
                    closeInputStream(inputStream);
                }
            }
        }
        return null;
    }

    private void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeOutputStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
