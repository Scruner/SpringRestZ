package ru.vdv.tregulov.spring.mvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vdv.tregulov.spring.mvc.entity.Employee;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    //при использовании аннотации @Transactional Spring берёт на себя ответственность за открытие
    // и закрытие транзакций

    public List<Employee> getAllEmployees() {
        //получаем из фабрики sessionFactory сессию session
        Session session = sessionFactory.getCurrentSession();
        //получим список всех работников из базы
//        List<Employee> allEmployees = session.createQuery("FROM Employee," +
//                Employee.class).getResultList();
        //вариант разделения выражения на две части читается так: запрос работников (Employee)
        Query<Employee> query = session.createQuery("FROM Employee", Employee.class);
        //после этого мы можем выполнить этот запрос и назначаем результат этого запроса
        // списку работников
        List<Employee> allEmployees = query.getResultList();
        return allEmployees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
//в ДАО мы прописываем реальную работу метода saveEmployee, потому что ДАО ответственен за связь с
//базой данных
        //внутри метода saveEmployee() мы используем метод saveOrUpdate(), который
        //делает insert (т.е. добавляет нового работника), если работник(employee) из параметра
        //метода был с id = 0. Или же он изменяет, существующего в базе работника, если
        //работник из параметра метода был с id не равным 0.
        session.saveOrUpdate(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("DELETE FROM Employee " +
//когда мы пишем :emplyeeId мы говорим, что вместо employeeId мы пропишем для этого
//запроса параметр
                "WHERE id =:employeeId");
        //то есть в нашем выражении delete произойдёт замена названия параметра, на само значение этого
        //параметра, это достигается с помощью метода setParameter. Это значит, employeeId в этом запросе
        //будет заменять на id работника, который прописывается в парметре метода deleteEmployee
        query.setParameter("employeeId", id);
        //теперь нужно, чтобы этот запрос сработал
        query.executeUpdate();

    }
}
