package com.example.nhnent.networkstudy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private String strUrl;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() { //통신 전 필요한 설정
                super.onPreExecute();
                try {
                    String apikeyQuery = "apikey=" + URLEncoder.encode("53946c1661edc714a895caebcc092b31", "UTF-8");
                    String searchQuery = "q=" + URLEncoder.encode("다음카카오", "UTF-8");
                    String outputQuery = "output=" + URLEncoder.encode("json", "UTF-8");
                    strUrl = "https://apis.daum.net/search/web?" + apikeyQuery + "&" + searchQuery + "&" + outputQuery; //탐색하고 싶은 URL
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Void doInBackground(Void... voids) { //통신 부분
                try{
                    URL url = new URL(strUrl); // URL화 한다.
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL을 연결한 객체 생성.
                    conn.setRequestMethod("GET"); // get방식 통신
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
        }.execute();
    }
}
