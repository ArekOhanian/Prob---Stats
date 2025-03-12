package StatsLibrary;
import java.util.ArrayList;
public class SetOperations {

//Union of two sets/array lists
    public ArrayList<String> stringUnion(ArrayList<String> set1, ArrayList<String> set2){
        ArrayList<String> union = new ArrayList<String>();
        for (String element: set1){
            union.add(element);
        }
        for (String element: set2){
            if(!union.contains(element)){
                union.add(element);
            }
        }
        return union;
    }
    //this algorithm creates the intersection of a set/array list
    public ArrayList<String> stringIntersection(ArrayList<String> set1, ArrayList<String> set2){
        ArrayList<String> intersection = new ArrayList<String>();
        for (String element: set1){
            intersection.add(element);
        }
        for (String element: set2){
            if (!set1.contains(element)){
                intersection.remove(element);
            }
        }
        for (String element: set1){
            if (!set2.contains(element)){
                intersection.remove(element);
            }
        }
        return intersection;
    }
//this algorithm creastes the complement of a set/array list
    public ArrayList<String> setCompliment(ArrayList<String> set, ArrayList<String> subSet){
        ArrayList<String> comp = new ArrayList<String>();
        for (String element : set){
            comp.add(element);
        }
        for (String element : subSet){
            comp.remove(element);
        }

        return comp;
    }
    
}
