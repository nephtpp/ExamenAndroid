package com.nep.examenandroid.data.dto.employees;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Data {

        @SerializedName("employees")
        @Expose
        private ArrayList<Employee> employees = null;

        public ArrayList<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(ArrayList<Employee> employees) {
            this.employees = employees;
        }



}
