package by.bobrovich.market.api;

import java.io.PrintStream;

public interface Receipt {
    void print(PrintStream out);
    String getTitle();
    String getBody();
    String getTotal();
}
