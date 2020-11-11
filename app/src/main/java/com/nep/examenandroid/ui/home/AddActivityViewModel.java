package com.nep.examenandroid.ui.home;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nep.examenandroid.data.dto.Location;
import com.nep.examenandroid.data.dto.employees.Employee;

import java.util.Random;

public class AddActivityViewModel extends ViewModel {

    private MutableLiveData<Employee> employeeMutable;
    private ObservableField email;
    private ObservableField pass;
    private ObservableField name;
    private Location loc;

    public AddActivityViewModel() {
        this.employeeMutable = new MutableLiveData<>();
        this.email = new ObservableField();
        this.pass = new ObservableField();
        this.name = new ObservableField();
    }

    public void onLoginButtonClick() {
        if (email.get() == null || pass.get() == null || name.get() == null || email.get().toString().equals("") || pass.get().toString().equals("") || name.get().toString().equals("")) {

            return;
        }
        Employee employee = new Employee();
        employee.setId(String.valueOf(randomId()));
        employee.setName(name.get().toString());
        employee.setMail(email.get().toString());

        employee.setLocation(new Location("-1.0966072","-96.6982323"));
        employeeMutable.postValue(employee);
    }

    private int randomId() {
        Random r = new Random();
        int randomNumber = r.nextInt(1000);
        return randomNumber;
    }

    private int randomLat(){
        Random r = new Random();
        int min =  19;
        int max = 21;
        int randomNumber = r.nextInt(max-min) + min;
        return randomNumber;
    }
    private int randomLog(){
        Random r = new Random();
        int min =  -98;
        int max = -99;
        int randomNumber = r.nextInt(-98) ;
        return randomNumber;
    }

    public MutableLiveData<Employee> getEmployeeMutable() {
        return employeeMutable;
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

    public ObservableField getName() {
        return name;
    }

    public void setName(ObservableField name) {
        this.name = name;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
