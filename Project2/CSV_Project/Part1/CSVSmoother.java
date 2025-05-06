package Project2.CSV_Project.Part1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CSVSmoother {

    DataHandeler data = new DataHandeler();
    File file = new File("OrderedPairSalted.csv");
    

    public void CSVSmootherOut(int windowValue) throws FileNotFoundException{
        ArrayList<OrderedPair> rippedPairs = data.CSVrip(file);
        ArrayList<OrderedPair> smoothedParis = CSVSmoothing(rippedPairs, windowValue);
        data.CSVWritter("OrderedPairSmooth.csv", smoothedParis);
    }


    public ArrayList<OrderedPair> CSVSmoothing(ArrayList<OrderedPair> rippedPairs, int windowValue) throws FileNotFoundException{
        ArrayList<OrderedPair> smootedPairs = new ArrayList<>();
        for(int i = 0; i < rippedPairs.size(); i++){
            OrderedPair smoothed = new OrderedPair(i, getSmootedNumb(windowValue, rippedPairs, i));
            smootedPairs.add(smoothed);
        }
        return smootedPairs;
    }


    //here is the math for the smoother Where I take the window value and go left and right of the current point and average their points.
    //if its at the start then I change how many spots left and right I go to get the same amount of data to be averaged
    public double getSmootedNumb(int windowValue, ArrayList<OrderedPair> saltedList, int focusedPoint){
        double smoothedNumb = 0;
        if(focusedPoint < windowValue){
            for(int i = 0; i < windowValue*2; i++){
                smoothedNumb += saltedList.get(i).getY();
            }
            smoothedNumb = smoothedNumb/(windowValue*2);
        } 
        else if(focusedPoint > saltedList.size() - windowValue){
            int difference = (windowValue) - (saltedList.size() - focusedPoint);
            for(int i = focusedPoint - windowValue - difference; i < saltedList.size(); i++){
                smoothedNumb += saltedList.get(i).getY();
            }
            smoothedNumb = smoothedNumb/(windowValue*2);
        } 
        else {    
            for(int i = focusedPoint - windowValue; i < focusedPoint + windowValue; i++){
                smoothedNumb += saltedList.get(i).getY();
            }
            smoothedNumb = smoothedNumb/(windowValue*2);
        }
        return smoothedNumb;
    }

}
