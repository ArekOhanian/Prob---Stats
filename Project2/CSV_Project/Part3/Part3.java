import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;

public class Part3 {

    public static void main(String[] args) {
        //here we are delcaring and filling in the xy serieses for the original salted and smoothed graphs
        Function funct = new Function();
        SaltJFree salter = new SaltJFree();
        Smooth smooth = new Smooth();
        XYSeries original = funct.getOriginal();
        XYSeries salted = salter.getSalted(original);
        XYSeries smoothed = smooth.getSmoothed(salted, 25);
        XYSeriesCollection set = new XYSeriesCollection();
        set.addSeries(original);
        set.addSeries(salted);
        set.addSeries(smoothed);

        //this is creating the new chart to display our collection of xy serieses
        JFreeChart chart = ChartFactory.createXYLineChart("Function Plot: y = 3x^2 + 7x + 3", "x", "y", set);

        JFrame frame = new JFrame("Function plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
