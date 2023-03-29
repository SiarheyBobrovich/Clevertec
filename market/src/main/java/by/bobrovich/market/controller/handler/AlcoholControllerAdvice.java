package by.bobrovich.market.controller.handler;

import by.bobrovich.market.exceptions.AlcoholNotFoundException;
import by.bobrovich.market.exceptions.AlcoholSqlException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class AlcoholControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handle(AlcoholNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handle(AlcoholSqlException e) {
        log.error(e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
