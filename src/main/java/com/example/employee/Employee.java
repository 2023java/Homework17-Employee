package com.example.employee;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class Employee {
    private String firstName;
    private String lastName;
    private double salary;
    private int departmentId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double  getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Employee(String firstName, String lastName, double salary, int departmentId) {
        this.firstName = capitalize(firstName.toLowerCase());
        this.lastName = capitalize(lastName.toLowerCase());
        this.salary = salary;
        this.departmentId = departmentId;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}

