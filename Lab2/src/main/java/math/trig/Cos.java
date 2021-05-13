package math.trig;

import exceptions.ODZException;
import math.Function;

public class Cos implements Function {
    private final Function sin;
    public Cos(Function sin){
        this.sin=sin;
    }

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        double result=0;
        if (x%(Math.PI/2)==0 && x%Math.PI!=0){
            return result;
        }
        result=Math.sqrt(1-Math.pow(sin.compute(x,accuracy),2));
        double newX=x%(2*Math.PI);
        if (newX>Math.PI/2 && newX<3*Math.PI/2 || newX<-Math.PI/2 &&
                newX>-3*Math.PI/2) result=-result;
        return result;
    }
}
