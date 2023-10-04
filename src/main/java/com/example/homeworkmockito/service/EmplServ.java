package com.example.homeworkmockito.service;
import com.example.homeworkmockito.model.Employee;
import com.example.homeworkmockito.record.EmployeeRequest;
import org.springframework.stereotype.Service;
import com.example.homeworkmockito.exception.EmployeeNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmplServ {

    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName()==null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream().mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmplMinSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee getEmplMaxSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeesWithSalaryMoreThatAverage() {
        Double averageSalary = getAverageSalary();
        if (averageSalary == null) {
            return Collections.emptyList();
        }
        return employees.values().stream().
                filter(e -> e.getSalary() > averageSalary).collect(Collectors.toList());
    }

    public Double  getAverageSalary() {
        return employees.values().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
    }

    public   Employee removeEmployee(int id) {
        return employees.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmplServ emplServ = (EmplServ) o;
        return Objects.equals(employees, emplServ.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employees);
    }
}
