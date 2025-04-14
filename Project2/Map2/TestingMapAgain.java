package Project2.Map2;
import java.util.HashMap;
import java.util.ArrayList;

public class TestingMapAgain {
    
    public static void main (String[] args){

        HashMap<String, Integer> hm = new HashMap<>();

        hm.put("Tom", 1);
        hm.put("Brandon", 22);
        hm.put("Lisa", 3);
        hm.put("Brandon", 2);
        hm.put("Jerry", 3);

        //sometimes helpful to have "super" data structure
        HashMap<String, ArrayList<String>> shm = new HashMap<>();

        System.out.println("The size of my map is: " + hm.size());
        System.out.println(hm.get("Jerry"));

        for(String singleValue : hm.keySet()){
            System.out.println(singleValue);
        }
    }
}
