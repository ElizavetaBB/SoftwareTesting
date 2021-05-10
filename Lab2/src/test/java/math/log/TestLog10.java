package math.log;

import exceptions.ODZException;
import math.Function;
import stubs.LnStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestLog10 {
    private static Function ln10;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        ln10=new Log10(new LnStub());
        accuracy=0.000001;
    }

    static Stream<Arguments> valuesRange() {
        return Stream.of(
                arguments(0.1),
                arguments(0.2),
                arguments(0.3),
                arguments(0.4),
                arguments(0.5),
                //[0,0.5]
                arguments(1.2),
                arguments(1.9),
                arguments(2.6),
                arguments(3.3),
                arguments(4.0),
                //(0.5,4]
                arguments(7.2),
                arguments(10.4),
                arguments(13.6),
                arguments(16.8),
                arguments(20.0)
                //(4;20]
        );
    }

    static Stream<Arguments> valuesWithExceptions() {
        return Stream.of(
                arguments(0.0),
                arguments(-0.1),
                arguments(-5.0)
        );
    }

    @DisplayName("Log10: Integration Test")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testLog10(Double value) throws ODZException {
        Assertions.assertEquals(Math.log(value)/Math.log(10),
                ln10.compute(value,accuracy), accuracy);
    }

    @DisplayName("Log10: Integration Test with exceptions")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesWithExceptions")
    public void testLog10WithExceptions(Double value){
        Assertions.assertThrows(ODZException.class,()->ln10.compute(value,accuracy));
    }
}
