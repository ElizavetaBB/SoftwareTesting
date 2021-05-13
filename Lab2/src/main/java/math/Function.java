package math;

import exceptions.ODZException;

public interface Function {
    double compute(double x,double accuracy) throws ODZException;
}
