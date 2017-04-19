package com.example.nhnent.exercise5_gallery;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nhnent.exercise5_gallery.cache.ImageCache;

import java.util.List;

/**
 * Created by nhnent on 2017. 4. 19..
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> imageKeys;
    private ImageCache imageCache;

    GalleryAdapter(Context context, List<String> imageKeys) {
        this.imageKeys = imageKeys;
        imageCache = new ImageCache();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_view, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageCache.loadBitmap(imageKeys.get(position), imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if (imageKeys == null) {
            return 0;
        }
        return imageKeys.size();
    }

    public void close() {
        imageCache.close();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
