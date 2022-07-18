package com.skylightapp.Classes;

import com.skylightapp.Interfaces.UploadImagePrefix;

import java.util.Date;


public class ImageUtil {
    public static final String TAG = ImageUtil.class.getSimpleName();


    public static String generateImageTitle(UploadImagePrefix prefix, long parentId) {
        if (parentId>0) {
            return prefix.toString() + parentId;
        }

        return prefix.toString() + new Date().getTime();
    }
    /*public static void loadImage(GlideRequests glideRequests, String url, AppCompatImageView imageView) {
        loadImage(glideRequests, url, imageView, DiskCacheStrategy.ALL);
    }

    public static void loadImage(GlideRequests glideRequests, String url, AppCompatImageView imageView, DiskCacheStrategy diskCacheStrategy) {
        glideRequests.load(url)
                .diskCacheStrategy(diskCacheStrategy)
                .error(R.drawable.ic_stub)
                .into(imageView);
    }

    public static void loadImage(GlideRequests glideRequests, String url, AppCompatImageView imageView,
                                 RequestListener<Drawable> listener) {
        glideRequests.load(url)
                .error(R.drawable.ic_stub)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);
    }

    public static void loadImageCenterCrop(GlideRequests glideRequests, String url, AppCompatImageView imageView,
                                           int width, int height) {
        glideRequests.load(url)
                .centerCrop()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_stub)
                .into(imageView);
    }

    public static void loadImageCenterCrop(GlideRequests glideRequests, String url, AppCompatImageView imageView,
                                           int width, int height, RequestListener<Drawable> listener) {
        glideRequests.load(url)
                .centerCrop()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_stub)
                .listener(listener)
                .into(imageView);
    }

    public static void loadImageCenterCrop(GlideRequests glideRequests, String url, AppCompatImageView imageView,
                                           RequestListener<Drawable> listener) {
        glideRequests.load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_stub)
                .listener(listener)
                .into(imageView);
    }


    @Nullable
    public static Bitmap loadBitmap(GlideRequests glideRequests, String url, int width, int height) {
        try {
            return glideRequests.asBitmap()
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .submit(width, height)
                    .get();
        } catch (Exception e) {
            LogUtil.logError(TAG, "getBitmapfromUrl", e);
            return null;
        }
    }

    public static void loadImageWithSimpleTarget(GlideRequests glideRequests, String url, SimpleTarget<Bitmap> simpleTarget) {
        glideRequests.asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(simpleTarget);
    }

    public static void loadLocalImage(GlideRequests glideRequests, Uri uri, AppCompatImageView imageView,
                                      RequestListener<Drawable> listener) {
        glideRequests.load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .listener(listener)
                .into(imageView);
    }*/
}
