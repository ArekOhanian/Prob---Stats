package Monty_Hall;
public class Door {
    //Global variables door alignment is weither the door is good or bad because of that i made it a boolean so it's true or false
    //door position is the position the door is on out of the three
    private boolean doorAlignment;
    private int doorPosition;

    //default constructor
    public Door(){
        setDoorAlignment(false);
        setDoorPosition(1);
    }
    //user inputeded constructor
    public Door(boolean doorAlignment, int doorPosition){
        setDoorAlignment(doorAlignment);
        setDoorPosition(doorPosition);
    }
    //getters and setters
    public boolean getDoorAlignment(){
        return doorAlignment;
    }

    public int getDoorPosition(){
        return doorPosition;
    }

    public void setDoorAlignment(boolean userDoorAlignment){
        doorAlignment = userDoorAlignment;
    }

    public void setDoorPosition(int userDoorPosition){
        doorPosition = userDoorPosition;
    }
}
