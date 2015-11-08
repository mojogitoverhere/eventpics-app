package com.example.david.testapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by david on 10/31/15.
 */
public class AppData {
    private static AppData mInstance = null;
    private RequestQueue mQueue;
    private static Context mContext;
    private ImageLoader mImageLoader;

    public static AppData getInstance(Context context) {
        if (mInstance == null)
            mInstance = new AppData(context);
        return mInstance;
    }

    private AppData(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> lruImageCache = new LruCache<String, Bitmap>(10);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return lruImageCache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        lruImageCache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue() { return mQueue; }

    public <T> void addToRequestQueue(Request<T> request) {
        mQueue.add(request);
    }

    public ImageLoader getImageLoader() { return this.mImageLoader; }
}
