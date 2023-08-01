package com.java.zhuli;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.zhuli.Models.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder>{
    private Context context;
    private List<Data> headlines;
    private SelectListener listener;

    public NewsAdapter(Context context, List<Data> headlines, SelectListener listener) {
        this.context = context;
        this.listener = listener;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.headlines_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        holder.title_text.setText(headlines.get(position).getTitle());
        holder.source_text.setText(headlines.get(position).getPublisher());

        if (headlines.get(position).getImage() != ""){
            String img_url = headlines.get(position).getImage();
            img_url = img_url.substring(1, img_url.length() -1);
            String[] imageLinkArr = img_url.split(",");
            String img = imageLinkArr[0];
            if (!img.isEmpty()){
                Picasso.get().load(img).into(holder.headline_img);
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(headlines.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public String getLastDate(){
        return headlines.get(headlines.size()-1).getPublishTime();
    }
}
