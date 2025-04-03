package Project2.CSV_Project;

import java.io.FileNotFoundException;

public class CSVTester {
    public static void main(String[] args) throws FileNotFoundException{
        CSVWritter a = new CSVWritter();
        CSVSalter b = new CSVSalter();
        a.CSVPlotter();
        b.CSVSaltWritter();
    }
}
