package math.system;

import exceptions.ODZException;
import math.Function;

public class SecondFunction implements Function {
    private final Function ln;
    private final Function log2;
    private final Function log5;
    private final Function log10;

    public SecondFunction(Function ln, Function log2, Function log5, Function log10){
        this.ln=ln;
        this.log5=log5;
        this.log10=log10;
        this.log2=log2;
    }

    @Override
    public double compute(double x,double accuracy)throws ODZException {
        if (accuracy<=0) throw new ODZException("Accuracy must be more than 0");
        if (x>0){
            if (ln.compute(x,accuracy)==0) throw new ODZException("2nd function(x) " +
                    "- ln(x) can't be 0");
            return Math.pow(Math.pow(log10.compute(x,accuracy)-
                            log5.compute(x,accuracy),2)/ln.compute(x,accuracy)
                    ,3)+Math.pow(log2.compute(x,accuracy)+log5.compute(x,accuracy),2);
        }else throw new ODZException("2nd function(x) - x can't be lower or equal 0");
    }

    @Override
    public String getDescription(){
        return "((log_10(x)-log_5(x))^2/ln(x))^3+" +
                "(log_2(x)+log_5(x))^2";
    }

}
