package com.example.memoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.memoapp.DB.MemoData;
import com.example.memoapp.DB.SqlDBManager;
import com.example.memoapp.Impl.ListViewAdapter;
import com.example.memoapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.memoapp.R.id.add_btn;
import static com.example.memoapp.R.id.delete_btn;
import static com.example.memoapp.R.id.imageView;

public class MainActivity extends BaseActivity
        implements AdapterView.OnItemClickListener {

    private final String tag = MainActivity.class.getSimpleName();
    private SqlDBManager manager;
    private ListView memoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "===== onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        manager = new SqlDBManager(getApplicationContext());
    }

    @Override
    protected void onStart() {
        Log.d(tag, "===== onStart start");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(tag, "===== onResume start");

        Button button1 = findViewById(add_btn);
        Button button2 = findViewById(delete_btn);
        ImageView imageView = findViewById(R.id.imageView);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        imageView.setOnClickListener(onClickListener);

        memoListView = findViewById(R.id.todoListView);


        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private View.OnClickListener onClickListener = v -> {

        switch (v.getId()) {

            case add_btn:
                Log.d(tag, "===== add_btn click");
                intentReg();
                //Boolean result = manager.add(testData());
                //Log.d(tag, "===== add result " + result);
                break;

            case delete_btn:
                Log.d(tag, "===== delete_btn click");
                List<MemoData> dataList = manager.allFind();

                Log.d(tag, "===== size = " + dataList.size());
                int a = 0;
                for (MemoData data : dataList) {
                    Log.d(tag, "===== count = " + a++);
                    Log.d(tag, "===== result key =" + data.getKEY());
                    Log.d(tag, "===== result date =" + data.getDATE());
                    Log.d(tag, "===== result genre =" + data.getGENRE());
                    Log.d(tag, "===== result title =" + data.getTITLE());
                    Log.d(tag, "===== result text =" + data.getTEXT());
                }
                break;

            case imageView:
                updateListView();
                break;
            default:
                Log.d(tag, "===== default");
                break;

        }
    };

    private void intentReg() {
        Intent i = new Intent(getApplication(), RegisterActivity.class);
        i.setAction(Intent.ACTION_VIEW);
        startActivity(i);
    }

    private void updateListView() {

        List<String> titles = new ArrayList<>();
        List<String> memos = new ArrayList<>();
        List<MemoData> dataList = manager.allFind();
        if (dataList.size() == 0) {
            return;
        }
        for (MemoData data : dataList) {

            Log.d(tag, "===== result key =" + data.getKEY());
            Log.d(tag, "===== result date =" + data.getDATE());
            Log.d(tag, "===== result genre =" + data.getGENRE());
            Log.d(tag, "===== result title =" + data.getTITLE());
            titles.add(data.getDATE());
            Log.d(tag, "===== result text =" + data.getTEXT());
            memos.add(data.getTITLE());
        }


        BaseAdapter adapter = new ListViewAdapter(this.getApplicationContext(),
                R.layout.item_list_view, titles, memos);

        // ListViewにadapterをセット
        memoListView.setAdapter(adapter);

        // クリックリスナーをセット
        memoListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Intent intent = new Intent(
                this.getApplicationContext(), ItemActivity.class);

        List<MemoData> dataList = manager.allFind();
        MemoData data = dataList.get(position);
        String title = data.getTITLE();
        String memo = data.getTEXT();
        String date = data.getDATE();
        // インテントにセット
        intent.putExtra("title", title);
        intent.putExtra("memo", memo);
        intent.putExtra("date", date);
        intent.putExtra("position", position);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}