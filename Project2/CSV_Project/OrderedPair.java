package Project2.CSV_Project;

public class OrderedPair{
    //global variables
    private int x;
    private double y;

    //constructor for the ordered pair
    public OrderedPair(int x, double y){
        this.x = x;
        this.y = y;
    }

    //getters and setters
    public int getX(){
        return x;
    }

    public void setX(int userX){
        x = userX;
    }

    public double getY(){
        return y;
    }

    public void setY(int userY){
        y = userY;
    }
}