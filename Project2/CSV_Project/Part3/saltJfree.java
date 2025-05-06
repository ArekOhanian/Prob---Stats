import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.jfree.data.xy.XYSeries;

public class SaltJFree {
    //here we use apache commons to create a random value to add or subtact from the original graph so that we can salt the data
    RandomGenerator rng = new JDKRandomGenerator();
    private XYSeries saltedSeries = new XYSeries("salted");
    private XYSeries saltSeries(XYSeries original){
        rng.setSeed(System.currentTimeMillis());

        for(int i = 0; i < original.getItemCount(); i++){
            double x = i;
            double y = original.getY(i).doubleValue();
            double rand = rng.nextGaussian() * 1000;
            saltedSeries.add(x,y+rand);
        }
        return saltedSeries;
    }

        //getter method for the salted series
        public XYSeries getSalted(XYSeries original){
            return saltSeries(original);
        }
}
