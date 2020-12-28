package ru.vdv.tregulov.spring.mvc.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//в этом классе мы прописываем то, что прописывали в web.xml
public class MyWebInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    //этот метод отвечает за ссылку на класс с конфигурацией нашего проекта
    @Override
    protected Class<?>[] getServletConfigClasses() {
        //убираем ноль из квадртных скобок и создаём массив с одним элементом, которым является
        //созданный нами конфигурационный класс
        return new Class[]{MyConfig.class};
    }

    //в этом методе указываем url
    @Override
    protected String[] getServletMappings() {
        //создаём массив с одним элементом, этим элементом будет url
        return new String[]{"/"};
    }
}
