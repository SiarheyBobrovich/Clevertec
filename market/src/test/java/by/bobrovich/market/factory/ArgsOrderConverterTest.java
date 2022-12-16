package by.bobrovich.market.factory;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.converter.ArgsOrderConverter;
import by.bobrovich.market.exceptions.ConvertionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArgsOrderConverterTest {

    private final ArgsOrderConverter factory = new ArgsOrderConverter();

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/args.csv", delimiter = '\n')
    void create(String args) {
        String[] strings = args.split(",");
        Order order = factory.convert(strings);

        StringBuilder expected = new StringBuilder();
        order.orderEntries().forEach(x->
                expected.append(x.id())
                        .append("-")
                        .append(x.quantity())
                        .append(",")
        );
        if (order.discountCardNumber() != null) {
            expected.append("card")
                    .append("-")
                    .append(order.discountCardNumber());
        }else {
            expected.deleteCharAt(expected.lastIndexOf(","));
        }

        assertEquals(expected.toString(), args);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/args_failed.csv", delimiter = '\n')
    void createFailed(String args) {
        assertThrows(ConvertionException.class,
                () -> factory.convert(args.split(",")));
    }

    @Test
    void createFailedNull() {
        assertThrows(ConvertionException.class,
                () -> factory.convert(null));
        assertThrows(ConvertionException.class,
                () -> factory.convert(new String[0]));
    }

    public static Stream<String> getArgs() {
        return Stream.<String>builder()
                .add("1-2,4-2,12-5,card-1234")
                .add("1-2,1-3,1-5,2-7")
                .add("12-5")
                .build();
    }

    public static Stream<String> getFailedArgs() {
        return Stream.<String>builder()
                .add("")                //failed by empty
                .add("card-1234")        //failed by empty order
                .add("1-2,cord-1234")    //failed by 'cord'
                .add("1-2,card-f12")     //failed by letter in card number
                .add("1-b21")                   //failed by letter in quantity
                .add("h-13")                    //failed by letter id
                .add("-1-99")                   //failed by negative id
                .add("1--99")                   //failed by negative quantity
                .add("0.8-5")                 //failed by double id
                .add("4-0.5")                 //failed by double quantity
                .build();
    }
}