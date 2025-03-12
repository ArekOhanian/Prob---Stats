package Monty_Hall;

public class MontyTester {
    //just a simple main method to call the method from the game class and print it to the terminal
    public static void main (String[] args){
        Game game = new Game();
        System.out.println(game.playGame());
    }
}
