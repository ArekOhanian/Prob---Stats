package Project2.Hash;
import java.util.LinkedList;

public class YourNameSimpleHashMap {
    LinkedList<String>[] data;

    //here is the constructor for the simple hash map
    public YourNameSimpleHashMap(int size){
        this.data = new LinkedList[size];
        for(int i = 0; i < size; i++){
            this.data[i] = new LinkedList<>();
        }
    }


    //Count the number of letters in the string to make it the hash
    public int dumbHash(String a){
        int pos = a.length();
        return pos;
    }

    //To check if the hash contains a certain value
    public boolean contains(String specific){
        int pos = dumbHash(specific);
        return data[pos].contains(specific);
    }


    //put function
    public void put(String userString){
        int pos = dumbHash(userString);
        while(pos >= data.length){
            this.data = resize(data);
        }
        if(!data[pos].contains(userString)){
            data[pos].add(userString);
        }
    }


    //get function where we input a string and it searches the map and returns the position if it is not in the hash it should return null
    public Integer get(String key){
        int pos = dumbHash(key);
        if(contains(key)){
            return pos;
        }
        return null;
    }


    //getters and setters
    public LinkedList<String>[] getData(){
        return data;
    }
    
    //here is the resize method where we take the old array and copy it to a new one and add one
    public LinkedList<String>[] resize(LinkedList<String>[] inputHash){
        LinkedList<String>[] outputHash = new LinkedList[inputHash.length + 1]; 
        for(int i = 0; i < inputHash.length; i++){
            outputHash[i] = inputHash[i];
        }

        outputHash[outputHash.length - 1] = new LinkedList<>();
        return outputHash;
    }
}
