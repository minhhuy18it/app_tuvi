package com.example.apptuvi;


import com.example.apptuvi.model.Code;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicesApi {

    @GET("/api_code.php/{code}")
    Call<ResponseBody> nhapcode(@Query("code") String code);


}
