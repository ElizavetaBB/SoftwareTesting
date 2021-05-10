package math.trig;

import exceptions.ODZException;
import math.Function;

public class Sin implements Function {

    @Override
    public double compute(double x,double accuracy) throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        boolean signReverse=false;
        double newX=x;
        if (newX<0){
            newX=-newX;
            signReverse=true;
        }
        newX%=(2*Math.PI);
        if (newX>Math.PI){
            signReverse=!signReverse;
            newX-=Math.PI;
        }
        if (Math.abs(newX-Math.PI)>accuracy){
            long i=3;
            int sign=-1;
            double previous=0;
            double current=newX;
            double partMul=newX;
            long factor=1;
            while (Math.abs(current-previous)>accuracy){
                previous=current;
                for (long j=i;j>i-2;j--){
                    partMul*=newX;
                    factor*=j;
                }
                current+=sign*partMul/factor;
                sign*=-1;
                i+=2;
            }
            if(signReverse) current=-current;
            return current;
        }else return 0;
    }

    @Override
    public String getDescription(){
        return "sin(x)";
    }

}
