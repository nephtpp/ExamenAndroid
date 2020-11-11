package com.nep.examenandroid.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nep.examenandroid.data.repositories.Repo;
import com.nep.examenandroid.ui.auth.AuthViewModel;

public class HomeActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Repo repository;

    public HomeActivityViewModelFactory(Repo repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeActivityViewModel(repository);
    }
}
