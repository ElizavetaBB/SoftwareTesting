package function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class FunctionTest {

    private static Function function;

    @BeforeAll
    public static void init(){
        function=new Function();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/passedTests.csv",numLinesToSkip = 1)
    public void checkPassedTests(double input,double expected) {
        Assertions.assertEquals(expected,function.asin(input),0.001);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/failedTests",numLinesToSkip = 1)
    public void checkFailedTests(double input,double expected){
        Assertions.assertEquals(expected,function.asin(input));
    }
}