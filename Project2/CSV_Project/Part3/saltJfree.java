import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.jfree.data.xy.XYSeries;
public class saltJfree {
    XYSeries salted = new XYSeries("salted");
    RandomGenerator rng = new JDKRandomGenerator();
    
}
