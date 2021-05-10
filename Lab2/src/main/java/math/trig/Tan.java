package math.trig;

import exceptions.ODZException;
import math.Function;

public class Tan implements Function {
    private final Function sin;
    private final Function cos;
    public Tan(Function sin, Function cos){
        this.cos=cos;
        this.sin=sin;
    }

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x%(Math.PI/2)==0 && x%Math.PI!=0) throw
                new ODZException("tan(x)-x can't pi/2*N");
        else return sin.compute(x,accuracy)/cos.compute(x,accuracy);
    }

    @Override
    public String getDescription(){
        return "tan(x)";
    }
}
