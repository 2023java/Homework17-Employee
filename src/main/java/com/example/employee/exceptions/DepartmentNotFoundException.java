package com.example.employee.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(int departmentId) {
        super("Отдел не найден: " + departmentId);
    }
}
