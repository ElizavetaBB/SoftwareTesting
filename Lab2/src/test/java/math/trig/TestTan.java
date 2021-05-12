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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestTan {
    private static Function tan;
    private static Cos cos;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        cos=mock(Cos.class);
        tan=new Tan(new SinStub(),cos);
        accuracy=0.000001;
    }

    static Stream<Arguments> passedValues() {
        return Stream.of(
                arguments(-1.516637061),
                arguments(-1.462477796),
                arguments(-1.4083185307),
                arguments(-1.354159265),
                arguments(-1.3),
                //(-pi/2;-1.3]
                arguments(-1.24),
                arguments(-1.18),
                arguments(-1.12),
                arguments(-1.06),
                arguments(-1.0),
                //(-1.3;-1]
                arguments(-0.9),
                arguments(-0.8),
                arguments(-0.7),
                arguments(-0.6),
                arguments(-0.5),
                //(-1;-0.5]
                arguments(-0.3),
                arguments(-0.1),
                arguments(0.1),
                arguments(0.3),
                arguments(0.5),
                //(-0.5;0.5]
                arguments(0.6),
                arguments(0.7),
                arguments(0.8),
                arguments(0.9),
                arguments(1.0),
                //(0.5;1]
                arguments(1.06),
                arguments(1.12),
                arguments(1.18),
                arguments(1.24),
                arguments(1.3),
                //(1;1.3]
                arguments(1.354159265),
                arguments(1.4083185307),
                arguments(1.462477796),
                arguments(1.516637061)
                //(1.3;pi/2)
        );
    }

    static Stream<Arguments> valuesWithExceptions() {
        return Stream.of(
                arguments(-Math.PI/2),
                arguments(Math.PI/2)
        );
    }

    @DisplayName("Tan: Integration Test")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testTan(double value) throws ODZException {
        when(cos.compute(value,accuracy)).thenReturn(Math.cos(value));
        Assertions.assertEquals(Math.tan(value),tan.compute(value,accuracy), accuracy);
    }

    @DisplayName("Tan: Integration Test with exceptions")
    @ParameterizedTest
    @MethodSource("valuesWithExceptions")
    public void testTanWithExceptions(double value){
        Assertions.assertThrows(ODZException.class,()->tan.compute(value,accuracy));
    }
}
