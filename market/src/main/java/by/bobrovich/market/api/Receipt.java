package by.bobrovich.market.api;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public interface Receipt {
    void print(PrintStream out);
    void print(OutputStreamWriter out) throws IOException;
}
