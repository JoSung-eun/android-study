package com.example.nhnent.exercise4_search;

import com.google.gson.JsonArray;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public interface HttpCallbackListener {
    void onSuccess(String data);
    void onFail(String data);
}
