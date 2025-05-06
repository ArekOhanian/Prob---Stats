import org.jfree.data.xy.XYSeries;
public class Smooth {
    XYSeries smoothed = new XYSeries("Smoothed");

    //here we smooth the data given the salted graph and a window value
    //this is done with a moving average
    private XYSeries smoothSeries(XYSeries salted, int windowValue){
        for (int i = 0; i < salted.getItemCount(); i++){
            double total = 0;
            int n = 0;

            for(int j = i - windowValue/2; j <= i + windowValue/2; j++){
                if(j >= 0 && j < salted.getItemCount()){
                    total += salted.getY(j).doubleValue();
                    n++;
                }
            }
            double x =  salted.getX(i).doubleValue();
            double y = total/n;
            smoothed.add(x,y);
        }
        return smoothed;
    }

    public XYSeries getSmoothed(XYSeries salted, int windowValue){
        return smoothSeries(salted, windowValue);
    }
}
