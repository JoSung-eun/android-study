package com.example.nhnent.exercise4_search;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public class UrlConnectionModule {
    UrlConnectionModule(String apiKey, String url, String method, String query) {
        new ConnectionTask(apiKey, url, method, query).execute();
    }

    public void requestSearch(String url, String method, String query, HttpCallbackListener httpCallbackListener) {


    }

    private class ConnectionTask extends AsyncTask<Void, Void, Void> {
        private final String apiKey;
        private String url;
        private String method;
        private String query;
        private String result;

        ConnectionTask(String apiKey, String url, String method, String query) {
            this.apiKey = apiKey;
            this.url = url;
            this.method = method;
            this.query = query;
        }

        @Override
        protected void onPreExecute() { //통신 전 필요한 설정
            super.onPreExecute();
            try {
                String apiKeyQuery = "apikey=" + URLEncoder.encode(apiKey, "UTF-8");
                String searchQuery = "q=" + URLEncoder.encode(query, "UTF-8");
                String outputQuery = "output=" + URLEncoder.encode("json", "UTF-8");
                url = url + "?" + apiKeyQuery + "&" + searchQuery + "&" + outputQuery; //탐색하고 싶은 URL -> String.format으로 바꾸기
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) { //통신 부분
            try{
                URL url = new URL(this.url); // URL화 한다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL을 연결한 객체 생성.
                conn.setRequestMethod(method); // get방식 통신
                conn.setDoInput(true); // 읽기모드 지정
                conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
                conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

                Log.i("Response", conn.getResponseMessage());

                InputStream is = conn.getInputStream(); //input스트림 개방

                StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
                //string builder는 동기화 X string buffer가 thread safe
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8")); //문자열 셋 세팅
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line).append('\n');
                }

                result = builder.toString();

            }catch(IOException io){
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) { //통신 후 마무리 작
            super.onPostExecute(aVoid);
            Log.i("Network", result);
        }
    }

}
