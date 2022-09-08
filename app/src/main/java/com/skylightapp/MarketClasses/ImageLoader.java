package com.skylightapp.MarketClasses;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

public class ImageLoader {
    private LruCache<String, Bitmap> mMemoryCache;

    public ImageLoader() {

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }
    public void removeBitmapFromMemoryCache(String key) {
        mMemoryCache.remove(key);
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


    public void downloadImage(final String photoUrl, final ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemCache(photoUrl);

        if (bitmap == null) {
            Glide.with(App.getAppContext()).load(photoUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(resource);
                    addBitmapToMemoryCache(photoUrl, resource);
                }
            });
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
}
