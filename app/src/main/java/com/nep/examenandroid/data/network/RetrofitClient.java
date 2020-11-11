package com.nep.examenandroid.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private IConexion conexion;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IConexion.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        conexion = retrofit.create(IConexion.class);
    }

    public static synchronized RetrofitClient getInstance(){
        if (instance == null){
            instance= new RetrofitClient();
        }
        return instance;
    }

    public IConexion getMyConexion(){
        return conexion;
    }

}
