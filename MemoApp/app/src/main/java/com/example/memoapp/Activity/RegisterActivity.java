package com.example.memoapp.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.memoapp.DB.MemoData;
import com.example.memoapp.DB.SqlDBManager;
import com.example.memoapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends BaseActivity implements TextWatcher {
    private final String tag = RegisterActivity.class.getSimpleName();
    private SqlDBManager manager;

    private Button regBtn;
    private EditText title;
    private EditText text;

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.register) {
                String titleText = title.getText().toString();
                String memoText = text.getText().toString();
                MemoData data = createAddData(titleText, memoText, "test");
                Boolean result = manager.add(data);
                if (result) {
                    Log.d(tag, "===== register true");
                    onBackPressed();
                } else {
                    Log.d(tag, "===== register false");
                }
            } else {
                Log.d(tag, "===== default");
            }
        }
    };

    private void init() {
        title = findViewById(R.id.title_text);
        text = findViewById(R.id.memo_text);
        title.addTextChangedListener(this);
        text.addTextChangedListener(this);
        regBtn = findViewById(R.id.register);
        regBtn.setOnClickListener(listener);
        regBtn.setEnabled(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new SqlDBManager(getApplicationContext());
        setContentView(R.layout.activity_register_view);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (title != null && text != null) {
            String titleText = title.getText().toString();
            String memoText = text.getText().toString();
            Log.d(tag, "===== title= " + titleText + " memo= " + memoText);
            regBtn.setEnabled(titleText.length() != 0 && memoText.length() != 0);
        } else {
            Log.d(tag, "===== text is null");
        }
    }

    private MemoData createAddData(
            String title,
            String text,
            String genre) {
        MemoData data = new MemoData();

        Date nowDate = new Date();
        // 表示形式を指定
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 =
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatNowDate = sdf1.format(nowDate);
        data.setTITLE(title);
        data.setTEXT(text);
        data.setGENRE(genre);
        data.setDATE(formatNowDate);
        data.setKEY("test");
        return data;
    }
}
