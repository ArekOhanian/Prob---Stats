package Project2.CSV_Project;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class CSVWritter {
    //global variables
    ArrayList <OrderedPair> pairs = new ArrayList<>();
    Function f = new Function();
    DataHandeler data = new DataHandeler();
    

    //for loop to find the ordered pairs
    public void orderedPairFiller(){
        for(int i = 0; i < 100; i++){
            pairs.add(f.findResult(i));
        }
    }


    //Writing to the file
    public void CSVPlotter() throws FileNotFoundException{
        orderedPairFiller();
        data.CSVWritter("OrderedPair.csv", pairs);
    }
}
