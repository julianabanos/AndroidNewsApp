package com.java.zhuli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.java.zhuli.Models.Data;

import java.util.ArrayList;

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

    public ArrayList<Data> showArticles(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Data> articleList = new ArrayList<>();
        Data article = null;
        Cursor articleCursor = null;

        articleCursor = db.rawQuery("SELECT * FROM " + TABLE_ARTICLES, null);

        if (articleCursor.moveToFirst()){
            do {
                article = new Data();
                article.setImage(articleCursor.getString(0));
                article.setTitle(articleCursor.getString(1));
                article.setPublishTime(articleCursor.getString(2));
                article.setLanguage(articleCursor.getString(3));
                article.setVideo(articleCursor.getString(4));
                article.setContent(articleCursor.getString(5));
                article.setUrl(articleCursor.getString(6));
                article.setNewsID(articleCursor.getString(7));
                article.setCrawlTime(articleCursor.getString(8));
                article.setPublisher(articleCursor.getString(9));
                article.setCategory(articleCursor.getString(10));
                /* Boolean saved */
                boolean value = false;
                if (articleCursor.getInt(11) == 1){
                    value = true;
                }
                article.setSaved(value);
                articleList.add(article);
            } while (articleCursor.moveToNext());

        }

        articleCursor.close();
        return articleList;

    }
}
