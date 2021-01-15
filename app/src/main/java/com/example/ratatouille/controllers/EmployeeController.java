package com.example.ratatouille.controllers;

import com.example.ratatouille.models.Employee;

import java.util.ArrayList;

public class EmployeeController {

    public static Employee getAEmployee(String employeeID){
        Employee currentEmployee = Employee.get(employeeID);
        return currentEmployee;
    }

    public static ArrayList<Employee> getAllEmployee(){
        ArrayList<Employee> employeeList = Employee.getAll();
        return employeeList;
    }
}
