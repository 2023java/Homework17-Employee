package com.example.employee;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(int departmentId) {
        super("Отдел не найден: " + departmentId);
    }
}
