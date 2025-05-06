
import org.jfree.data.xy.XYSeries;

public class Function{
    //this function creates the xy plots using the xy series 
    private XYSeries original = new XYSeries("y = 3x^2 + 7x + 3");
    public void seriesFill(){
        for(double x = 0; x  <= 100; x++){
            double y = (3 * x * x) + (7 * x) + 3;
            original.add(x,y);
        }
    }
    
    //this returns those values
    public XYSeries getOriginal(){
        seriesFill();
        return original;
    }
}
