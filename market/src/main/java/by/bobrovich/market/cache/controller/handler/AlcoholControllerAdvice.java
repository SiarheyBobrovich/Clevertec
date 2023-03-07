package by.bobrovich.market.cache.controller.handler;

import by.bobrovich.market.cache.exception.AlcoholNotFoundException;
import by.bobrovich.market.cache.exception.AlcoholSqlException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class AlcoholControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handle(AlcoholNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(AlcoholSqlException e) {
        log.error(e);
        return e.getMessage();
    }
}
