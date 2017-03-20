package com.example.nhnent.activitylifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    static final String TAG = "ActParent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        Log.i(TAG, "startActivity");
        Intent intent = new Intent(this, ActChild.class);
        startActivity(intent);
    }
    public static class CounterFragment extends Fragment {
        static final String TAG_FRAGMENT = "Fragment";
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment, container, false);
            Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
            final TextView textCounter = (TextView)root.findViewById(R.id.txtcounter);
            btnIncrease.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(textCounter.getText().toString());
                    textCounter.setText(Integer.toString(count + 1));
                }
            });
            Log.i(TAG_FRAGMENT, "onCreateView");
            return root;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.i(TAG_FRAGMENT, "onAttach");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i(TAG_FRAGMENT, "onCreate");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.i(TAG_FRAGMENT, "onActivityCreated");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.i(TAG_FRAGMENT, "onStart");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.i(TAG_FRAGMENT, "onResume");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i(TAG_FRAGMENT, "onPause");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.i(TAG_FRAGMENT, "onStop");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.i(TAG_FRAGMENT, "onDestroyView");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i(TAG_FRAGMENT, "onDestroy");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.i(TAG_FRAGMENT, "onDetach");
        }
    }

    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
