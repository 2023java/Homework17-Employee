package com.example.employee.exceptions;

public class EmployeeAlreadyAddedException extends RuntimeException {
    public EmployeeAlreadyAddedException(String firstName, String lastName) {
        super("Сотрудник уже добавлен: " + firstName + " " + lastName);
    }
}

