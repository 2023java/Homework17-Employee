package com.example.employee;

public class EmployeeStorageIsFullException extends RuntimeException {
    public EmployeeStorageIsFullException() {
        super("Хранилище сотрудников заполнено");
    }
}

