package by.bobrovich.market.controller.handler;

import by.bobrovich.market.exceptions.ConvertionException;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import by.bobrovich.market.exceptions.ServiceNotAvailableNow;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MarketControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handle(ConvertionException e) {
        return e.getMessage();
    }
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handle(DiscountCardNotFoundException e) {
        return e.getMessage();
    }

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

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(ServiceNotAvailableNow e) {
        return e.getMessage();
    }
}