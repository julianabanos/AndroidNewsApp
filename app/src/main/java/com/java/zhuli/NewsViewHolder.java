package com.java.zhuli;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder{
    TextView title_text, source_text;
    ImageView headline_img;
    CardView cardView;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        title_text = itemView.findViewById(R.id.title_text);
        source_text = itemView.findViewById(R.id.source_text);
        headline_img = itemView.findViewById(R.id.headline_image);
        cardView = itemView.findViewById(R.id.main_container);
    }
}
