package com.java.zhuli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.zhuli.Models.APIResponse;
import com.java.zhuli.Models.Data;
import com.java.zhuli.db.DbHelper;
import com.java.zhuli.utils.DateToday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    SwipeRefreshLayout swipeRefreshLayout;
    static JSONArray obj_array = new JSONArray();
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    ProgressDialog load_dialog;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, add;
    Boolean bt1=true, bt2=true, bt3=true, bt4=true, bt5=true, bt6=true, bt7=true, bt8=true, bt9=true, bt10=true;
    SearchView searchView;
    String curr_query;
    String date_today = DateToday.returnDate();
    public String chosenCategory = "";
    List<Data> shownArticles;

    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* CREATE DATABASE */

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db != null){
            Toast.makeText(this, "DATABASE CREATED", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERROR CREATING DATABASE", Toast.LENGTH_LONG).show();
        }

        /* CREATE DATABASE */


        /* Initialize and assign variable */

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById((R.id.main_recycler));
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /*
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { // Check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
                            loading = true;
                        }
                    }
                }
            }
            */

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("Last date", DateToday.cutDate(adapter.getLastDate()));
                    downRequest(DateToday.cutDate(adapter.getLastDate()));

                    Log.d("Mytag", "Last");

                }
            }
        });


        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                curr_query = query;
                load_dialog.setTitle("搜索新闻。。。");
                load_dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, "2021-09-01", "2021-12-31", query, "", "None");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        load_dialog = new ProgressDialog(this);

        //Buttons
        {
            b1 = findViewById(R.id.btn_1);
            b1.setOnClickListener(this);
            b2 = findViewById(R.id.btn_2);
            b2.setOnClickListener(this);
            b3 = findViewById(R.id.btn_3);
            b3.setOnClickListener(this);
            b4 = findViewById(R.id.btn_4);
            b4.setOnClickListener(this);
            b5 = findViewById(R.id.btn_5);
            b5.setOnClickListener(this);
            b6 = findViewById(R.id.btn_6);
            b6.setOnClickListener(this);
            b7 = findViewById(R.id.btn_7);
            b7.setOnClickListener(this);
            b8 = findViewById(R.id.btn_8);
            b8.setOnClickListener(this);
            b9 = findViewById(R.id.btn_9);
            b9.setOnClickListener(this);
            b10 = findViewById(R.id.btn_10);
            b10.setOnClickListener(this);
            add = findViewById(R.id.category_add);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        /* API Request */
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "2019-09-01", date_today, "", "", "None");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upRequest();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        /* Bottom Navigation  */
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.personal:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.category_dialog);

        Button  btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btnok;

        //Initializing buttons
        {
            btn1 = dialog.findViewById(R.id.btn_yule);
            btn2 = dialog.findViewById(R.id.btn_junshi);
            btn3 = dialog.findViewById(R.id.btn_jiaoyu);
            btn4 = dialog.findViewById(R.id.btn_wenhua);
            btn5 = dialog.findViewById(R.id.btn_jiankang);
            btn6 = dialog.findViewById(R.id.btn_caijing);
            btn7 = dialog.findViewById(R.id.btn_tiyu);
            btn8 = dialog.findViewById(R.id.btn_qiche);
            btn9 = dialog.findViewById(R.id.btn_keji);
            btn10 = dialog.findViewById(R.id.btn_shehui);
            btnok = dialog.findViewById(R.id.btn_ok);
        }

        //Keep view of buttons
        {
            {
                if (!bt1) {
                    btn1.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn1.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn1.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn1.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }


            }
            {
                if (!bt2) {
                    btn2.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn2.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn2.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn2.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt3) {
                    btn3.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn3.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn3.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn3.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt4) {
                    btn4.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn4.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn4.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn4.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt5) {
                    btn5.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn5.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn5.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn5.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt6) {
                    btn6.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn6.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn6.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn6.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt7) {
                    btn7.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn7.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn7.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn7.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt8) {
                    btn8.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn8.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn8.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn8.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt9) {
                    btn9.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn9.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn9.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn9.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }
            {
                if (!bt10) {
                    btn10.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                    btn10.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));

                } else {
                    btn10.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                    btn10.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                }
            }

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt1) {
                        btn1.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn1.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt1 = true;
                        b1.setVisibility(View.VISIBLE);

                    } else {
                        btn1.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn1.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt1 = false;
                        b1.setVisibility(View.GONE);
                    }
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt2) {
                        btn2.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn2.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt2 = true;
                        b2.setVisibility(View.VISIBLE);

                    } else {
                        btn2.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn2.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt2 = false;
                        b2.setVisibility(View.GONE);
                    }
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt3) {
                        btn3.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn3.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt3 = true;
                        b3.setVisibility(View.VISIBLE);

                    } else {
                        btn3.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn3.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt3 = false;
                        b3.setVisibility(View.GONE);
                    }
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt4) {
                        btn4.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn4.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt4 = true;
                        b4.setVisibility(View.VISIBLE);

                    } else {
                        btn4.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn4.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt4 = false;
                        b4.setVisibility(View.GONE);
                    }
                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt5) {
                        btn5.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn5.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt5 = true;
                        b5.setVisibility(View.VISIBLE);

                    } else {
                        btn5.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn5.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt5 = false;
                        b5.setVisibility(View.GONE);
                    }
                }
            });
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt6) {
                        btn6.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn6.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt6 = true;
                        b6.setVisibility(View.VISIBLE);

                    } else {
                        btn6.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn6.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt6 = false;
                        b6.setVisibility(View.GONE);
                    }
                }
            });
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt7) {
                        btn7.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn7.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt7 = true;
                        b7.setVisibility(View.VISIBLE);

                    } else {
                        btn7.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn7.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt7 = false;
                        b7.setVisibility(View.GONE);
                    }
                }
            });
            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt8) {
                        btn8.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn8.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt8 = true;
                        b8.setVisibility(View.VISIBLE);

                    } else {
                        btn8.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn8.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt8 = false;
                        b8.setVisibility(View.GONE);
                    }
                }
            });
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt9) {
                        btn9.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn9.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt9 = true;
                        b9.setVisibility(View.VISIBLE);

                    } else {
                        btn9.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn9.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt9 = false;
                        b9.setVisibility(View.GONE);
                    }
                }
            });
            btn10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!bt10) {
                        btn10.setTextColor(getApplication().getResources().getColor(R.color.clear_bg));
                        btn10.setBackgroundColor(getApplication().getResources().getColor(R.color.vermilion));
                        bt10 = true;
                        b10.setVisibility(View.VISIBLE);

                    } else {
                        btn10.setTextColor(getApplication().getResources().getColor(R.color.vermilion));
                        btn10.setBackgroundColor(getApplication().getResources().getColor(R.color.clear_bg));
                        bt10 = false;
                        b10.setVisibility(View.GONE);
                    }
                }
            });
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private final OnFetchDataListener<APIResponse> listener = new OnFetchDataListener <APIResponse>() {
        @Override
        public void onFetchData(List<Data> list, String message, String option) {

            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
            }
            else {
                showNews(list, option);
                load_dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Data> list, String option) {
        if (option == "None"){
            shownArticles = list;
        } else {
            shownArticles.addAll(list);
        }
        adapter = new NewsAdapter(this, shownArticles, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(Data headlines) {
        startActivity(new Intent(MainActivity.this, DetailActivity.class)
        .putExtra("data", headlines));

        try {

            String temp = MyJSON.getData(this);
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
            //MyJSON.clear(this, obj_array.toString());
            MyJSON.saveData(this, obj_array.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        Button button = (Button) view;
        String category = button.getText().toString();

        resetButtons();

        load_dialog.setTitle("收集" + category + "新闻。。。");
        load_dialog.show();
        chosenCategory = category;

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"2019-08-20", date_today, "", category, "None");
        button.setBackgroundColor(getApplication().getResources().getColor(R.color.light_vermilion));
    }

    /* UTILS */
    public void upRequest(){
        load_dialog.setTitle("再刷新。。。");
        load_dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "2019-08-20", date_today, "", chosenCategory, "None");
    }

    public void downRequest(String date){
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "2019-08-20", date, "", chosenCategory, "Add");
    }

    public void resetButtons(){
        b1.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b2.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b3.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b4.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b5.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b6.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b7.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b8.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b9.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
        b10.setBackgroundColor(getApplication().getResources().getColor(R.color.lightest_vermilion));
    }
}








