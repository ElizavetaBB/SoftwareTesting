package math.trig;

import exceptions.ODZException;
import math.Function;
import math.trig.Sin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestSin {
    private static Function sin;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        sin=new Sin();
        accuracy=0.000001;
    }

    static Stream<Arguments> valuesRange() {
        return Stream.of(
                arguments(-Math.PI),
                arguments(-2.827433388),
                arguments(-2.513274123),
                arguments(-2.1991148575),
                arguments(-1.884955592),
                arguments(-Math.PI/2),
                //[-pi;-pi/2]
                arguments(-1.256637061),
                arguments(-0.942477796),
                arguments(-0.6283185307),
                arguments(-0.314159265),
                arguments(0.0),
                //(-pi/2;0]
                arguments(0.314159265),
                arguments(0.6283185307),
                arguments(0.942477796),
                arguments(1.256637061),
                arguments(1.57079632679),
                //(0;pi/2]
                arguments(1.884955592),
                arguments(2.1991148575),
                arguments(2.513274123),
                arguments(2.827433388),
                arguments(3.141592654)
                //(pi/2;pi]
        );
    }

    @DisplayName("Sin: Unit Test")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testSin(Double value) throws ODZException {
        Assertions.assertEquals(Math.sin(value),sin.compute(value,accuracy), accuracy);
    }
}
