package by.bobrovich.market.controller.handler;

import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handle(ProductNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public String handle(ProductQuantityIsNotAvailable e) {
        return e.getMessage();
    }
}