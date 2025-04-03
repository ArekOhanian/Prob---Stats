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
    ArrayList<OrderedPair> saltedPairs = new ArrayList<>();
    private int randNumb;
    private int neg;
    
    public void CSVSaling() throws FileNotFoundException{
        Scanner fileScan = new Scanner(file);
        String y;
        int x = 0;
        while(fileScan.hasNext()){
            String line = fileScan.nextLine();
            randNumb = rand.nextInt(1000);
            neg = rand.nextInt(2);
            if(neg == 1){
                randNumb = (randNumb * -1);
            }
            y = line.substring(line.indexOf(", ") + 2, line.length());
            int yN = Integer.parseInt(y);
            OrderedPair pair = new OrderedPair(x, yN + (randNumb));
            x++;
            saltedPairs.add(pair);
        }
        fileScan.close();
    }

    public void CSVSaltWritter() throws FileNotFoundException{
        CSVSaling();
        PrintWriter out = new PrintWriter("OrderedPairSalted.csv");
        for(int i = 0; i < saltedPairs.size(); i++){
            out.println(saltedPairs.get(i).getX() + ", " + saltedPairs.get(i).getY());
        }
        out.close();
    }


        public ArrayList<OrderedPair> getSaltedPairs(){
            return saltedPairs;
        }
    }
