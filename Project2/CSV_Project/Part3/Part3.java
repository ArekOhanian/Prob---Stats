import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

public class Part3 {

    public static void main(String[] args) {
        function funct = new function();
        XYSeries series = funct.f();

        XYSeriesCollection set = new XYSeriesCollection();
        set.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Function Plot: y = 3x^2 + 7x + 3", "x", "y", set);

        JFrame frame = new JFrame("Function plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
