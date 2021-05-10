package math.system;

import exceptions.ODZException;
import math.Function;
import math.log.*;
import stubs.LnStub;
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

public class TestSecondFunction {
    private static Function secondFunction;
    private static Log2 log2;
    private static Log5 log5;
    private static Log10 log10;
    private static FunctionSystem system;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        log2=mock(Log2.class);
        log5=mock(Log5.class);
        log10=mock(Log10.class);
        secondFunction=new SecondFunction(new LnStub(),log2,log5,log10);
        accuracy=0.000001;
        system=new FunctionSystem(null,secondFunction);
    }

    static Stream<Arguments> valuesRange() {
        return Stream.of(
                arguments(0.08,27.176551992729955),
                arguments(0.16,14.307065988342146),
                arguments(0.24,8.676499340191812),
                arguments(0.32,5.531024618819573),
                arguments(0.4,3.5767994362933675),
                //(0,0.4]
                arguments(0.52,1.8217424456110807),
                arguments(0.64,0.8485134382771268),
                arguments(0.76,0.32086004532516654),
                arguments(0.88,0.06961774131550848),
                //(0.4,1)
                arguments(2.8,4.516374459020646),
                arguments(4.6,9.921555713062153),
                arguments(6.3999999999999995,14.680318961011269),
                arguments(8.2,18.862012839453627),
                arguments(10.0,22.587773696774363),
                //(1;10]
                arguments(11.6,25.5935852337533),
                arguments(13.2,28.363228880507084),
                arguments(14.799999999999999,30.93435991310618),
                arguments(16.4,33.33623160142747),
                arguments(18.0,35.59196995808797)
                //(10,18]
        );
    }

    static Stream<Arguments> valuesWithException() {
        return Stream.of(
                arguments(1.0),
                arguments(-3),
                arguments(0.0),
                arguments(-0.1)
        );
    }

    @DisplayName("Second Function: Integration Test")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testSecondFunction(double value, double expected) throws ODZException {
        when(log2.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(2));
        when(log5.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(5));
        when(log10.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(10));
        Assertions.assertEquals(expected,secondFunction.compute(value,accuracy),accuracy);
    }

    @DisplayName("Second Function: Integration Test with Exceptions")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesWithException")
    public void testSecondFunctionWithExceptions(double value) throws ODZException {
        if (value<=0){
            when(log2.compute(value,accuracy)).thenThrow(new ODZException(""));
            when(log5.compute(value,accuracy)).thenThrow(new ODZException(""));
            when(log10.compute(value,accuracy)).thenThrow(new ODZException(""));
        }
        Assertions.assertThrows(ODZException.class,()->secondFunction.compute(value,accuracy));
    }

    @DisplayName("Second Function: Integration Test in System")
    @ParameterizedTest(name = "{index}: Check range of values, x = {0}")
    @MethodSource("valuesRange")
    public void testSecondFunctionInSystem(double value, double expected) throws ODZException {
        when(log2.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(2));
        when(log5.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(5));
        when(log10.compute(value,accuracy)).thenReturn(Math.log(value)/Math.log(10));
        Assertions.assertEquals(expected,system.compute(value,accuracy),accuracy);
    }
}
