package Project2.CSV_Project.Part1;

public class Function {
    OrderedPair result;

    //this is to get the final result 
    public OrderedPair findResult(int x){
        //this is finding the result given the given x value 
        int y = function(x);
        result = new OrderedPair(x, y);
        return result;
    }

    //this is the method to unput the number into to find the result
    private int function(int x){
        //intialize the local variable
        int y;
        //setting th varible to be equal to the function 3x^2 + 7x + 3 so it should be a parabola
        y = 3 * ((int) Math.pow(x, 2)) + (7 * x) + 3;
        //returning out findings
        return y;
    }
}
