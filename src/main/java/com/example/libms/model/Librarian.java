package com.example.libms.model;

public class Librarian extends UserModel {
    private String employeeId;

    public Librarian(int id, String name, String email, String password, String employeeId, String admin) {
        super(id, name, email, password, "Librarian");
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public void displayUserRole() {
        System.out.println(getName() + " is a Librarian with employee ID: " + employeeId);
    }


}
