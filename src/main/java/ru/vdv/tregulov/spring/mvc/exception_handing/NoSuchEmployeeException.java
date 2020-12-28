package ru.vdv.tregulov.spring.mvc.exception_handing;

public class NoSuchEmployeeException extends RuntimeException {

//при создании исключения, мы будем передавать ему сообщение
    public NoSuchEmployeeException(String message) {
        super(message);
    }
}
