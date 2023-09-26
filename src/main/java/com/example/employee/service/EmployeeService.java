package com.example.employee.service;

import com.example.employee.Employee;
import com.example.employee.exceptions.*;
import org.springframework.stereotype.Service;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private final Map<String, Employee> employees = new HashMap<>();
    private final Map<Integer, List<Employee>> employeesByDepartment = new HashMap<>();

    public void addEmployee(String firstName, String lastName, double salary, int departmentId) {
        String key = generateKey(firstName, lastName);

        if (!validateInput(firstName,lastName)) {
            throw new InvalidInputException();
        }

        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException(firstName, lastName);
        }

        if (employees.size() >= MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }

        Employee employee = new Employee(firstName, lastName, salary, departmentId);
        employees.put(key, employee);
        employeesByDepartment.computeIfAbsent(departmentId, k -> new ArrayList<>()).add(employee);
    }

    public void removeEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);

        if (!validateInput(firstName,lastName)) {
            throw new InvalidInputException();
        }

        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }

        Employee employee = employees.get(key);
        employees.remove(key);
        employeesByDepartment.get(employee.getDepartmentId()).remove(employee);
    }

    public Employee findEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);

        if (!validateInput(firstName,lastName)) {
            throw new InvalidInputException();
        }

        Employee employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException(firstName, lastName);
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Employee getEmployeeWithMaxSalaryInDepartment(int departmentId) {
        List<Employee> employeesInDepartment = employeesByDepartment.get(departmentId);
        if (employeesInDepartment == null || employeesInDepartment.isEmpty()) {
            throw new DepartmentNotFoundException(departmentId);
        }

        return employeesInDepartment.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    }

    public Employee getEmployeeWithMinSalaryInDepartment(int departmentId) {
        List<Employee> employeesInDepartment = employeesByDepartment.get(departmentId);
        if (employeesInDepartment == null || employeesInDepartment.isEmpty()) {
            throw new DepartmentNotFoundException(departmentId);
        }

        return employeesInDepartment.stream()
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    }

    public List<Employee> getAllEmployeesInDepartment(int departmentId) {
        List<Employee> employeesInDepartment = employeesByDepartment.get(departmentId);
        if (employeesInDepartment == null || employeesInDepartment.isEmpty()) {
            throw new DepartmentNotFoundException(departmentId);
        }

        return employeesInDepartment;
    }

    public Map<Integer, List<Employee>> getAllEmployeesWithDepartments() {
        return new HashMap<>(employeesByDepartment);
    }

    private String generateKey(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    private boolean validateInput (String firstName, String lastName) {
        return isAlpha(firstName) && isAlpha(lastName);
    }
}

