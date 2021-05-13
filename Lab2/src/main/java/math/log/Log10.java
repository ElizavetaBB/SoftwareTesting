package math.log;

import exceptions.ODZException;
import math.Function;

public class Log10 implements Function {

    private final Function ln;
    public Log10(Function ln){
        this.ln=ln;
    }

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        return ln.compute(x,accuracy)/ln.compute(10,accuracy);
    }

}
