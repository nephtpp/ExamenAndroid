package com.nep.examenandroid.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nep.examenandroid.AuthListener;
import com.nep.examenandroid.data.repositories.Repo;

public class AuthViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Repo repository;
    private AuthListener authListener;
//    public AuthViewModelFactory() {
//    }

    public AuthViewModelFactory(Repo repository, AuthListener authListener) {
        this.repository = repository;
        this.authListener = authListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthViewModel(repository,authListener);
    }
}
