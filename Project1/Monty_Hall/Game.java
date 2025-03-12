package Monty_Hall;
import java.util.Random;
//Question answers A. The reasonable probability of getting a goat is 2/3 and getting not the goat is 1/3 if chosen at random
//B.
// 1. 1/3 to win the good prize if you don't switch
// 2. the result would be the dud if she swapped of the good one
// 3. if she had a dud and swpped she would get the good prize
// 4. The probability of winning by switching is about 2/3
// 5. If you want to win switch every time
public class Game extends Door{
    private Door[] doors;
    Random random = new Random();
    public Door[] populateDoors(){
        doors = new Door[3];
        //here the code populates the array with the "goat doors" so that they array is full
        for (int i = 0; i < 3; i++){
            doors[i] = new Door(false, i); 
        }

        //then here we make a random number to make the good door and replaces the old bad one
        int goodDoorPos = random.nextInt(3);
        doors[goodDoorPos] = new Door(true, goodDoorPos);

        //then we return the array of doors
        return doors;
    }

    //this is the method for playing the game and always staying since we dont switch here its like a simplified version of the switching class
    public boolean playGameStay(){
        //so here we have the program choose a door at random from the array and we also say what door they chose
        int doorChosenPos = random.nextInt(3);
        Door doorChosen = doors[doorChosenPos];

        //here we are having the program choose the door to reveal
        int revealDoorPos;
        //here is a while loop that will run untill it chooses the door that is not the chosen door or the door that is true we technically dont need this to be fair
        while (true){
            revealDoorPos = random.nextInt(3);
            if (!doors[revealDoorPos].getDoorAlignment()  && revealDoorPos != doorChosenPos){
                break;
            }
        }
        return doorChosen.getDoorAlignment();
    }

    //this is for playing the game and always switching
    public boolean playGameSwitch(){
        //here we have the program choose it's initial door like the method above
        int doorChosenPos = random.nextInt(3);
        Door doorChosen = doors[doorChosenPos];

        //here is the program choosing the door to reveal much like the method above
        int revealDoorPos;

        //an infinite loop to randomly choose a door to reveal that isn't the one chosen or the good door
        while (true){
            revealDoorPos = random.nextInt(3);
            if(!doors[revealDoorPos].getDoorAlignment() && revealDoorPos != doorChosenPos){
                break;
            }
        }
        
        //for switching to the last remainging door we just run down the list and choose the one not already chosen previously or the revealed door
        for(int i = 0; i < 3; i++) {
            if (i != doorChosenPos && i != revealDoorPos){
                doorChosen = doors[i];
                break;
            }
        }

        return doorChosen.getDoorAlignment();
    }
    
    //here is the method to play the game and is what will by called by the main method
    public String playGame(){
        int stayWins = 0;
        int switchWins = 0;
        //this is where we run the problem ten thousand times keeping track of how many times each of them wins
        for (int i = 0; i < 10000; i++){
            populateDoors();
            if(playGameStay()){
                stayWins++;
            }
            if(playGameSwitch()){
                switchWins++;
            }
        }
        //here is where we get the average fo the wins
        double stayPercentDec = (double)stayWins/10000;
        double switchPercentDec = (double)switchWins/10000;

        String switchPercent = switchPercentDec * 100 + "%";
        String stayPercent = stayPercentDec * 100 + "%";
        String playGame = "Staying Gave a: " + stayPercent + " Sucess rate" + "\n" + "Switching Gave a: " + switchPercent + " Sucess rate";
        return playGame;
    }
}
