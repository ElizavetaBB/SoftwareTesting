package math.system;

import exceptions.ODZException;
import math.Function;

public class FirstFunction implements Function {
    private final Function cos;
    private final Function sin;
    private final Function csc;
    private final Function tan;

    public FirstFunction(Function sin,Function cos,Function csc,Function tan){
        this.cos=cos;
        this.sin=sin;
        this.csc=csc;
        this.tan=tan;
    }

    @Override
    public double compute(double x,double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x<=0){
            if (cos.compute(x,accuracy)==0) throw new ODZException("" +
                    "1st function(x) - cos(x) can't be 0");
            return ((csc.compute(x,accuracy)-sin.compute(x,accuracy)+tan.compute(x,accuracy))*
                    cos.compute(x,accuracy)-tan.compute(x,accuracy)/cos.compute(x,accuracy))
                    +sin.compute(x,accuracy);
        }else throw new ODZException("1st function(x) - x can't more than 0");
    }

}
