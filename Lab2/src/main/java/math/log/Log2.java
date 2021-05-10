package math.log;

import exceptions.ODZException;
import math.Function;

public class Log2 implements Function {
    private final Function ln;

    public Log2(Function ln){
        this.ln=ln;
    }

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        return ln.compute(x,accuracy)/ln.compute(2,accuracy);
    }

    @Override
    public String getDescription(){
        return "log_2(x)";
    }

}
