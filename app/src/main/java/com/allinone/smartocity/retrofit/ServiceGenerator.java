package com.allinone.smartocity.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;


public class ServiceGenerator {


    private static OkHttpClient getClient() {


        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();


    }


    public static <S> S createService(Class<S> serviceClass, String baseurl) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}