package com.example.nhnent.exercise4_search;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sung-EunJo on 2017. 4. 16..
 */

public class VolleyModule {
    private static final String API_KEY = "53946c1661edc714a895caebcc092b31";
    private static final String DAUM_URL = "https://apis.daum.net/search";
    private static final int MAX_RESULT_COUNT = 20;
    private static final String OUTPUT_JSON = "json";

    static RequestQueue requestQueue;

    public static void requestDaumSearch(Context context, String path, String query, int pageNo, HttpCallbackListener httpCallbackListener) {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("q", query);
        params.put("result", String.valueOf(MAX_RESULT_COUNT));
        params.put("pageno", String.valueOf(pageNo));
        params.put("output", OUTPUT_JSON);

        requestQueue = Volley.newRequestQueue(context);

        getRequest(path, params, httpCallbackListener);
    }

    private static void getRequest(String path, Map<String, String> params, final HttpCallbackListener httpCallbackListener) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(DAUM_URL).append(path).append("?");
        try {
            urlBuilder.append(DAUM_URL).append(path).append("?");
            for (String key : params.keySet()) {
                if (urlBuilder.length() > 0) {
                    urlBuilder.append('&');
                }
                urlBuilder.append(String.format("%s=%s",
                        URLEncoder.encode(key, "UTF-8"),
                        URLEncoder.encode(params.get(key), "UTF-8")));
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        httpCallbackListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        httpCallbackListener.onFail(error.getMessage());
                    }
        });

        requestQueue.add(stringRequest);
    }
}
