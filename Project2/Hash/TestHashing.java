package Project2.Hash;
import java.util.LinkedList;

public class TestHashing {
    public static void main(String[] args){
        YourNameSimpleHashMap hash = new YourNameSimpleHashMap(5);
        hash.put("Hello");
        hash.put("world");
        hash.put("what");
        hash.put("why");

        System.out.println(hash.get("Hello"));
        System.out.println(hash.contains("Hello"));
        System.out.println(hash.get("world"));
    }

}
