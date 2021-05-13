package math.trig;

import exceptions.ODZException;
import math.Function;

public class Csc implements Function {
    private final Function sin;
    public Csc(Function sin){
        this.sin=sin;
    }

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x%Math.PI==0) throw new ODZException("Csc(x) - x can't be pi*N");
        else return 1/sin.compute(x,accuracy);
    }
}
