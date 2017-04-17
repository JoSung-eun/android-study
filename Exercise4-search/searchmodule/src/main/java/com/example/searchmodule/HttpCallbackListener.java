package com.example.searchmodule;

/**
 * Created by nhnent on 2017. 4. 12..
 */

public interface HttpCallbackListener {
    void onSuccess(String data);
    void onFail(String data);
}
