package com.java.zhuli;

import com.java.zhuli.Models.Data;

import java.util.List;

public interface OnFetchDataListener<APIResponse> {
    void onFetchData(List<Data> list, String message, String option);
    void onError(String Message);

}
