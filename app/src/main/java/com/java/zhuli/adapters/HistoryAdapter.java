package com.java.zhuli.adapters;

import android.content.Context;
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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Data> model;

    //LISTENER
    private View.OnClickListener listener;

    public HistoryAdapter(Context context, ArrayList<Data> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.headlines_items, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = model.get(position).getTitle();
        String source = model.get(position).getPublisher();

        holder.title_text.setText(title);
        holder.source_text.setText(source);

        if (model.get(position).getImage() != ""){
            String img_url = model.get(position).getImage();
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
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);

        }
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
