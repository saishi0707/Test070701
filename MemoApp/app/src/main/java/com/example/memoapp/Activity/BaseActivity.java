package com.example.memoapp.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate start.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart start.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume start.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop start.");
    }
}
