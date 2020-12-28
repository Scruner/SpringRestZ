package ru.vdv.tregulov.spring.mvc.dao;

import ru.vdv.tregulov.spring.mvc.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> getAllEmployees();

    public void saveEmployee(Employee employee);

    public Employee getEmployee(int id);

    public void deleteEmployee(int id);
}
