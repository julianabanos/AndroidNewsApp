package com.java.zhuli.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vermillion.db";
    public static final String TABLE_ARTICLES = "t_articles";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "image TEXT NOT NULL," +
                "title TEXT," +
                "publishTime TEXT," +
                "language TEXT," +
                "video TEXT," +
                "content TEXT," +
                "url TEXT," +
                "newsID TEXT UNIQUE," +
                "crawlTime TEXT," +
                "publisher TEXT," +
                "category TEXT," +
                "saved BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_ARTICLES);
        onCreate(db);

    }
}
