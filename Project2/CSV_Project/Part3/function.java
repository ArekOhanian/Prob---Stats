
import org.jfree.data.xy.XYSeries;

public class function {
    double x;
    double y;
    XYSeries original = new XYSeries("y = 3x^2 + 7x + 3");
    public XYSeries f(){
        for(x = 0; x  <= 100; x++){
            y = 3 * ((int) Math.pow(x, 2)) + (7 * x) + 3;
            original.add(x,y);
        }

        return original;
    }
    

    public double getY(){
        return y;
    }
}
