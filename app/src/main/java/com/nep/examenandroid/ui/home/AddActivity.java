package com.nep.examenandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nep.examenandroid.R;
import com.nep.examenandroid.databinding.ActivityAddBinding;
import com.nep.examenandroid.databinding.ActivityHomeBinding;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddBinding binding;
    private AddActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(AddActivityViewModel.class);
        binding.setViewModel(viewModel);
        binding.button.setOnClickListener(this);
        viewModel.getEmployeeMutable().observe(this,persona->{
            if(persona != null){
                Intent intent = getIntent();
                intent.putExtra("persona", persona);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void onClick(View view) {
        viewModel.onLoginButtonClick();
    }
}