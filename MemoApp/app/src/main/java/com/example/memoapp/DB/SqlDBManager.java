package com.example.memoapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.memoapp.Impl.AndroidApplication;

import java.util.List;

/**
 * SqlDBHelperにアクセスするクラス
 * このクラス以外はSqlDBHelperにアクセスしない
 */
public class SqlDBManager {

    private final SqlDBHelper helper;
    private final Context appContext;
    public SqlDBManager(Context context) {
        appContext = context;
        helper = new SqlDBHelper(appContext);
    }

    public Boolean add(MemoData data) {
        SQLiteDatabase database = helper.getWritableDatabase();
        return helper.addData(data, database);
    }

    public List<MemoData> allFind() {
        SQLiteDatabase database = helper.getWritableDatabase();
        return helper.findAllData(database);
    }

    public Boolean delete(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();
        return helper.delete(database, id);
    }
}
