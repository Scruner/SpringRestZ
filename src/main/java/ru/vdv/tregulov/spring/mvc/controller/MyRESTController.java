package ru.vdv.tregulov.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vdv.tregulov.spring.mvc.entity.Employee;
import ru.vdv.tregulov.spring.mvc.exception_handing.NoSuchEmployeeException;
import ru.vdv.tregulov.spring.mvc.service.EmployeeService;

import java.util.List;

//эта аннотация определяет контролеер, который управляет REST запросами и ответами
@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    //получаем список работников
    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        //теперь мы возварщаем не jsp страницу, а список всех работников
        return allEmployees;
    }

    //получаем работника по id
    @GetMapping("/employees/{id}")
    //аннотация PathVariable используется для получения значения переменной из адреса запроса
    public Employee getEmployee(@PathVariable int id) {
        //получаем работника по id
        Employee employee = employeeService.getEmployee(id);
        //проверяем нашёлся ли работник с таким id, если работник равен null, то выбрасывается
        // exception
        if (employee == null) {
            throw new NoSuchEmployeeException("There is no employee with ID = " +
                    id + " int Database");
        }
        return employee;
    }

    //добавляем работника
    //эта аннотация связывает HTTP запрос, использующий HTTP метод POST с методом контроллера
    @PostMapping("/employees")
    //в этом методе мы должны использовать информацию, которую посылаем в теле метода POST.
    // Чтобы использовать тело метода POST, в параметре метода контроллера addNewEmployee,
    // мы прописываем аннотацию RequestBody, в теле метода POST приходит работник Employee
    public Employee addNewEmployee(@RequestBody Employee employee) {
        //теперь используем этого работника, чтобы добавить его в базу
        employeeService.saveEmployee(employee);
        return employee;
    }

    //изменяем работника
    //эта аннотация связывает HTTP запрос, использующий HTTP метод PUT с методом контроллера
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        //возвращаем мы java объект, который конвертируется в json и добавляется в
        // тело HTTP ответа
        return employee;
    }

    //удаление работника
    //эта аннотация связывает HTTP запрос, использующий HTTP метод DELETE с методом контроллера
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        //проверяем существует ли такой работник, если да - то возвращаем "Employee with ID =
        // " + id + " was deleted". Если же работника с таким id нет, будем возвращать
        // очень информативный json
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("There is no employee with ID = " +
                    id + " in Database");
        }

        employeeService.deleteEmployee(id);
        return "Employee with ID = " + id + " was deleted";

    }

}
