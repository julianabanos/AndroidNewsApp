package com.java.zhuli;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.java.zhuli.Models.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    HistoryAdapter adapter;
    ArrayList<Data> saved_data = new ArrayList<Data>();
    RecyclerView savedView;
    Button clear;

    //references to communicate
    Activity activity;
    FragmentCommunicator interfaceCommunicator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        savedView = view.findViewById(R.id.saved_recycler);
        clear = view.findViewById(R.id.erase);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONSaved.clear(getContext(), "");
            }
        });
        loadList();
        showData();
        return view;
    }

    private void loadList() {
        int x = 0;
        String json = JSONSaved.getData(getActivity());
        System.out.println(json);
        try {
            JSONArray jsonObj = new JSONArray(json);
            for (int i= jsonObj.length()-1; i >= 0; i--){
                JSONObject Obj = (JSONObject) jsonObj.get(i);
                Data Item = new Data();
                Item.setImage((String) Obj.get("image"));
                Item.setTitle((String) Obj.get("title"));
                Item.setPublishTime((String) Obj.get("publishTime"));
                Item.setLanguage((String) Obj.get("language"));
                Item.setVideo((String) Obj.get("video"));
                Item.setContent((String) Obj.get("content"));
                Item.setUrl((String) Obj.get("url"));
                Item.setNewsID((String) Obj.get("newsID"));
                Item.setCrawlTime((String) Obj.get("crawlTime"));
                Item.setPublisher((String) Obj.get("publisher"));
                Item.setCategory((String) Obj.get("category"));

                for (int j=0; j < saved_data.size(); j++){
                    if (saved_data.get(j).getTitle().equals(Item.getTitle())){
                        x = 1;
                    }
                }
                if (x != 1){
                    saved_data.add(Item);
                }
                System.out.println(Item.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showData() {
        savedView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter(getContext(), saved_data);
        savedView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
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