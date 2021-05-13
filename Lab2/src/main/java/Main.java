import math.Function;
import math.log.*;
import math.system.*;
import math.system.FunctionSystem;
import math.trig.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static String[] names;
    private static Function[] functions;

    private static void initializeFunction() {
        names=new String[]{"sin","cos","tan","csc","ln","log_2","log_5","log_10","" +
                "(csc(x)-sin(x)+tan(x))*cos(x)-tan(x)/cos(x)+sin(x)","" +
                "((log_10(x)-log_5(x))^2/ln(x))^3+(log_2(x)+log_5(x))^2","" +
                "x<=0:(csc(x)-sin(x)+tan(x))*cos(x)-tan(x)/cos(x)+sin(x)," +
                "x>0:((log_10(x)-log_5(x))^2/ln(x))^3+(log_2(x)+log_5(x))^2"};
        Ln ln=new Ln();
        Log2 log2=new Log2(ln);
        Log5 log5=new Log5(ln);
        Log10 log10=new Log10(ln);
        Sin sin=new Sin();
        Cos cos=new Cos(sin);
        Tan tan=new Tan(sin,cos);
        Csc csc=new Csc(sin);
        FirstFunction firstFunction=new FirstFunction(sin,cos,csc,tan);
        SecondFunction secondFunction=new SecondFunction(ln,log2,log5,log10);
        functions=new Function[]{sin,cos,tan,csc,
                ln,log2,log5,log10,firstFunction,
                secondFunction,new FunctionSystem(firstFunction,secondFunction)};
    }

    public static void main(String[] args) throws IOException {
        initializeFunction();
        BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выберите функцию из предложенных (введите цифру):");
        for (int i=0;i<names.length;i++){
            System.out.println(i+"."+names[i]);
        }
        int key=-1;
        while (key==-1){
            int n = Integer.parseInt(input.readLine().trim());
            if (n<0 || n>names.length-1){
                System.out.println("Введите число от 0 до "+(names.length-1));
                break;
            }
            key=n;
        }
        System.out.println("Введите начальное значение:");
        double startX = Double.parseDouble(input.readLine().trim());
        System.out.println("Введите конечное значение:");
        double endX = Double.parseDouble(input.readLine().trim());
        System.out.println("Введите шаг:");
        double step = Double.parseDouble(input.readLine().trim());
        System.out.println("Введите точность:");
        double accuracy=Double.parseDouble(input.readLine().trim());
        System.out.println("Введите название csv файла:");
        String fileName = input.readLine();
        if (fileName.isEmpty()) {
            fileName = "lab2.csv";
        }
        Writer writer=new Writer(fileName,functions[key],false);
        writer.addDescription(names[key]);
        writer.write(startX,endX,step,accuracy);
        writer.close();
    }
 }

