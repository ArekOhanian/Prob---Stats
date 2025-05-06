package Project2.CSV_Project.Part1;
import java.util.Random;
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
    
    //here we use the data handeler clas to grab the pairs from the original csv we then create a random number between -1000 and 1000 to add/subtract from the original y value
    //the result is then added to the salted pairs array list
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

    //here we write the csv file with help from the data handeler class
    public void CSVSaltWritter() throws FileNotFoundException{
        CSVSaling();
        data.CSVWritter("OrderedPairSalted.csv", saltedPairs);
    }

    //getter for the salted pairs
    public ArrayList<OrderedPair> getSaltedPairs(){
        return saltedPairs;
    }
}
