package math.system;

import exceptions.ODZException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSystem {

    private static FunctionSystem system;
    private static double accuracy;

    @BeforeAll
    public static void setUp() throws ODZException{
        FirstFunction firstFunction=mock(FirstFunction.class);
        SecondFunction secondFunction=mock(SecondFunction.class);
        system=new FunctionSystem(firstFunction,secondFunction);
        accuracy=0.000001;
        when(firstFunction.compute(-2*Math.PI,accuracy)).thenThrow(new ODZException(""));
        when(firstFunction.compute(-3*Math.PI/2,accuracy)).thenThrow(new ODZException(""));
        when(firstFunction.compute(-Math.PI,accuracy)).thenThrow(new ODZException(""));
        when(firstFunction.compute(-Math.PI/2,accuracy)).thenThrow(new ODZException(""));
        when(firstFunction.compute(0.0,accuracy)).thenThrow(new ODZException(""));
        when(secondFunction.compute(1.0,accuracy)).thenThrow(new ODZException(""));
        when(firstFunction.compute(-6.146548245743669,accuracy)).
                thenReturn(7.271750601363942);
        when(firstFunction.compute(-4.5299111843077515,accuracy)).
                thenReturn(-27.902364008484522);
        when(firstFunction.compute(-3.273274122871835,accuracy)).
                thenReturn(-7.290975900891769);
        when(firstFunction.compute(-2.2341592653589792,accuracy)).
                thenReturn(0.7984874049401148);
        when(firstFunction.compute(-0.36,accuracy)).thenReturn(-2.6294000112503824);
        when(secondFunction.compute(0.08,accuracy)).thenReturn(27.176551992729955);
        when(secondFunction.compute(0.52,accuracy)).thenReturn(1.8217424456110807);
        when(secondFunction.compute(2.8,accuracy)).thenReturn(4.516374459020646);
        when(secondFunction.compute(11.6,accuracy)).thenReturn(25.5935852337533);
    }

    static Stream<Arguments> passedValues() {
        return Stream.of(
                arguments(-6.146548245743669,7.271750601363942),
                arguments(-4.5299111843077515,-27.902364008484522),
                arguments(-3.273274122871835,-7.290975900891769),
                arguments(-2.2341592653589792,0.7984874049401148),
                arguments(-0.36,-2.6294000112503824),
                arguments(0.08,27.176551992729955),
                arguments(0.52,1.8217424456110807),
                arguments(2.8,4.516374459020646),
                arguments(11.6,25.5935852337533)
        );
    }

    static Stream<Arguments> valuesWithException() {
        return Stream.of(
                arguments(-2*Math.PI),
                arguments(-3*Math.PI/2),
                arguments(-Math.PI),
                arguments(-Math.PI/2),
                arguments(0.0),
                arguments(1.0)
        );
    }

    @DisplayName("FunctionSystem: Integration Test")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testSystem(double value, double expected) throws ODZException {
        Assertions.assertEquals(expected,system.compute(value,accuracy),accuracy);
    }

    @DisplayName("FunctionSystem: Integration Test with Exceptions")
    @ParameterizedTest
    @MethodSource("valuesWithException")
    public void testSystemWithExceptions(double value){
        Assertions.assertThrows(ODZException.class,()->system.compute(value,accuracy));
    }
}
