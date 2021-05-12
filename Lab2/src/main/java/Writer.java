import math.Function;
import exceptions.ODZException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer{

    private final FileWriter writer;
    private final Function function;

    public Writer(String name, Function function,boolean append) throws IOException {
        File file=new File(name);
        writer = new FileWriter(file,append);
        this.function=function;
    }

    public void addDescription() throws IOException{
        writer.write("x,"+function.getDescription()+"\n");
        writer.flush();
    }

    public void write(double start, double finish, double step,double accuracy)
            throws IOException{
        double result;
        for (double i=start;i<finish;i+=step){
            try {
                result = function.compute(i,accuracy);
            }catch (ODZException odz){
                result=Double.NaN;
            }
            writer.write(i+","+result+"\n");
        }
        writer.flush();
    }

    public void close() throws IOException{
        writer.close();
    }

}
