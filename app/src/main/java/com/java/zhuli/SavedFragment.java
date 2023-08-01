package com.java.zhuli;

import static com.java.zhuli.db.DbHelper.TABLE_ARTICLES;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.java.zhuli.Models.Data;
import com.java.zhuli.adapters.ArticleListAdapter;
import com.java.zhuli.adapters.HistoryAdapter;
import com.java.zhuli.adapters.SavedAdapter;
import com.java.zhuli.db.DbArticles;
import com.java.zhuli.db.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    SavedAdapter saved_adapter;
    ArrayList<Data> saved_data = new ArrayList<Data>();
    RecyclerView savedView;
    Button clear;
    Activity activity;
    FragmentCommunicator interfaceCommunicator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        savedView = view.findViewById(R.id.saved_recycler);

        clear = view.findViewById(R.id.erase);

        /* Clear all saved from database

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONSaved.clear(getContext(), "");
            }
        });

        */

        loadList();
        Log.d("myTag", "List Loaded");
        showData();
        return view;
    }

    private void loadList() {

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor articleCursor = null;

        articleCursor = db.rawQuery("SELECT * FROM " + TABLE_ARTICLES, null);

        if (articleCursor.moveToFirst()){
            int x = 0;
            do {
                Data article = new Data();
                article.setImage(articleCursor.getString(1));
                Log.d("Image: ", articleCursor.getString(1));
                article.setTitle(articleCursor.getString(2));
                article.setPublishTime(articleCursor.getString(3));
                article.setLanguage(articleCursor.getString(4));
                article.setVideo(articleCursor.getString(5));
                article.setContent(articleCursor.getString(6));
                article.setUrl(articleCursor.getString(7));
                article.setNewsID(articleCursor.getString(8));
                article.setCrawlTime(articleCursor.getString(9));
                article.setPublisher(articleCursor.getString(10));
                article.setCategory(articleCursor.getString(11));
                /* Boolean saved */
                boolean value = false;
                if (articleCursor.getInt(12) == 1){
                    value = true;
                }
                article.setSaved(value);

                for (int j=0; j < saved_data.size(); j++){
                    if (saved_data.get(j).getTitle().equals(article.getTitle())){
                        x = 1;
                    }
                }
                if (x != 1){
                    saved_data.add(article);
                }

            } while (articleCursor.moveToNext());

        }

        articleCursor.close();

        Log.d("List", saved_data.toString());

    }

    private void showData() {
        savedView.setLayoutManager(new LinearLayoutManager(getContext()));
        saved_adapter = new SavedAdapter(getContext(), saved_data);
        savedView.setAdapter(saved_adapter);

        saved_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceCommunicator.sendObject(saved_data.get(savedView.getChildAdapterPosition(view)));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity = (Activity) context;
            interfaceCommunicator = (FragmentCommunicator) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}