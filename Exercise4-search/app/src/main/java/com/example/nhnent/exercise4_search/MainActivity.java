package com.example.nhnent.exercise4_search;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String API_KEY = getResources().getString(R.string.daum_api_key);

        Log.i("search", API_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UrlConnectionModule connectionModule =
                new UrlConnectionModule(getString(R.string.daum_api_key), getString(R.string.daum_search_url), "GET", "다음");

        connectionModule.requestSearch("", "", null, new HttpCallbackListener() {
            @Override
            public void httpCallback(int responseCode, Object data) {

            }
        });

    }
}
