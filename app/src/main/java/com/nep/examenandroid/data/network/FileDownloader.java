package com.nep.examenandroid.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class FileDownloader {

    public static final String API_BASE_URL = IConexion.FILE_URL;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL);
//                    .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass){
        Retrofit retrofit = builder.client(httpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}
