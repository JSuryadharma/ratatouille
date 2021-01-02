package com.example.ratatouille.models;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DatabaseReference;

public class Employee {
    String employeeName;
    String username;
    String phone;
    String address;
    String dateJoined;

    public Employee() {
        //let this to be blank, for data retrival..
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Employee(String employeeName, String username, String phone, String address, String dateJoined) {
        this.employeeName = employeeName;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.dateJoined = dateJoined;
    }

    public void save() {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.EmployeeTable.EMPLOYEE_TABLE);
        dbRef.child(VariablesUsed.loggedUser.getUid()).setValue(this);
    }

    public static void delete(String employeeID){
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.EmployeeTable.EMPLOYEE_TABLE);
        dbRef.child(employeeID).removeValue();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDateJoined() {
        return dateJoined;
    }
}
