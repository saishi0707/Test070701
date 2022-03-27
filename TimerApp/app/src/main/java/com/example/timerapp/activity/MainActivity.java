package com.example.timerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timerapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String tag = MainActivity.class.getSimpleName();

    private Button stopOrStartBtn;
    private Button rapBtn;
    private Button resetBtn;
    private TextView countTime;
    private ListView rapListView;
    private long TIME_MILLISECOND = 100L;
    private long time = 0L;
    private Boolean isStopFlag = false;
    private String nowTime;
    private List<String> rapList = new ArrayList<>();

    private Handler timerHandler;
    private Handler textHandler;
    private SimpleDateFormat dataFormat;
    private ListAdapter adapter;

    private Runnable timerRunner = new Runnable() {
        @Override
        public void run() {
            time += TIME_MILLISECOND;
            nowTime = dataFormat.format(time);
            countTime.setText(nowTime);
            timerHandler.postDelayed(this, TIME_MILLISECOND);
        }
    };

    private Runnable setTextRunner = new Runnable() {
        @Override
        public void run() {
            if (isStopFlag) {
                stopOrStartBtn.setText(R.string.name_button_start);
                isStopFlag = false;
            } else {
                stopOrStartBtn.setText(R.string.name_button_stop);
                isStopFlag = true;
            }
        }
    };

    private void init() {
        stopOrStartBtn = findViewById(R.id.button_stop_or_start);
        rapBtn = findViewById(R.id.button_rap);
        resetBtn = findViewById(R.id.button_reset);
        countTime = findViewById(R.id.time);
        rapListView = findViewById(R.id.listView);

        timerHandler = new Handler(Looper.getMainLooper());
        textHandler = new Handler(Looper.getMainLooper());

        dataFormat = new SimpleDateFormat("mm:ss.S");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "onCreate start");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "onStart start");
    }

    @Override
    protected void onResume() {
        super.onResume();

        stopOrStartBtn.setOnClickListener(clickListener);
        rapBtn.setOnClickListener(clickListener);
        resetBtn.setOnClickListener(clickListener);

        Log.d(tag, "onResume start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "onStop start");
    }

    private View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.button_stop_or_start:
                Log.d(tag, "button_stop_or_start start");
                if (isStopFlag) {
                    stopTimer();
                } else {
                    startTimer();
                }
                textHandler.post(setTextRunner);
                break;
            case R.id.button_rap:
                Log.d(tag, "button_rap start");
                String t = nowTime;
                rapList.add(t);
                adapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        rapList);
                rapListView.setAdapter(adapter);
                break;
            case R.id.button_reset:
                Log.d(tag, "button_reset start");
                stopTimer();
                textHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopOrStartBtn.setText(R.string.name_button_start);
                        isStopFlag = false;
                        countTime.setText(R.string.text_def_time);
                    }
                });
                rapList.clear();
                adapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        rapList);
                rapListView.setAdapter(adapter);
                time = 0L;
                break;
            default:
                break;
        }
    };

    private void startTimer() {
        // 経過時間
        timerHandler.post(timerRunner);
    }

    private void stopTimer() {
        try {
            timerHandler.removeCallbacks(timerRunner);
        } catch (Exception e) {
            Log.d(tag, "timerHandler.wait() error " + e.getMessage());
        }
    }
}