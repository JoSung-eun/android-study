// ICount.aidl
package com.example.nhnent.servicestudy;

// Declare any non-default types here with import statements
import com.example.nhnent.servicestudy.ICountCallback;

interface ICount {
    oneway void getCurNumber(ICountCallback callback);
}