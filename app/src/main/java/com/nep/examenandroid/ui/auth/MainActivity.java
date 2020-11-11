package com.nep.examenandroid.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nep.examenandroid.AuthListener;
import com.nep.examenandroid.R;
import com.nep.examenandroid.data.repositories.Repo;
import com.nep.examenandroid.databinding.ActivityMainBinding;
import com.nep.examenandroid.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity implements AuthListener{

    private ActivityMainBinding binding;
//    private AuthViewModel viewModel;
    private AuthViewModelFactory authViewModelFactory;
    private AuthViewModel viewModel;
    private Repo repository;
    private AuthListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);

        repository = new Repo();
        authListener = this;
        authViewModelFactory = new AuthViewModelFactory(repository,authListener);
        viewModel = ViewModelProviders.of(MainActivity.this,authViewModelFactory).get(AuthViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getmUser().observe(this, usuario->{
            if(usuario != null){
//                Toast.makeText(this,"user : "+usuario.getEmail(),Toast.LENGTH_SHORT).show();
                Log.i(AuthViewModel.TAG, " user entrando click button");
                Intent intent = new Intent();
                intent.setClass(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStarted() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.buttonSignIn.setEnabled(false);
    }

    @Override
    public void onSuccess() {
        binding.progressBar.setVisibility(View.GONE);
        binding.buttonSignIn.setEnabled(true);

    }

    @Override
    public void onFailure(String message) {
        binding.progressBar.setVisibility(View.GONE);
        binding.buttonSignIn.setEnabled(true);
        Toast.makeText(this," "+message,Toast.LENGTH_LONG).show();
    }
}