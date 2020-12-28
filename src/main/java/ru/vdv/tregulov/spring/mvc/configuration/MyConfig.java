package ru.vdv.tregulov.spring.mvc.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.vdv.tregulov.spring.mvc")
//аннотация EnableWebMvc заменяет <mvc:annotation-driven/>
@EnableWebMvc
//по функциональности аннотация EnableTransactionManagement то же самое, что
//<tx:annotation-driven transaction-manager="transactionManager"/> из xml файла настройки
@EnableTransactionManagement
public class MyConfig {
    //создаём бин для DataSource через класс ComboPooledDataSource
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("bestuser");
            dataSource.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    //Создаём бин для sessionFactory через класс LocalSessionFactoryBean
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.vdv.tregulov.spring.mvc.entity");
        //присваиваем свойства для Hibernate
        //сначала создаём объект Properties
        Properties hibernateProperties = new Properties();
        //назначаем свойства объекту Properties("имя Properties", "значение Properties")
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        //назначаем Properties нашему объекту sessionFactory
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    //Создаём бин для HibernateTransactionManager
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        //назначаем объекту transactionManager фабрику sessionFactory(в параметрах указываем
        // метод sessionFactory(). Важный момент!!!: Чтобы получить sessionFactory из класса
        //LocalSessionFactoryBean мы должны использовать метода getObject()
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
