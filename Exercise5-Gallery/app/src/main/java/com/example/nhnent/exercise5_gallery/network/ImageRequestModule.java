package com.example.nhnent.exercise5_gallery.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sung-EunJo on 2017. 4. 16..
 */

public class ImageRequestModule {
    private static final String IMAGE_URL = "https://unsplash.it/400";
    private static RequestQueue requestQueue;

    public static void getImage(Context context, String no, ImageCallbackListener imageCallbackListener) {
        requestQueue = getRequestQueue(context);

        StringBuilder url = new StringBuilder();
        url.append(IMAGE_URL).append("?image=").append(no);
        requestImage(url.toString(), imageCallbackListener);
    }

    private static void requestImage(String url, final ImageCallbackListener imageCallbackListener) {
        ImageRequest imageRequest =  new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageCallbackListener.onSuccess(response);
                    }
                },
                400, 400, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageCallbackListener.onFail(error.getLocalizedMessage());
                    }
                }
        );

        requestQueue.add(imageRequest);
    }

    private static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            return Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
