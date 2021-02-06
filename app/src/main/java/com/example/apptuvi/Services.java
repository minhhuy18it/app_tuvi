package com.example.apptuvi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 *
 */
public class Services {
    public static final String BASE_URL = "https://quanlytuvi.000webhostapp.com/";
    private ServicesApi retrofit;

    public Services() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        this.retrofit = retrofit.create(ServicesApi.class);
    }

    public ServicesApi getService() {
        return this.retrofit;
    }
}
