package ru.vdv.tregulov.spring.mvc.exception_handing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//этот класс отвечает за поимку и обработку исключений во всех контроллерах нашего проекта
@ControllerAdvice
public class EmployeeGlobalExceptionHandler {

    //этой аннотацией отмечается метод, ответственный за обработку исключений
    @ExceptionHandler
    //метод с такой аннотацией возвращает ResponseEntity(т.е. обёртку HTTP response). Эта обёртка
    // использует дженерики, в которых мы указываем, что в случае выброса NoSuchEmployeeException,
    // мы должны в тело response добавить объект <EmployeeIncorrectData>(т.е. класса который мы
    // создали для вывода информации об исключении). Это значит, что в параметр метода handleException()
    // мы прописываем exception, на который должен реагировать данный метод
    public ResponseEntity<EmployeeIncorrectData> handleException(
            NoSuchEmployeeException exception) {
        //создаём объект, ответственный за json
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        //возвращаем ResponseEntity, который является обёрткой HTTP response(первым парметром
        // мы задаём объект класса EmployeeIncorrectData, вторым параметром мы задём
        // статус код для response(т.е. для ответа))
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    //здесь мы создали метод, который реагирует на любой exception
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(
            Exception exception) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
