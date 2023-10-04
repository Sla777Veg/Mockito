package com.example.homeworkmockito.service;
import com.example.homeworkmockito.model.Employee;
import org.springframework.stereotype.Service;
import com.example.homeworkmockito.exception.EmployeeNotFoundException;

import java.util.Collection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class DepartmentService {
    private final EmplServ emplServ;

    public DepartmentService(EmplServ emplServ) {
        this.emplServ = emplServ;
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getEmployeesByDepartmentStream(department)
                .collect(Collectors.toList());
    }
    public int getSumOfSalariesByDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary).sum();
    }

    public  int getMaxSalaryByDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary).max().orElseThrow(EmployeeNotFoundException::new);
    }

    public  int getMinSalaryByDepartment(int department) {
        return getEmployeesByDepartmentStream(department)
                .mapToInt(Employee::getSalary).min().orElseThrow(EmployeeNotFoundException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartments() {
        return emplServ.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
    private Stream<Employee> getEmployeesByDepartmentStream(int department) {
        return emplServ.getAllEmployees().stream().filter(e -> e.getDepartment()==department);
    }
}
