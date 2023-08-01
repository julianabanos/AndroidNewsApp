package com.java.zhuli;

import android.content.Context;
import android.widget.Toast;

import com.java.zhuli.Models.APIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api2.newsminer.net/svc/news/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getNewsHeadlines(OnFetchDataListener listener, String startDate, String endDate, String words, String categories, String option)
    {
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<APIResponse> call = callNewsApi.callHeadlines("15", startDate, endDate, words, categories);

        try {
            call.enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getData(), response.message(), option);
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    listener.onError("Request Failed!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("queryNewsList")
        Call<APIResponse> callHeadlines(
            @Query("size") String size,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("words") String words,
            @Query("categories") String categories

        );
    }
}
