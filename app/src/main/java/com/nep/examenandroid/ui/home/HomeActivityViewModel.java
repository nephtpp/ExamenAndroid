package com.nep.examenandroid.ui.home;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nep.examenandroid.Utils;
import com.nep.examenandroid.data.dto.FileResponse;
import com.nep.examenandroid.data.dto.User;
import com.nep.examenandroid.data.dto.employees.Data;
import com.nep.examenandroid.data.dto.employees.Employee;
import com.nep.examenandroid.data.dto.employees.Users;
import com.nep.examenandroid.data.repositories.Repo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivityViewModel extends ViewModel {
    public final static String TAG = "home";
    private Repo repository;
    private MutableLiveData<FileResponse> fileResponseMutableLiveData;
    private MutableLiveData<ArrayList<Employee>> mutListUser;

    public HomeActivityViewModel(Repo repository) {
        this.repository = repository;
        fileResponseMutableLiveData = new MutableLiveData<>();

        mutListUser = new MutableLiveData<>();
        mutListUser.setValue(new ArrayList<>());
    }

    public void getFile(){
        fileResponseMutableLiveData = (repository.getFile());
        Log.i(TAG,"files");
    }

    public void downloadFile(Context context, String url){
//        repository.downloadFile(context,url);
        try {
            repository.downloadFileFromFirebase(url,context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        repository.getMyFile().observeForever(file->{
            if(file != null){
//                Utils.fileToString(file);
                Gson gson = new Gson();
                String js = Utils.fileToString(file);

                Type listType =  new TypeToken<List<Employee>>() {}.getType();
               Users employees =  gson.fromJson(js,Users.class);// listType
                Log.i(TAG,"uers"+employees.getData().getEmployees().get(0).getName());
                mutListUser.setValue(employees.getData().getEmployees());
            }else {
                // TODO  - error
            }
        });
    }

    public MutableLiveData<FileResponse> getFileResponseMutableLiveData() {
        return fileResponseMutableLiveData;
    }

    public MutableLiveData<ArrayList<Employee>> getMutListUser() {
        return mutListUser;
    }
}
