package com.example.employee;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(String firstName, String lastName) {
        if (getEmployeeByFullName(firstName, lastName) != null) {
            throw new EmployeeAlreadyAddedException(firstName, lastName);
        }

        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }

        Employee employee = new Employee(firstName, lastName);
        employees.add(employee);
    }

    public void removeEmployee(String firstName, String lastName) {
        Employee employee = getEmployeeByFullName(firstName, lastName);
        if (employee == null) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }
        employees.remove(employee);
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = getEmployeeByFullName(firstName, lastName);
        if (employee == null) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    private Employee getEmployeeByFullName(String firstName, String lastName) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName)) {
                return employee;
            }
        }
        return null;
    }
}

