package com.example.nhnent.exercise4_search;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public class UrlConnectionModule {

    public static void requestSearch(final Context context, final String query, final HttpCallbackListener httpCallbackListener) {
        final String API_KEY = context.getString(R.string.daum_api_key);
        final String DAUM_URL = context.getString(R.string.daum_search_url);

        final Map<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("q", query);
        params.put("result", "20");
        params.put("pageno", "1");
        params.put("output", "json");

        new AsyncTask<Void, Void, String>() {
            StringBuilder urlBuilder = new StringBuilder();

            int responseCode;

            @Override
            protected void onPreExecute() {
                try {
                    urlBuilder.append(DAUM_URL).append("?");
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

            }

            @Override
            protected String doInBackground(Void... voids) { //TODO close
                String responseMessage = null;
                HttpURLConnection connection = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(urlBuilder.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    responseCode = connection.getResponseCode();
                    responseMessage = connection.getResponseMessage();

                    StringBuilder builder = new StringBuilder();

                    inputStreamReader = new InputStreamReader(connection.getInputStream(),"UTF-8");
                    bufferedReader = new BufferedReader(inputStreamReader); //todo

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line).append('\n');
                    }

                    return builder.toString();

                } catch(IOException io) {
                    io.printStackTrace();
                    return String.format(context.getString(R.string.search_error_message), responseCode, responseMessage);
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    httpCallbackListener.onSuccess(result);
                }
                else {
                    httpCallbackListener.onFail(result);
                }
            }
        }.execute();

    }

}
