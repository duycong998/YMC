package com.example.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    //màn hình , tên data, con trỏ(để trả kết quả), phiên bản
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy vấn không trả kết quả : CREATE, INSERT, UPDATE, DELETE...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase(); //ghi dữ liệu
        database.execSQL(sql);
    }

    //truy vấn trả kết quả : SELECT
    public Cursor GetDate(String sqll){
        SQLiteDatabase database = getReadableDatabase();// đọc data
        return database.rawQuery(sqll,null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
