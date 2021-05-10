package stubs;

import exceptions.ODZException;
import math.Function;

public class LnStub implements Function {

    @Override
    public double compute(double x,double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x<=0) throw new ODZException("ln(x)-x can't be lower or equals 0");
        return Math.log(x);
    }

    @Override
    public String getDescription(){
        return "ln(x) stub";
    }
}
