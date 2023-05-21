package com.example.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public ResponseEntity<Object> addEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            employeeService.addEmployee(firstName, lastName);
            return ResponseEntity.ok(new Employee(firstName, lastName));
        } catch (EmployeeAlreadyAddedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmployeeStorageIsFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/remove")
    public ResponseEntity<Object> removeEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            employeeService.removeEmployee(firstName, lastName);
            return ResponseEntity.ok(new Employee(firstName, lastName));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            return ResponseEntity.ok(employeeService.findEmployee(firstName, lastName));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}

