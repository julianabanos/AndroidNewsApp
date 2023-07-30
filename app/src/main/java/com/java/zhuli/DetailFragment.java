package com.java.zhuli;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.zhuli.Models.Data;
import com.java.zhuli.R;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    TextView text_title, text_author, text_time, text_content;
    ImageView img_headline;
    VideoView vid_news;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_viewed_detail, container, false);
        text_title = view.findViewById(R.id.detail_text_title);
        text_author = view.findViewById(R.id.detail_text_author);
        text_time = view.findViewById(R.id.detail_text_time);
        text_content = view.findViewById(R.id.detail_text_content);
        img_headline = view.findViewById(R.id.detail_img_news);
        vid_news = view.findViewById(R.id.video_news);

        Bundle dataObject = getArguments();
        Data data = null;

        //verify
        if (dataObject != null){
            data = (Data) dataObject.getSerializable("object");
            text_title.setText(data.getTitle());
            text_author.setText(data.getPublisher());
            text_time.setText(data.getPublishTime());
            text_content.setText(data.getContent());

            String video_url;
            String img_url;

            if (data.getVideo() != ""){

                video_url = data.getVideo();
                Uri uri = Uri.parse(video_url);
                vid_news.setVideoURI(uri);
                MediaController mediaController = new MediaController(getContext());
                mediaController.setAnchorView(vid_news);
                mediaController.setMediaPlayer(vid_news);
                vid_news.setMediaController(mediaController);
                vid_news.start();
            }
            if (data.getImage() != ""){
                img_url = data.getImage();
                img_url = img_url.substring(1, img_url.length() -1);
                String[] imageLinkArr = img_url.split(",");
                String img = imageLinkArr[0];
                if (!img.isEmpty()){
                    Picasso.get().load(img).into(img_headline);
                }
            }
        }
        return view;
    }
}
