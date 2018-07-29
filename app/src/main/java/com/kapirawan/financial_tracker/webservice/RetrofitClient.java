package com.kapirawan.financial_tracker.webservice;

import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.43.46:8080";
    private static String token = "";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request().newBuilder();
                                ongoing.addHeader("Authorization", "Bearer " + token );
                                return chain.proceed(ongoing.build());
                            }
                        })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    public static void setToken(String tok){
        token = tok;
    }
}
