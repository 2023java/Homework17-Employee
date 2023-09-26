package com.example.employee.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String firstName, String lastName) {
        super("Сотрудник не найден: " + firstName + " " + lastName);
    }
}

