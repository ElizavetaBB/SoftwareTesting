package math.system;

import exceptions.ODZException;
import math.Function;
import math.trig.Cos;
import math.trig.Csc;
import stubs.SinStub;
import math.trig.Tan;
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

public class TestFirstFunction {
    private static Function firstFunction;
    private static FunctionSystem system;
    private static Cos cos;
    private static Csc csc;
    private static Tan tan;
    private static double accuracy;

    @BeforeAll
    public static void setUp(){
        cos=mock(Cos.class);
        csc=mock(Csc.class);
        tan=mock(Tan.class);
        firstFunction=new FirstFunction(new SinStub(),cos,csc,tan);
        accuracy=0.000001;
        system=new FunctionSystem(firstFunction,null);
    }

    static Stream<Arguments> passedValues() {
        return Stream.of(
                arguments(-6.146548245743669,7.271750601363942),
                arguments(-6.009911184307752,3.556593642010017),
                arguments(-5.873274122871835,2.259128805655145),
                arguments(-5.736637061435918,1.5271004447993477),
                arguments(-5.6000000000000005,0.9520481411360096),
                //(-2pi;-5.6]
                arguments(-5.4224777960769375,0.09760254304141658),
                arguments(-5.244955592153875,-1.4667272615863949),
                arguments(-5.067433388230813,-5.8386956756500625),
                arguments(-4.889911184307751,-29.58911626983455),
                //(-5.6,-3pi/2)
                arguments(-4.5299111843077515,-27.902364008484522),
                arguments(-4.347433388230813,-5.513694092037334),
                arguments(-4.164955592153875,-1.6091649959944805),
                arguments(-3.982477796076937,-0.5834538000903212),
                arguments(-3.8,-0.563042399526441),
                //(-3pi/2;-3.8]
                arguments(-3.6683185307179587,-0.9523382649328163),
                arguments(-3.5366370614359175,-1.7251412159157704),
                arguments(-3.4049555921538763,-3.2160887465216836),
                arguments(-3.273274122871835,-7.290975900891769),
                //(-3.8;-pi)
                arguments(-2.9932741228718345,6.402104553237114),
                arguments(-2.844955592153876,2.727122558646638),
                arguments(-2.696637061435917,1.3760461142027645),
                arguments(-2.5483185307179586,0.7145910832279755),
                arguments(-2.4,0.48490965870403413),
                //(-pi;-2.4]
                arguments(-2.2341592653589792,0.7984874049401148),
                arguments(-2.0683185307179586,2.2243468366543677),
                arguments(-1.902477796076938,7.062185390248939),
                arguments(-1.7366370614359172,34.223195265645714),
                //(-2.4;-pi/2)
                arguments(-1.3766370614359174,24.38811172222935),
                arguments(-1.1824777960769381,4.546239693344919),
                arguments(-0.9883185307179588,0.89018203371009),
                arguments(-0.7941592653589795,-0.45726552869226833),
                arguments(-0.6,-1.296041822897679),
                //(-pi/2;-0.6]
                arguments(-0.48,-1.8478453063770834),
                arguments(-0.36,-2.6294000112503824),
                arguments(-0.24,-4.078935687968661),
                arguments(-0.12,-8.292415233809004)
                //(-0.6;0)
        );
    }

    static Stream<Arguments> valuesWithException() {
        return Stream.of(
                arguments(-2*Math.PI,true),
                arguments(-3*Math.PI/2,false),
                arguments(-Math.PI,true),
                arguments(-Math.PI/2,false),
                arguments(0.0,true),
                arguments(0.1,false),
                arguments(5,false)
        );
    }

    @DisplayName("First Function: Integration Test")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testFirstFunction(double value, double expected) throws ODZException {
        when(cos.compute(value,accuracy)).thenReturn(Math.cos(value));
        when(csc.compute(value,accuracy)).thenReturn(1/Math.sin(value));
        when(tan.compute(value,accuracy)).thenReturn(Math.tan(value));
        Assertions.assertEquals(expected,firstFunction.compute(value,accuracy),accuracy);
    }

    @DisplayName("First Function: Integration Test with Exceptions")
    @ParameterizedTest
    @MethodSource("valuesWithException")
    public void testFirstFunctionWithExceptions(double value, boolean piN)
            throws ODZException {
        if (value<=0.0) {
            if (piN) {
                when(csc.compute(value, accuracy)).thenThrow(new ODZException(""));
            } else {//pi/2*N
                when(cos.compute(value, accuracy)).thenThrow(new ODZException(""));
                when(tan.compute(value, accuracy)).thenThrow(new ODZException(""));
            }
        }
        Assertions.assertThrows(ODZException.class,()->firstFunction.compute(value,accuracy));
    }

    @DisplayName("First Function: Integration Test in System")
    @ParameterizedTest
    @MethodSource("passedValues")
    public void testFirstFunctionInSystem(double value, double expected) throws ODZException {
        when(cos.compute(value,accuracy)).thenReturn(Math.cos(value));
        when(csc.compute(value,accuracy)).thenReturn(1/Math.sin(value));
        when(tan.compute(value,accuracy)).thenReturn(Math.tan(value));
        Assertions.assertEquals(expected,system.compute(value,accuracy),accuracy);
    }
}
