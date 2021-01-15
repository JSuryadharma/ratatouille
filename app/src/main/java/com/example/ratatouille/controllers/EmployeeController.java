package com.example.ratatouille.controllers;

import com.example.ratatouille.models.Employee;

import java.util.ArrayList;
import java.util.Date;

public class EmployeeController {

    public static Employee getAEmployee(String employeeID){
        Employee currentEmployee = Employee.get(employeeID);
        return currentEmployee;
    }

    public static ArrayList<Employee> getAllEmployee(){
        ArrayList<Employee> employeeList = Employee.getAll();
        return employeeList;
    }

    public static Employee createEmployee(String employeeName, String username, String phone, String address){
        Date date = new Date();
        Employee newEmployee = new Employee(employeeName, username, phone, address, date.toString());
        newEmployee.save();
        return newEmployee;
    }
}
