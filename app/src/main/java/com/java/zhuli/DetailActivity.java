package com.java.zhuli;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.java.zhuli.Models.Data;
import com.java.zhuli.db.DbArticles;
import com.java.zhuli.db.DbHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    Data headlines;
    TextView art_title, art_author, art_time, art_content;
    ImageView art_img;
    String video_url;
    String img_url;
    VideoView video_view;
    Button save;
    Boolean saved;
    static JSONArray obj_array = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        save = findViewById(R.id.save_button);
        video_view = findViewById(R.id.video_news);
        video_url = "";


        art_title = findViewById(R.id.detail_text_title);
        art_author = findViewById(R.id.detail_text_author);
        art_time = findViewById(R.id.detail_text_time);
        art_content = findViewById(R.id.detail_text_content);
        art_img = findViewById(R.id.detail_img_news);

        headlines = (Data) getIntent().getSerializableExtra("data");

        // Set as saved
        saved = headlines.getSaved();
        if (saved == true){
            save.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        }
        else {
            save.setBackgroundColor(getApplication().getResources().getColor(R.color.light_vermilion));
        }

        art_title.setText(headlines.getTitle());
        art_author.setText(headlines.getPublisher());
        art_time.setText(headlines.getPublishTime());
        art_content.setText(headlines.getContent());
        if (headlines.getVideo() != ""){

            video_url = headlines.getVideo();
            Uri uri = Uri.parse(video_url);
            video_view.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(video_view);
            mediaController.setMediaPlayer(video_view);
            video_view.setMediaController(mediaController);
            video_view.start();
        }
        if (headlines.getImage() != ""){
            img_url = headlines.getImage();
            img_url = img_url.substring(1, img_url.length() -1);
            String[] imageLinkArr = img_url.split(",");
            String img = imageLinkArr[0];
            if (!img.isEmpty()){
                Picasso.get().load(img).into(art_img);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (saved == true){
                    headlines.setSaved();
                    save.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
                }
                else{
                    headlines.setSaved();
                }

                /* DATABASE */
                DbArticles dbArticles = new DbArticles(DetailActivity.this);
                long id = dbArticles.insertArticle(headlines.getImage(), headlines.getTitle(), headlines.getPublishTime(), headlines.getLanguage(), headlines.getVideo(), headlines.getContent(), headlines.getUrl(), headlines.getNewsID(), headlines.getCrawlTime(), headlines.getPublisher(), headlines.getCategory(), headlines.getSaved());
                if (id > 0){
                    Toast.makeText(DetailActivity.this, "Article Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Error Saving", Toast.LENGTH_LONG).show();
                }

                /* DATABASE */

                /*
                try {

                    String temp = JSONSaved.getData(DetailActivity.this);
                    if (!temp.isEmpty()) {
                        obj_array = new JSONArray(temp);
                    }
                    JSONObject obj = new JSONObject();
                    obj.put("image", headlines.getImage());
                    obj.put("title", headlines.getTitle());
                    obj.put("publishTime", headlines.getPublishTime());
                    obj.put("language", headlines.getLanguage());
                    obj.put("video", headlines.getVideo());
                    obj.put("content", headlines.getContent());
                    obj.put("url", headlines.getUrl());
                    obj.put("newsID", headlines.getNewsID());
                    obj.put("crawlTime", headlines.getCrawlTime());
                    obj.put("publisher", headlines.getPublisher());
                    obj.put("category", headlines.getCategory());
                    obj_array.put(obj);
                    //JSONSaved.clear(DetailActivity.this, obj_array.toString());
                    JSONSaved.saveData(DetailActivity.this, obj_array.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 */
            }
        });


    }

}