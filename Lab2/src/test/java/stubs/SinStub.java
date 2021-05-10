package stubs;

import exceptions.ODZException;
import math.Function;

public class SinStub implements Function {

    @Override
    public double compute(double x,double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        return Math.sin(x);
    }

    @Override
    public String getDescription(){
        return "sin(x) stub";
    }

}
