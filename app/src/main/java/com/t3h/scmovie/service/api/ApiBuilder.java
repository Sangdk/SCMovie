package com.t3h.scmovie.service.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static Api api;
    public static Api getApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);
        }
        return api;
    }
}
