package Project2.CSV_Project;
import java.util.ArrayList;
import java.io.FileWriter;

public class CSVWritter {
    //global variables
    ArrayList <OrderedPair> pairs = new ArrayList<>();
    Function f = new Function();
    

    //for loop to find the ordered pairs
    public void orderedPairFiller(){
        for(int i = 0; i <= 256; i++){
            pairs.add(f.findResult(i));
        }
    }


    //Writing to the file
    public void CSVPlotter(){
        try {
            orderedPairFiller();
            //intializing the file 
            FileWriter fileWriter = new FileWriter("OrderedPair.csv");
            //printing the ordered pairs
            for (int i = 0; i < pairs.size(); i++){
                String pairAt = pairs.get(i).getX() + ", " + pairs.get(i).getY() + "\n";
                fileWriter.write(pairAt);
            }
            //closing the writer
            fileWriter.close();
        } catch (Exception e) {
            //this happens if the file fails to write
            System.out.println("Failed to write file");
        }

    }
}
