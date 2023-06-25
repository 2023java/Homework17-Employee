package com.example.employee;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException(firstName, lastName);
        }

        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }

        Employee employee = new Employee(firstName, lastName);
        employees.put(key, employee);
    }

    public void removeEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }
        employees.remove(key);
    }

    public Employee findEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);
        Employee employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return List.copyOf(employees.values());
    }

    private String generateKey(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}

