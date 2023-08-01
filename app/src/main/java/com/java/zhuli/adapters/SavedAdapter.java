package com.java.zhuli.adapters;

import android.content.Context;
import android.util.Log;
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

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> implements View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<Data> saved_articles;
    private View.OnClickListener listener;

    public SavedAdapter(Context context, ArrayList<Data> saved_articles){
        this.inflater = LayoutInflater.from(context);
        this.saved_articles = saved_articles;

    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.headlines_items, parent, false);
        view.setOnClickListener(this);
        return new SavedAdapter.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        String title = saved_articles.get(position).getTitle();
        String source = saved_articles.get(position).getPublisher();

        holder.title_text.setText(title);
        holder.source_text.setText(source);

        if (saved_articles.get(position).getImage() != ""){
            String img_url = saved_articles.get(position).getImage();
            Log.d("BindViewHolder", img_url);
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
        return saved_articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_text, source_text;
        ImageView headline_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text = itemView.findViewById(R.id.title_text);
            source_text = itemView.findViewById(R.id.source_text);
            headline_img = itemView.findViewById(R.id.headline_image);
        }
    }
}
