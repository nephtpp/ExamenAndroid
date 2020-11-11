package com.nep.examenandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStores;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.nep.examenandroid.R;
import com.nep.examenandroid.data.dto.User;
import com.nep.examenandroid.data.dto.employees.Employee;
import com.nep.examenandroid.databinding.ActivityListColaboradoresBinding;
import com.nep.examenandroid.ui.adapters.ColaboradoresAdapter;
import com.nep.examenandroid.ui.adapters.ListViewClickListener;

import java.util.ArrayList;

public class ListColaboradoresActivity extends AppCompatActivity implements ListViewClickListener {

    private ActivityListColaboradoresBinding binding;
    private ColaboradoresAdapter adapter;
    private ListColaboradoresViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_colaboradores);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(this).get(ListColaboradoresViewModel.class);


        binding.rvListaPersonas.setHasFixedSize(true);
        binding.rvListaPersonas.setLayoutManager(new LinearLayoutManager(ListColaboradoresActivity.this));

        //TODO - obtener los colaboradores


        ArrayList lista = getIntent().getParcelableArrayListExtra("employees");
        viewModel.getListEmployees().setValue(lista);

        adapter = new ColaboradoresAdapter(viewModel.getListEmployees().getValue());//
        binding.rvListaPersonas.setAdapter(adapter);
        adapter.setClickListener(this);

        viewModel.getListEmployees().observe(this, empleados -> {
            if (empleados.size() > 0) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void clickListener(int position) {
        // TODO - ir a mapas
        Employee e  = viewModel.getListEmployees().getValue().get(position);
        Intent i = new Intent();
        i.setClass(getBaseContext(),MapsActivity.class);
        i.putExtra("user",e);
        startActivity(i);
    }
}