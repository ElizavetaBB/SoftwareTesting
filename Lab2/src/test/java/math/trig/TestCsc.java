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

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestCsc {
    private static Function csc;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        csc=new Csc(new SinStub());
        accuracy=0.000001;
    }

    static Stream<Arguments> valuesRange() {
        return Stream.of(
                arguments(-6.066585307),
                arguments(-5.849985307),
                arguments(-5.633385307),
                arguments(-5.416785307),
                arguments(-5.2),
                //(-2*pi;-5.2]
                arguments(-4.92),
                arguments(-4.64),
                arguments(-4.36),
                arguments(-4.08),
                arguments(-3.8),
                //(-5.2;-3.8]
                arguments(-3.799992654),
                arguments(-3.668312654),
                arguments(-3.536632654),
                arguments(-3.404952654),
                arguments(-3.273272654),
                //(-3.8;-pi)
                arguments(-2.953274154),
                arguments(-2.764955654),
                arguments(-2.576637154),
                arguments(-2.388318654),
                arguments(-2.200000154),
                //(-pi;-2.2]
                arguments(-1.92),
                arguments(-1.64),
                arguments(-1.36),
                arguments(-1.08),
                arguments(-0.8),
                //(-2.2;-0.8]
                arguments(-0.64),
                arguments(-0.48),
                arguments(-0.32),
                arguments(-0.16)
                //(-0.8;0)
        );
    }

    static Stream<Arguments> valuesWithExceptions() {
        return Stream.of(
                arguments(-2*Math.PI),
                arguments(-Math.PI),
                arguments(0.0)
        );
    }

    @DisplayName("Csc: Integration Test")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testCsc(Double value) throws ODZException {
        Assertions.assertEquals(1/Math.sin(value),csc.compute(value,accuracy), accuracy);
    }

    @DisplayName("Csc: Integration Test with exceptions")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesWithExceptions")
    public void testCscWithExceptions(Double value){
        Assertions.assertThrows(ODZException.class,()->csc.compute(value,accuracy));
    }
}
