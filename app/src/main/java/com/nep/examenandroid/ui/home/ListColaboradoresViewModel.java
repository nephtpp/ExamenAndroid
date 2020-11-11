package com.nep.examenandroid.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nep.examenandroid.data.dto.employees.Employee;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListColaboradoresViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Employee>> listEmployees;

    public ListColaboradoresViewModel() {
        this.listEmployees = new MutableLiveData<>();
        listEmployees.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Employee>> getListEmployees() {
        return listEmployees;
    }
}
