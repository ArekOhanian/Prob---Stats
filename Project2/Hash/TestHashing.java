package Project2.Hash;
import java.util.LinkedList;

public class TestHashing {
    public static void main(String[] args){
        

        //creating strings to insert into the linkedLists
        String a1 = "a";
        String a2 = "aa";
        String b1 = "bbb";
        String b4 = "bbbb";

        //initializing andf filling the linked lists to be inserted into the hashMap
        LinkedList<String> a = new LinkedList<>();
        LinkedList<String> b = new LinkedList<>();
        a.add(a1);
        a.add(a2);
        
        b.add(b1);
        b.add(b4);

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
