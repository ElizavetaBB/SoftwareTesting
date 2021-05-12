package math.trig;

import exceptions.ODZException;
import math.Function;
import stubs.SinStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

public class TestCos{
    private static Function cos;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        cos=new Cos(new SinStub());
        accuracy=0.000001;
    }

    static Stream<Arguments> passedValues() {
        return Stream.of(
                arguments(-3*Math.PI/2),
                arguments(-4.398229715),
                arguments(-4.0840704496),
                arguments(-3.769911184),
                arguments(-3.4557519189),
                arguments(-Math.PI),
                //[-3*pi/2;-pi]
                arguments(-2.827433388),
                arguments(-2.513274123),
                arguments(-2.1991148575),
                arguments(-1.884955592),
                arguments(-1.57079632679),
                //(-pi;-pi/2]
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
                arguments(1.57079632679)
                //(0;pi/2]
        );
    }

    @DisplayName("Cos: Integration Test")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testCos(double value) throws ODZException {
        Assertions.assertEquals(Math.cos(value),cos.compute(value,accuracy), accuracy);
    }
}
