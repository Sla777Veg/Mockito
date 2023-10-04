package com.example.homeworkmockito;

import com.example.homeworkmockito.model.Employee;
import com.example.homeworkmockito.record.EmployeeRequest;
import com.example.homeworkmockito.service.DepartmentService;
import com.example.homeworkmockito.service.EmplServ;
import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("Test1", "Test2",1, 5000),
            new Employee("Test3", "Test4",1, 6000),
            new Employee("Test5", "Test6",1, 7000),
            new Employee("Test7", "Test8",2, 8000),
            new Employee("Test9", "Test10",2, 9000)
    );
    @Mock
    EmplServ emplServ;
    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(emplServ.getAllEmployees()).thenReturn(employees);
    }

    @Test
    void getEmployeesByDepartment(){
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        Assertions.assertThat(employeeList).hasSize(3)
                .contains(employees.get(0), employees.get(1), employees.get(2));
    }


    @Test
    void sumOfSalariesByDepartment() {
        int sum = this.departmentService.getSumOfSalariesByDepartment(1);
        Assertions.assertThat(sum).isEqualTo(18_000);
    }

    @Test
    void MaxSalaryInDepartment() {
        int max = this.departmentService.getMaxSalaryByDepartment(2);
        Assertions.assertThat(max).isEqualTo(9000);
    }

    @Test
    void MinSalaryInDepartment() {
        int min = this.departmentService.getMinSalaryByDepartment(2);
        Assertions.assertThat(min).isEqualTo(8000);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();
        Assertions.assertThat(groupedEmployees)
                .hasSize(2)
                .containsEntry(1, List.of(employees.get(0),employees.get(1),employees.get(2)))
                .containsEntry(2,List.of(employees.get(3),employees.get(4)));
    }


}
