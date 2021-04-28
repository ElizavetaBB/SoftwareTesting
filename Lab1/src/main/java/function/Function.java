package function;

public class Function {
    private double accuracy=0.000000000001;

    public double asin(double x) {
        if (Math.abs(x)>1) return Double.NaN;
        double y=x>0?x:-x;
        double sum=y;
        double previous=0;
        int n=1;
        while (Math.abs(y-previous)>accuracy) {
            previous=y;
            sum*=(2*n+1)*1.0/(2*n)*x*x;
            y+=sum/(2*n+1)/(2*n+1);
            n++;
        }
        if (x<0) y*=-1;
        return y;
    }
}
