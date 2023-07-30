package com.java.zhuli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbArticles extends DbHelper{

    Context context;
    public DbArticles(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertArticle(String image, String title, String publishTime, String language, String video, String content, String url, String newsID, String crawlTime, String publisher, String category, Boolean saved){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("image", image);
            values.put("title", title);
            values.put("publishTime", publishTime);
            values.put("language", language);
            values.put("video", video);
            values.put("content", content);
            values.put("url", url);
            values.put("newsID", newsID);
            values.put("crawlTime", crawlTime);
            values.put("publisher", publisher);
            values.put("category", category);
            values.put("saved", saved);

            id = db.insert(TABLE_ARTICLES, null, values);

        } catch(Exception ex){
            ex.toString();
        }

        return id;
    }
}
