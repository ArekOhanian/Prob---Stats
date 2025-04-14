package Project2.CSV_Project;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CSVSmoother {

    private ArrayList<OrderedPair> orderedPairsSmoothed;
    private int windowValue;
    DataHandeler data = new DataHandeler();
    File file = new File("OrderedPairSalted.csv");
    ArrayList<OrderedPair> smootedPairs = new ArrayList<>();
    

    public ArrayList<OrderedPair> CSVSmoothing() throws FileNotFoundException{
        windowValue = 5;
        ArrayList<OrderedPair> rippedPairs = data.CSVrip(file);
        
        return orderedPairsSmoothed;
    }

    public int getSmootedNumb(int windowValue, ArrayList<OrderedPair> saltedList){
        int smoothedNumb = 0;
        for(int i = 0; i < windowValue; i++){
            
        }
        return smoothedNumb;
    }

}
