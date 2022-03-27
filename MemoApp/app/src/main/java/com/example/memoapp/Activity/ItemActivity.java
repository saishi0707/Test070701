package com.example.memoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.memoapp.DB.SqlDBManager;
import com.example.memoapp.R;

public class ItemActivity extends BaseActivity {

    private final String tag = ItemActivity.class.getSimpleName();
    private SqlDBManager manager;
    private TextView titleView;
    private TextView memoView;
    private TextView dateView;
    private Button deleteBtn;

    private Intent intent;

    private void init() {
        titleView = findViewById(R.id.textView_title);
        memoView = findViewById(R.id.textView_memo);
        dateView = findViewById(R.id.textView_date);
        manager = new SqlDBManager(getApplicationContext());
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_delete) {
                String position = intent.getStringExtra("title");
                boolean result = manager.delete(position);
                if (result) {
                    onBackPressed();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        init();


        deleteBtn = findViewById(R.id.button_delete);
        deleteBtn.setOnClickListener(listener);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        String memo = intent.getStringExtra("memo");
        String date = intent.getStringExtra("date");
        Handler mHandler = new Handler(Looper.getMainLooper());

        mHandler.post(() -> {

            titleView.setText(title);
            dateView.setText(date);
            memoView.setText(memo);
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
