package Project2.CSV_Project;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class CSVSalter{
    //global variables
    Random rand = new Random();
    File file = new File("OrderedPair.csv");
    DataHandeler data = new DataHandeler();
    ArrayList<OrderedPair> saltedPairs = new ArrayList<>();
    private int randNumb;
    private int neg;
    
    public void CSVSaling() throws FileNotFoundException{
        ArrayList<OrderedPair> rippedPairs = data.CSVrip(file);
        for(int i = 0; i < rippedPairs.size(); i++){
            randNumb = rand.nextInt(1000);
            neg = rand.nextInt(2);
            if(neg == 1){
                randNumb = randNumb * -1;
            }
            OrderedPair pair = new OrderedPair(i, (rippedPairs.get(i).getY() + randNumb));
            saltedPairs.add(pair);
        }
    }

    public void CSVSaltWritter() throws FileNotFoundException{
        CSVSaling();
        data.CSVWritter("OrderedPairSalted.csv", saltedPairs);
    }

    public ArrayList<OrderedPair> getSaltedPairs(){
        return saltedPairs;
    }
}
