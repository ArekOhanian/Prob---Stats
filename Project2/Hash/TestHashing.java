package Project2.Hash;
import java.util.LinkedList;

public class TestHashing {
    public static void main(String[] args){
        
        YourNameSimpleHashMap hash = new YourNameSimpleHashMap(null, 0);


    }

    //here is the resize method where we take the old array and copy it to a new one by removing the last indicy
    public LinkedList<String>[] resize(LinkedList<String>[] inputHash){
        LinkedList<String>[] outputHash = new LinkedList<String>[inputHash.length - 1]; 
        for(int i = 0; i < inputHash.length - 1; i++){
            outputHash[i] = inputHash[i];
        }
        return outputHash;
    }
}
