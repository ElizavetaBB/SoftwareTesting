package math.system;

import exceptions.ODZException;
import math.Function;

public class FunctionSystem implements Function {
    private final Function first;
    private final Function second;

    public FunctionSystem(Function firstFunction, Function secondFunction){
        this.first=firstFunction;
        this.second=secondFunction;
    }

    @Override
    public double compute(double x,double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x<=0) return first.compute(x,accuracy);
        else return second.compute(x,accuracy);
    }
}
