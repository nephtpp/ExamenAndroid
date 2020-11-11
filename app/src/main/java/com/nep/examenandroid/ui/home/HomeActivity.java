package com.nep.examenandroid.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nep.examenandroid.R;
import com.nep.examenandroid.data.dto.employees.Employee;
import com.nep.examenandroid.data.repositories.Repo;
import com.nep.examenandroid.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeActivityViewModel viewModel;
    private Repo repository;
    private HomeActivityViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setLifecycleOwner(this);
        repository = new Repo();
        factory = new HomeActivityViewModelFactory(repository);
        viewModel = ViewModelProviders.of(HomeActivity.this, factory).get(HomeActivityViewModel.class);
        binding.btnColaboradores.setOnClickListener(colaboradores);
        binding.btnAgregar.setOnClickListener(addColaborador);


        viewModel.getFile();
        viewModel.getFileResponseMutableLiveData().observe(this, fileResponse -> {
            if (fileResponse != null) {
//                String url = "https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97";

                Log.i(HomeActivityViewModel.TAG, " fileresponse " + fileResponse.getCode());
                viewModel.downloadFile(HomeActivity.this, fileResponse.getData().getFile());//url
            }
        });
        viewModel.getMutListUser().observe(this, usuarios -> {
            if (usuarios.size() > 0) {
                binding.btnAgregar.setEnabled(true);
                binding.btnColaboradores.setEnabled(true);
            } else {
                binding.btnAgregar.setEnabled(false);
                binding.btnColaboradores.setEnabled(false);
            }
        });


    }

    public View.OnClickListener colaboradores = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, ListColaboradoresActivity.class);
            ArrayList list = viewModel.getMutListUser().getValue();
            intent.putParcelableArrayListExtra("employees",list);
            startActivity(intent);
        }
    };

    View.OnClickListener addColaborador = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, AddActivity.class);
            startActivityForResult(intent,200);
//            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 200){
           Employee persona = data.getParcelableExtra("persona");
            viewModel.getMutListUser().getValue().add(persona);
        }

    }
}