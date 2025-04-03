package Project2.Hash;
import java.util.LinkedList;

public class YourNameSimpleHashMap {
    LinkedList<String>[] data;

    //Count the number of letters in the string to make it the hash
    public int dumbHash(String a){
        int pos = 0;
        for(int i = 0; i < a.length(); i++){
            pos += pos;
        }
        return pos;
    }

    //To check if the hash contains a certain value
    public boolean contains(LinkedList<String> b, String specific){
        if(b.contains(specific)){
        return true;
        }
        return false;
    }
}
