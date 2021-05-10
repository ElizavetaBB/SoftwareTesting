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

    @Override
    public String getDescription(){
        return "x<=0: csc(x)-sin(x)+tan(x))*cos(x)-tan(x)/cos(x)+sin(x)" +
                "; x>0: ((log_10(x)-log_5(x))^2/ln(x))^3+" +
                "(log_2(x)+log_5(x))^2";
    }
}
