package com.example.employee.controller;

import com.example.employee.exceptions.DepartmentNotFoundException;
import com.example.employee.exceptions.EmployeeAlreadyAddedException;
import com.example.employee.exceptions.EmployeeNotFoundException;
import com.example.employee.exceptions.EmployeeStorageIsFullException;
import com.example.employee.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/add")
    public ResponseEntity<Object> addEmployee(@RequestParam String firstName, @RequestParam String lastName,
                                              @RequestParam double salary, @RequestParam int departmentId) {
        try {
            employeeService.addEmployee(firstName, lastName, salary, departmentId);
            return ResponseEntity.ok(new Employee(firstName, lastName, salary, departmentId));
        } catch (EmployeeAlreadyAddedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmployeeStorageIsFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/employee/remove")
    public ResponseEntity<Object> removeEmployee(@RequestParam String firstName, @RequestParam String lastName,@RequestParam double salary, @RequestParam int departmentId) {
        try {
            employeeService.removeEmployee(firstName, lastName);
            return ResponseEntity.ok(new Employee(firstName, lastName,salary,departmentId));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employee/find")
    public ResponseEntity<Object> findEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            return ResponseEntity.ok(employeeService.findEmployee(firstName, lastName));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employee/list")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/departments/max-salary")
    public ResponseEntity<Object> getEmployeeWithMaxSalaryByDepartment(@RequestParam("departmentId") int departmentId) {
        try {
            Employee employee = employeeService.getEmployeeWithMaxSalaryInDepartment(departmentId);
            return ResponseEntity.ok(employee);
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/departments/min-salary")
    public ResponseEntity<Object> getEmployeeWithMinSalaryByDepartment(@RequestParam("departmentId") int departmentId) {
        try {
            Employee employee = employeeService.getEmployeeWithMinSalaryInDepartment(departmentId);
            return ResponseEntity.ok(employee);
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/departments/all")
    public ResponseEntity<Object> getAllEmployeesInDepartment(@RequestParam("departmentId") int departmentId) {
        try {
            List<Employee> employees = employeeService.getAllEmployeesInDepartment(departmentId);
            return ResponseEntity.ok(employees);
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/departments/all-with-departments")
    public ResponseEntity<Object> getAllEmployeesWithDepartments() {
        Map<Integer, List<Employee>> employeesByDepartment = employeeService.getAllEmployeesWithDepartments();
        return ResponseEntity.ok(employeesByDepartment);
    }
}

