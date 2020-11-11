package com.nep.examenandroid.ui.auth;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthCredential;
import com.nep.examenandroid.AuthListener;
import com.nep.examenandroid.data.dto.FileResponse;
import com.nep.examenandroid.data.dto.User;
import com.nep.examenandroid.data.repositories.Repo;

import java.io.IOException;

public class AuthViewModel extends ViewModel {
    public final static String TAG = "loggin";

    private Repo repository;
    private MutableLiveData<User> mUser;
    private AuthListener authListener;

    private ObservableField email;
    private ObservableField pass;


    public AuthViewModel(Repo repository, AuthListener listener) {
        this.repository = repository;
        this.authListener = listener;
        email = new ObservableField();
        pass = new ObservableField();
        mUser = new MutableLiveData<>();


    }


    public void onLoginButtonClick(View view) {

        authListener.onStarted();
        if (email.get() == null || pass.get() == null || email.get().toString().equals("") || pass.get().toString().equals("")) {
            authListener.onFailure("Correo o contraseña invalida");
            return;
        }
        Log.i(TAG, " user entrando click button");
        repository.firebaseSignIn(email.get().toString(), pass.get().toString());;//
//        Log.i(TAG, " user saliendo click button"+mUser.getValue().getEmail());

        repository.getUserAuntenticado().observeForever(user -> {
            if(user != null){
                mUser.postValue(user);
                authListener.onSuccess();
            }
        });
        repository.getOnError().observeForever(error->{
            authListener.onFailure(error);
        });
    }


    public MutableLiveData<User> getmUser() {
        return mUser;
    }

    public ObservableField getEmail() {
        return email;
    }

    public void setEmail(ObservableField email) {
        this.email = email;
    }

    public ObservableField getPass() {
        return pass;
    }

    public void setPass(ObservableField pass) {
        this.pass = pass;
    }
}
