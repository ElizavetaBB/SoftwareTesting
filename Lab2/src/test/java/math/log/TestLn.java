package math.log;

import exceptions.ODZException;
import math.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestLn{
    private static Function ln;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        ln=new Ln();
        accuracy=0.000001;
    }

    static Stream<Arguments> passedValues() {
        return Stream.of(
                arguments(0.1),
                arguments(0.2),
                arguments(0.3),
                arguments(0.4),
                arguments(0.5),
                //(0,0.5]
                arguments(0.8),
                arguments(1.1),
                arguments(1.4),
                arguments(1.7),
                arguments(2.0),
                //(0.5,2]
                arguments(2.8),
                arguments(3.6),
                arguments(4.4),
                arguments(5.2),
                arguments(6.0),
                //(2,6]
                arguments(8.8),
                arguments(11.6),
                arguments(14.4),
                arguments(17.2),
                arguments(20.0)
                //(6;20]
        );
    }

    static Stream<Arguments> valuesWithExceptions() {
        return Stream.of(
                arguments(0.0),
                arguments(-0.1),
                arguments(-5.0)
        );
    }

    @DisplayName("Ln: unit test")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testLn(double value) throws ODZException {
        Assertions.assertEquals(Math.log(value),ln.compute(value,accuracy), accuracy);
    }


    @DisplayName("Ln: unit test with exceptions")
    @ParameterizedTest
    @MethodSource("valuesWithExceptions")
    public void testLnWithExceptions(double value){
        Assertions.assertThrows(ODZException.class,()->ln.compute(value,accuracy));
    }
}
