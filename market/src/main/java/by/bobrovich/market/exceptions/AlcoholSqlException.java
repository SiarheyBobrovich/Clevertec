package by.bobrovich.market.exceptions;

public class AlcoholSqlException extends IllegalStateException {
    public AlcoholSqlException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AlcoholSqlException(String s) {
        super(s);
    }
}
