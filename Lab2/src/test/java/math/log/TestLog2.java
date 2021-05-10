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

public class TestLog2 {
    private static Function ln2;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        ln2=new Log2(new LnStub());
        accuracy=0.000001;
    }

    static Stream<Arguments> valuesRange() {
        return Stream.of(
                arguments(0.02),
                arguments(0.04),
                arguments(0.06),
                arguments(0.08),
                arguments(0.1),
                //(0,0.1]
                arguments(0.18),
                arguments(0.26),
                arguments(0.34),
                arguments(0.42),
                arguments(0.5),
                //[0.1,0.5]
                arguments(1.1),
                arguments(1.7),
                arguments(2.3),
                arguments(2.9),
                arguments(3.5),
                //(0.5,3.5]
                arguments(6.8),
                arguments(10.1),
                arguments(13.4),
                arguments(16.7),
                arguments(20.0)
                //(3.5,20]
        );
    }

    static Stream<Arguments> valuesWithExceptions() {
        return Stream.of(
                arguments(0.0),
                arguments(-0.1),
                arguments(-5.0)
        );
    }

    @DisplayName("Log2: Integration Test")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testLog2(Double value) throws ODZException {
        Assertions.assertEquals(Math.log(value)/Math.log(2),
                ln2.compute(value,accuracy), accuracy);
    }

    @DisplayName("Log2: Integration Test with exceptions")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesWithExceptions")
    public void testLog2WithExceptions(Double value){
        Assertions.assertThrows(ODZException.class,()->ln2.compute(value,accuracy));
    }
}
