package Project2.CSV_Project.Part1;

import java.io.FileNotFoundException;

public class CSVTester {
    public static void main(String[] args) throws FileNotFoundException{
        CSVWritter a = new CSVWritter();
        CSVSalter b = new CSVSalter();
        CSVSmoother c = new CSVSmoother();
        a.CSVPlotter();
        b.CSVSaltWritter();
        c.CSVSmootherOut(5);
    }
}
