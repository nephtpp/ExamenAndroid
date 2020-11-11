package com.nep.examenandroid.data.network;

import com.nep.examenandroid.data.dto.Colaborador;
import com.nep.examenandroid.data.dto.FileResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface IConexion {
    String URL ="https://dl.dropboxusercontent.com/s/5u21281sca8gj94/" ;
//            "getFile.json?dl=0";
    @GET("getFile.json?dl=0")
    Call<FileResponse> getFile();

    String FILE_URL = "https://firebasestorage.googleapis.com/";// v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97";
    @Streaming
    @GET
    Call<ResponseBody> downloadFile( @Url String fileUrl);//
}

