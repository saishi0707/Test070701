package com.example.memoapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * DBに対する機能クラス
 * 登録、削除、検索
 */
public class SqlDBHelper extends SQLiteOpenHelper {

    private static final String tag = SqlDBHelper.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String MEMO_DB_NAME = "memoDB.db";

    private static List<MemoData> memoDataList;

    private static final String TABLE_NAME = "memoDb";
    private static final String _ID = "id";
    private static final String COLUMN_NAME_DATA_KEY = "dataKey";
    private static final String COLUMN_NAME_DATE = "date";
    private static final String COLUMN_NAME_GENRE = "genre";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_TEXT = "text";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_DATA_KEY + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_GENRE + " TEXT," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_TEXT + " TEXT)";

    private ContentValues createValue(MemoData data) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DATA_KEY, data.getKEY());
        values.put(COLUMN_NAME_DATE, data.getDATE());
        values.put(COLUMN_NAME_GENRE, data.getGENRE());
        values.put(COLUMN_NAME_TITLE, data.getTITLE());
        values.put(COLUMN_NAME_TEXT, data.getTEXT());
        Log.d(tag, "to value = " + values);
        return values;
    }

    public SqlDBHelper(@Nullable Context context) {
        super(context, MEMO_DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SQL_CREATE_ENTRIES
        );
        Log.d(tag, "start onCreate SqlDBHelper.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TESTアプリなので実装なし
    }

    public boolean addData(MemoData data, SQLiteDatabase db) {
        try {
            if (!db.isOpen()) {
                return false;
            }
            ContentValues values = createValue(data);
//            for (String key : values.keySet()) {
//                Log.d(tag, "======= key:" + key + " value:" + values.get(key));
//            }
            long result = db.insert(TABLE_NAME, null, values);
            Log.d(tag, "start onCreate SqlDBHelper.");
            if (result == 0) {
                Log.d(tag, "db insert false.");
                return false;
            } else {
                Log.d(tag, "db insert success. ");
                return true;
            }
        } catch (Exception e) {
            Log.d(tag, "db insert error." + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean delete(SQLiteDatabase db, String id) {
        try {
            if (!db.isOpen()) {
                Log.d(tag, "===== db is close.");
                return false;
            }
            String[] str = new String[]{id};
            int count = db.delete(TABLE_NAME, COLUMN_NAME_TITLE + "= ? ", str);
            if (count != 0) {
                Log.d(tag, "===== db is true. count = " + count);
                return true;
            } else {
                Log.d(tag, "===== db is false.");
                return false;
            }
        } catch (Exception e) {
            Log.d(tag, "===== db delete error." + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    private Boolean result;
    public List<MemoData> findAllData(SQLiteDatabase db) {
        memoDataList = new ArrayList<>();
        try {
            if (!db.isOpen()) {
                Log.d(tag, "===== db is close.");
                return null;
            }
            Cursor cursor;
            cursor = db.query(
                    TABLE_NAME,
                    new String[] {
                            COLUMN_NAME_DATA_KEY,
                            COLUMN_NAME_DATE,
                            COLUMN_NAME_GENRE,
                            COLUMN_NAME_TITLE,
                            COLUMN_NAME_TEXT
                    },
                    null,
                    null,
                    null,
                    null,
                    null
            );
            result = cursor.moveToFirst();
            while (result) {
                MemoData data = new MemoData();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    switch (cursor.getColumnName(i)) {
                        case COLUMN_NAME_DATA_KEY:
                            data.setKEY(cursor.getString(i));
                            break;
                        case COLUMN_NAME_DATE:
                            data.setDATE(cursor.getString(i));
                            break;
                        case COLUMN_NAME_GENRE:
                            data.setGENRE(cursor.getString(i));
                            break;
                        case COLUMN_NAME_TITLE:
                            data.setTITLE(cursor.getString(i));
                            break;
                        case COLUMN_NAME_TEXT:
                            data.setTEXT(cursor.getString(i));
                            break;
                        case _ID:
                            Log.d(tag, "===== _id " + cursor.getColumnName(i));
                        default:
                            Log.d(tag, "===== not found column." + cursor.getColumnName(i));
                            break;
                    }
                }
                memoDataList.add(data);
                result = cursor.moveToNext();
            }
            return memoDataList;
        } catch (Exception e) {
            Log.d(tag, "===== db findData error." + e.getMessage());
            return null;
        } finally {
            db.close();
        }
    }


}
