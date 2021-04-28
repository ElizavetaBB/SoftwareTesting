import function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class FunctionTest {

    Function function=new Function();

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