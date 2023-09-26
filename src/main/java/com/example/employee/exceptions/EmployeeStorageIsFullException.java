package com.example.employee.exceptions;

public class EmployeeStorageIsFullException extends RuntimeException {
    public EmployeeStorageIsFullException() {
        super("Хранилище сотрудников заполнено");
    }
}

