package com.example.searchmodule;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public class HttpUrlConnectionModule {
    private static final String API_KEY = "53946c1661edc714a895caebcc092b31";
    private static final String DAUM_URL = "https://apis.daum.net/search";
    private static final int MAX_RESULT_COUNT = 20;
    private static final String OUTPUT_JSON = "json";

    private static final String JSON_ERROR_FORMAT = "{'responseCode':%d, 'message':'%s'}";

    public static void requestDaumSearch(String path, String query, int pageNo, HttpCallbackListener httpCallbackListener) {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("q", query);
        params.put("result", String.valueOf(MAX_RESULT_COUNT));
        params.put("pageno", String.valueOf(pageNo));
        params.put("output", OUTPUT_JSON);

        getRequest(path, params, httpCallbackListener);
    }

    private static void getRequest(final String path, final Map<String, String> params, final HttpCallbackListener httpCallbackListener) {
        new AsyncTask<Void, Void, String>() {
            StringBuilder urlBuilder = new StringBuilder();

            int responseCode;

            @Override
            protected void onPreExecute() {
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

            }

            @Override
            protected String doInBackground(Void... voids) {
                String responseMessage = null;

                HttpURLConnection connection = null;
                ByteArrayOutputStream outputStream = null;
                InputStream inputStream = null;

                try{
                    URL url = new URL(urlBuilder.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    responseCode = connection.getResponseCode();
                    responseMessage = connection.getResponseMessage();

                    StringBuilder result = new StringBuilder();

                    inputStream = new BufferedInputStream(connection.getInputStream());
                    outputStream = new ByteArrayOutputStream();

                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }

                    result.append(new String(outputStream.toByteArray()));
                    outputStream.flush();

                    return result.toString();

                } catch(IOException io) {
                    io.printStackTrace();
                    return String.format(Locale.US, JSON_ERROR_FORMAT, responseCode, responseMessage);
                } finally {
                    closeStream(inputStream, outputStream, connection);
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

            private void closeStream(InputStream inputStream, OutputStream outputStream, HttpURLConnection connection) {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }

}
