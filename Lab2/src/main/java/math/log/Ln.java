package math.log;

import exceptions.ODZException;
import math.Function;

public class Ln implements Function {

    @Override
    public double compute(double x, double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x>0){
            int i=1;
            double previous=0;
            double current=(x-1)/(x+1);
            double partMul=(x-1)/(x+1);
            while (Math.abs(10*(current-previous))>=accuracy){
                partMul*=(x-1)*(x-1)/(x+1)/(x+1);
                previous=current;
                current+=partMul/(2*i+1);
                i++;
            }
            current*=2;
            return current;
        } else throw new ODZException("ln(x)-x can't be lower or equals 0");
    }

}
