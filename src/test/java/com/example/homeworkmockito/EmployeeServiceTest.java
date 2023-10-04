package com.example.homeworkmockito;

import com.example.homeworkmockito.model.Employee;
import com.example.homeworkmockito.record.EmployeeRequest;
import com.example.homeworkmockito.service.EmplServ;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeServiceTest {
    private EmplServ emplServ;

    @BeforeEach
    public void setUp() {
        this.emplServ = new EmplServ();
        Stream.of(
                new EmployeeRequest("Test1", "Test2",1, 5000),
                new EmployeeRequest("Test3", "Test4",1, 6000),
                new EmployeeRequest("Test5", "Test6",1, 7000),
                new EmployeeRequest("Test7", "Test8",1, 8000),
                new EmployeeRequest("Test9", "Test10",1, 9000)
        ).forEach(emplServ::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Valid", "Valid", 3, 5000);
        Employee result = emplServ.addEmployee(request);

        assertEquals(request.getFirstName(),result.getFirstName());
        assertEquals(request.getLastName(),result.getLastName());
        assertEquals(request.getDepartment(),result.getDepartment());
        assertEquals(request.getSalary(),result.getSalary());



        Assertions.
                assertThat(emplServ.getAllEmployees())
                .contains(result);

    }

    @Test
    public void getAllEmployeesNotNull() {
        EmplServ emplNotNull = new EmplServ();
        Collection<Employee> expected = emplNotNull.getAllEmployees();

        assertNotNull(expected);
    }


    @Test
    public void listEmployees() {
        Collection<Employee> employees = emplServ.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
        Assertions.assertThat(employees).first()
                .extracting(Employee::getFirstName)
                .isEqualTo("Test1");
    }

    @Test
    public void sumOfSalaries() {
        int sum = emplServ.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(35_000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = emplServ.getEmplMaxSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("Test9");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = emplServ.getEmplMinSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("Test1");
    }

    @Test
    public void getTestAverageSalary() {
        double average = emplServ.getAverageSalary();
        Assertions.assertThat(average).isEqualTo(7_000);
    }
    @Test
    public void EmplWithSalaryMoreThatAverage() {
        List<Employee> employee = emplServ.getEmployeesWithSalaryMoreThatAverage();
        assertEquals(employee.get(0).getSalary(),8_000);
        assertEquals(employee.get(1).getSalary(),9_000);

    }

    @Test
    public void removeEmployee() {
        emplServ.removeEmployee(0);
        Collection<Employee> employees = emplServ.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
    }
}
