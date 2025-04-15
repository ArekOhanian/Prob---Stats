
package Project2.CSV_Project;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class DataHandeler {
    
    
    public ArrayList<OrderedPair> CSVrip(File source) throws FileNotFoundException{
        Scanner sc = new Scanner(source);
        ArrayList<OrderedPair> rippedPairs = new ArrayList<>(); 
        int x = 0;
        while(sc.hasNext()){
            String line = sc.nextLine();
            String y = line.substring(line.indexOf(", ") + 2, line.length());
            double yD = Double.parseDouble(y);
            OrderedPair pair = new OrderedPair(x, yD);
            rippedPairs.add(pair);
        }
        sc.close();
        return rippedPairs;
    }

    public void CSVWritter(String fileName, ArrayList<OrderedPair> orderedPairs) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(fileName);
        for(int i = 0; i < orderedPairs.size(); i++){
            out.println(i + ", " + orderedPairs.get(i).getY());
        }
        out.close();
    }
}
