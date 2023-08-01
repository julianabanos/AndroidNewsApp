package com.java.zhuli.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.zhuli.Models.Data;
import com.java.zhuli.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    ArrayList<Data> articleList;

    public ArticleListAdapter(ArrayList<Data> articleList){
        this.articleList = articleList;
    }
    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title_text, source_text;
        ImageView headline_img;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text = itemView.findViewById(R.id.title_text);
            source_text = itemView.findViewById(R.id.source_text);
            headline_img = itemView.findViewById(R.id.headline_image);
        }
    }
    @NonNull
    @Override
    public ArticleListAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headlines_items, null, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListAdapter.ArticleViewHolder holder, int position) {

        holder.title_text.setText(articleList.get(position).getTitle());
        holder.source_text.setText(articleList.get(position).getPublisher());

        if(!articleList.get(position).getImage().isEmpty()){
            String img_url = articleList.get(position).getImage();
            img_url = img_url.substring(1, img_url.length() -1);
            String[] imageLinkArr = img_url.split(",");
            String img = imageLinkArr[0];
            if (!img.isEmpty()){
                Picasso.get().load(img).into(holder.headline_img);
            }
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


}
