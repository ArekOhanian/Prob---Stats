package Project2.CSV_Project;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CSVSmoother {
    Random rand = new Random();
    private ArrayList<OrderedPair> orderedPairsSmoothed;
    private int random;
    private int windowValue;
    private OrderedPair smoothPair;
    

    public ArrayList<OrderedPair> CSVSmoothing(ArrayList<OrderedPair> saltedList){
        windowValue = 5;
        int counter = 1;
        for(int i = 0; i < saltedList.size(); i++){
            if(counter == windowValue){
                counter = 1;
            }
            
            counter++;
        }

        return orderedPairsSmoothed;
    }
}
