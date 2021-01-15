package com.example.ratatouille.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ratatouille.db.DatabaseHelper;
import com.example.ratatouille.db.DatabaseVars;
import com.example.ratatouille.vars.VariablesUsed;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Employee {
    private String employeeName;
    private String username;
    private String phone;
    private String address;
    private String dateJoined;
    private static Employee selectedValues;

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

    public static Employee get(String employeeID) {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.EmployeeTable.EMPLOYEE_TABLE);
        dbRef.child(employeeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedValues = snapshot.getValue(Employee.class);
                Log.w(TAG, "onSuccess: Employee Data Received!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onFailure: Employee Data Failed to Receive!");
            }
        });
        return selectedValues;
    }

    public static ArrayList<Employee> getAll() {
        DatabaseReference dbRef = DatabaseHelper.getDb().getReference(DatabaseVars.EmployeeTable.EMPLOYEE_TABLE);
        ArrayList<Employee> employeeList = new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    employeeList.add(ds.getValue(Employee.class));
                }
                Log.d(TAG, "onSuccess: All Employee Data Received!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onFailure: All Employee Data Failed to Receive!");
            }
        });

        return employeeList;
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
