package PokemonCardProject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game{
    private Player player1;
    private Player player2;
    private boolean gameOver;
    Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    private boolean energyCardPerTurn;
    private int turn;


    public void runGame(){
        setup();
        while(!gameOver){
            turn++;
            System.out.println("Turn " + turn);
            player1Turn();
            player2Turn();
        }
    }
    
    //this is a method for setting up the game so we need to populate the decks, draw 7 cards mulligan if we need to
    //put a pokemon down into the active slot and on the bench if needed, and put in the prize cards
    public void setup(){
        System.out.println("Starting Game: ");
        gameOver = false;
        player1 = new Player("Player 1");
        player2 = new Player("CPU");
        player1.setDeck(player1Deck());
        player2.setDeck(player2Deck());
        player1.setDeckIsEmpty(false);
        player2.setDeckIsEmpty(false);
        System.out.println("Decks are set: ");
        player1.shuffleDeck(player1.getDeck());
        player2.shuffleDeck(player2.getDeck());
        System.out.println("Decks are shuffled: ");
        System.out.println("Drawing starting hand: ");
        player1.populateHand(player1.getDeck(), 7);
        player2.populateHand(player2.getDeck(), 7);
        //automatic mulligan if the player has no pokemon cards in hand
        // it also makes the opponent draw a card if the player mulligans
        while(!player1.checkForPokemonInHand(player1.getHand())){
            System.out.println("Player 1 has no pokemon in hand: ");
            player1.mulligan(player1.getHand(), player1.getDeck());
            player2.drawCard(player2.getDeck());
        }
        while(!player2.checkForPokemonInHand(player2.getHand())){
            System.out.println("Player 2 has no Pokemon in hand: ");
            player2.mulligan(player2.getHand(), player2.getDeck());
            player1.drawCard(player1.getDeck());
        }
        //here we populate the prize pools with the cards left in the deck taking the top six cards and putting them into an array
        System.out.println("Populating prize pools");
        player1.populatePrizeCards();
        player2.populatePrizeCards();
        //here the first player puts a pokemon card in their active slot they should have at least one 
        //since they were forced to mulligan if they didn't have at least one 
        System.out.println("Player 1 please put a pokemon in the active slot: ");
        System.out.println(player1.getHand());
        //player 1 sets down their active pokemon
        int p1ActiveSelect = getValidInput("Player 1 please put the number position of the card you want to place in the active position");
        boolean p1ActiveSelectionBool = false;
        while(!p1ActiveSelectionBool){
            if(player1.getHand()[p1ActiveSelect].getCardType().equals("Pokemon")){
                player1.makeActiveHand(player1.getHand(), p1ActiveSelect);
                p1ActiveSelectionBool = true;
            }
            else{
                System.out.println("That is not a pokemon try again");
            }
        }
        //player 1 will set any more pokemon into the bench if they want to
        while(player1.checkForPokemonInHand(player1.getHand())){
            int moreBench = getValidInput("Do you want to put a pokemon on the bench?(Type 1 for yes, 0 for no): ");
            if(moreBench == 0){
                break;
            }
            if (moreBench == 1){
                System.out.println("Please put the number position of the card you want to put on the bench: ");
                player1.benchPokemonHand(player1.getHand(), p1ActiveSelect, p1ActiveSelect);
            }
            else{
                System.out.println("Not a valid number");
            }
        }
        //player 2 sets down their active pokemon this is the cpu player so it is all automated
        //it will set down the first pokemon in its hand to the active slot and the rest ot the bench from left to right
        System.out.println("Player 2 is putting down their pokemon: ");
        int count = 0;
        int emptyPos = 0;
        for(Card card: player2.getHand()){
            if(card.getCardType().equals("Pokemon")){
                if(player2.getActive() == null){
                    player2.setActive((PokemonCard) card);
                }
                player2.benchPokemonHand(player2.getHand(), count, emptyPos);
                emptyPos++;
            }
            count++;
            //this is to make sure that it doesn't try to put down a cardin a position that does not exist
            if(emptyPos > 5){
                break;
            }
        }
    }

    //this is the code for the first players turn
    public void player1Turn(){
        energyCardPerTurn = false;
        System.out.println("Player 1 turn start: Drawing a card");
        //this is a check to see if they don't have any cards left 
        if(!player1.getDeckIsEmpty()){
            System.out.println("Player 1's deck is empty and is trying to draw a card");
            gameOver(player2);
        }
        player1.drawCard(player1.getDeck());
        player1.displayHand();
        //here we make sure that at the start of the turn the player has a pokemon in the active slot in case their old one was knocked out
        while(player1.getActive() == null){
            int activeBench = getValidInput("Please pick a pokemon from your bench to put into the active slot (Pick the number corresponding to the card position)");
            if(player1.getBench()[activeBench] != null){
            player1.makeActiveBench(player1.getBench(), activeBench);
            }
            else{
                System.out.println("That is not a valid number try again");
            }
        }
        boolean stillPlacing = true;
        //so here is a loop where it keeps asking the player if they want to play a card and if they want to they select a card in hand
        //then depending on the card it does a different thing
        //the loop will end when the player chooses it to or their hand is empty
        while(stillPlacing){
            int wantsToPlayCard = getValidInput("Player 1 do you want to play a card from your hand? (type 1 for yes 0 for no)");
            if(wantsToPlayCard == 1){
                stillPlacing = true;
                int handPos = getValidInput("What Card do you want to play? (Please put the number corresponding to the position in your hand)");
                Card playCard = player1.getHand()[handPos];
                //this is if they place down an energy card
                if(playCard.getCardType().equals("Energy")){
                    if(!energyCardPerTurn){
                        int energyChose = getValidInput("Do you want to attach to the active pokemon: press 0, or the bench: press 1");
                        if(energyChose == 0){
                            player1.attachEnergy(player1.getHand(), (EnergyCard) playCard, player1.getActive(), handPos);
                            System.out.println("Energy Attached");
                            energyCardPerTurn = true;
                        }
                        if(energyChose == 1){
                            player1.displayBench();
                            int benchPos =  getValidInput("Please select the number you want to attatch the energy to");
                            //implement ways to catch bad inputs
                            if(0 <= benchPos && benchPos < player1.getBench().length && player1.getBench()[benchPos] != null){
                                player1.attachEnergy(player1.getHand(), (EnergyCard) playCard, player1.getBench()[benchPos], handPos);
                                energyCardPerTurn = true;
                            }
                            else{
                                System.out.println("That is not a valid number");
                            }
                            
                        }
                        else{
                            System.out.println("That is not a valid number");
                        }
                    }
                    else{
                        System.out.println("You have already played an energy card this turn");
                    }
                }
                //this is if they want to place down a pokemon card
                if(playCard.getCardType().equals("Pokemon")){

                    player1.displayBench();
                    int benchPos = getValidInput("What position on the bench do you want to put it on? (please put the number position on the bench starting at 0)");
                    player1.benchPokemonHand(player1.getHand(), handPos, benchPos);

                }
                //this is if they want to place down a trainer card
                if(playCard.getCardType().equals("Trainer")){
                    boolean supporterPerTurn = false;
                    //this card discards your current hand and draws 7 cards
                    if(playCard.getCardName().equals("Professors Research") && !supporterPerTurn){
                        for(int i = player1.getHandSize(); i >= 0; i--){
                            player1.discardCardHand(player1.getHand(), 0);
                        }
                        for (int i = 0; i < 7; i++){
                            player1.drawCard(player1.getDeck());
                        }
                        supporterPerTurn = true;
                    }
                    if(playCard.getCardType().equals("Potion")){
                        while(true){
                            int potionPos = getValidInput("Which pokemon do you want to heal (type 0 for active 1 for on on the bench)");
                            //so here we add 30 to the health of the active pokemon and then check if it is more than it's max health
                            //if it is then we set it's health to its original max health
                            if(potionPos == 0){
                                player1.getActive().setHealth(player1.getActive().getHealth() + 30);
                                if(player1.getActive().getHealth() > player1.getActive().getMaxHealth()){
                                    player1.getActive().setHealth(player1.getActive().getMaxHealth());
                                }
                                break;
                            }
                            if(potionPos == 1){
                                int potPosTwo = getValidInput("Which position on the bench do you want to heal?");
                                    if(potPosTwo >= 0 && potPosTwo <= 5 && player1.getBench()[potPosTwo] != null ){
                                        player1.getBench()[potPosTwo].setHealth(player1.getBench()[potPosTwo].getHealth() + 30);
                                        if(player1.getBench()[potPosTwo].getHealth() > player1.getBench()[potPosTwo].getMaxHealth()){
                                            player1.getBench()[potPosTwo].setHealth(player1.getBench()[potPosTwo].getMaxHealth());
                                        }
                                    break;
                                    }
                                    else{
                                        System.out.println("That is not a valid input");
                                    }
                            }
                            else{
                                System.out.println("That is not a valid number please try again");
                            }
                        }
                    }
                    //here is for the trainer card bill which just draws 2 cards
                    if(playCard.getCardName().equals("Bill") && !supporterPerTurn){
                        player1.drawCard(player1.getDeck());
                        player1.drawCard(player1.getDeck());
                        supporterPerTurn = true;
                    }
                    //this is for the lillie card where you draw up to 6 cards unless it is turn 1 then you draw up to 8
                    if(playCard.getCardName().equals("Lillie") && !supporterPerTurn){
                        if (turn == 1){
                            for(int i = player1.getHand().length; i < 8; i++){
                                player1.drawCard(player1.getDeck());
                            }
                            supporterPerTurn = true;
                            }
                            else{
                                for(int i = player1.getHand().length; i < 6; i++){
                                    player1.drawCard(player1.getDeck());
                                }
                            }
                        }
                            //this is for the superpotion card
                    if(playCard.getCardName().equals("Super Potion")){
                        while(true){
                            int soupPotPos = getValidInput("Which pokemon do you want to heal (type 0 for active 1 for on on the bench)");
                            //so here we add 60 to the health of the active pokemon then check if it has any energies attached to it and if they do discard it
                            //so the energy is added to the discard pile and then removed from the array list of attached energies
                            //then we check if it has gone over it's max health
                            //if it is then we set it's health to its original max health
                            if(soupPotPos == 0){
                                player1.getActive().setHealth(player1.getActive().getHealth() + 60);
                                while (true){
                                    if(!player1.getActive().getAttachedEnergies().isEmpty()){
                                        int energyDis = getValidInput("Which energy do you want to get rid of please type the number");
                                        if(energyDis <= player1.getActive().getAttachedEnergies().size()){
                                            player1.getDiscardPile().add(player1.getActive().getAttachedEnergies().get(energyDis));
                                            player1.getActive().getAttachedEnergies().remove(energyDis); 
                                            break;
                                        }
                                        else{
                                            System.out.println("not a valid index try again");
                                        }
                                    }
                                }
                                if(player1.getActive().getHealth() > player1.getActive().getMaxHealth()){
                                    player1.getActive().setHealth(player1.getActive().getMaxHealth());
                                }
                                break;
                            }   
                            if(soupPotPos == 1){
                                int soupPotPosTwo = getValidInput("Which position on the bench do you want to heal?");
                                    if(soupPotPosTwo >= 0 && soupPotPosTwo <= 5 && player1.getBench()[soupPotPosTwo] != null ){
                                        player1.getBench()[soupPotPosTwo].setHealth(player1.getBench()[soupPotPosTwo].getHealth() + 60);
                                        if(player1.getBench()[soupPotPosTwo].getHealth() > player1.getBench()[soupPotPosTwo].getMaxHealth()){
                                            player1.getBench()[soupPotPosTwo].setHealth(player1.getBench()[soupPotPosTwo].getMaxHealth());
                                        }
                                    break;
                                    }
                                    else{
                                        System.out.println("That is not a valid input");
                                    }
                            }
                            else{
                                System.out.println("That is not a valid number please try again");
                            }
                        }
                    }
                }
            }
        
        //so here it check if the player wants to continue playing cards or if they have no cards and ends the main phase if either condition are met
            if(wantsToPlayCard == 0 || player1.getHandSize() == 0){
                stillPlacing = false;
            }
            //this catches any numbers that are not 0 or 1
            else{
                System.out.println("That is not a valid input try again");
            }
        }
        int attackSelection = getValidInput("Player 1 do you want to attack? (type 1 for yes 0 for no)");
        boolean hasAttacked = false;
        while(!hasAttacked){
            if(attackSelection == 1){
                System.out.println(player1.getActive().getPokemonMoves());
                int moveSelection = getValidInput("What Move do you want to use? (type the number coresponding to the move 0 for the first 1 for the second)");
                if(moveSelection == 0 || moveSelection == 1){
                    int totalCount = 0;
                    int elementCount = 0;
                    for(EnergyCard energy: player1.getActive().getAttachedEnergies()){
                        if(energy.getElement().equals(player1.getActive().getPokemonMoves()[moveSelection].getPrimEnergyCostType())){
                            elementCount++;
                        }
                        totalCount++;
                    }
                    if(totalCount >= player1.getActive().getPokemonMoves()[moveSelection].getTotalEnergyCost() && elementCount >= player1.getActive().getPokemonMoves()[moveSelection].getPrimEnergyCostAmmount()){
                        attack(player1.getActive().getPokemonMoves()[moveSelection], player2.getActive(), player1, player2);
                        hasAttacked = true;
                    }
                    else{
                        System.out.println("You do not have the attached energies to perform this move");
                    }
                }
                else{
                    System.out.println("That is not a valid selectio please select another or do not attack");
                }
            }
        }
        //end step
        while(player1.getHandSize() > 7){
            player1.displayHand();
            int cardDiscarPos = getValidInput("Player 1 you have more than 7 cards in hand please select a card to discard: ");
            player1.discardCardHand(player1.getHand(), cardDiscarPos);
        }
        
    }

    //this is the code for the second Players turn
    //this turn will be automated
    public void player2Turn(){
        System.out.println("Player 2 turn start:  Drawing a card");
        player2.drawCard(player2.getDeck());
        if(player2.checkForPokemonInHand(player2.getHand())){

        }
    }

    //this is the attack method
    public void attack(Move attackingMove, PokemonCard toBeAttackedPokemon, Player attackingPlayer, Player damagedPlayer){
        //this is for the deino's move stomp off where it does no damage but discards the top card from the deck
        if (attackingMove.getName().equals("Stomp Off")){
            damagedPlayer.discardCardDeck();
        }
        //this is to check if the pokemon is weak to the specific move element
        if(attackingMove.getPrimEnergyCostType().equals(toBeAttackedPokemon.getWeakness())){
            toBeAttackedPokemon.setHealth(toBeAttackedPokemon.getHealth() - (attackingMove.getDamage() * 2));
            System.out.println("It was supper effective!");
        }
        //of not we just subtract the health from the attacking move 
        else{
            toBeAttackedPokemon.setHealth(toBeAttackedPokemon.getHealth() - attackingMove.getDamage());
        }
        System.out.println("You have done " + attackingMove.getDamage() + " Damage. The opponents active has " + toBeAttackedPokemon.getHealth() + " Health left");
        //here is where we check if the pokemon has feinted if they have then we draw a card from the bench
        if(toBeAttackedPokemon.getHealth() <= 0){
            //here we make sure that the bench is not empty cause if it is then the player who got attacked losses
            damagedPlayer.feintPokemon();
            if(damagedPlayer.isBenchEmpty()){
                System.out.println(damagedPlayer.getName() + " has no pokemon in play");
                gameOver(attackingPlayer);
            }
            System.out.println(attackingPlayer.getName() + " Draws a prize Card");
            attackingPlayer.drawPrizeCard();
            // here we check if the attacking players prize pool is empty cause if it is then they win
            if(attackingPlayer.getPrizeCards().length == 0){
                System.out.println(attackingPlayer.getName() + " Has collected all prize cards");
                gameOver(attackingPlayer);
            }
            
        }
    }
    
    //this method here is what i will run any input throught to make sure that every input is valid so it doesn't crash
    //basically is loops until a number is put in
    public int getValidInput(String Message){
        System.out.println(Message);
        while(!sc.hasNextInt()){
            System.out.println("Not a valid Input, please input a number.");
            sc.next();
        }
        return sc.nextInt();
    }
    //This is a method for ending the game
    public void gameOver(Player winningPlayer){
        System.out.println(winningPlayer.getName() + " Wins");
        gameOver = true;
    }  

    //This is a method that populates a deck and returns it so that player 1 has this deck set 
    public Card[] player1Deck(){
        Card[] player1Deck = new Card[60];
        ArrayList<EnergyCard> attachedEnergies = new ArrayList<>();
        int cardPlacement = 0;
        Move[] zorMoves = new Move[2];
        zorMoves[0] = new Move("Ram", 10, 0, "Dark", 1);
        zorMoves[1] = new Move("Rear Kick", 20, 1, "Dark", 2);
        Move[] deiMoves = new Move[2];
        deiMoves[0] = new Move("Stomp Off", 0, 1, "Dark", 1);
        deiMoves[1] = new Move("Bite", 20, 1, "Dark", 2);
        Move[] woopMoves = new Move[1];
        woopMoves[0] = new Move("Flop",30, 1, "Dark", 2);
        Move[] pawnMoves = new Move[1];
        pawnMoves[0] = new Move("Pierce", 10, 1, "Dark", 1);
        for(int i = 0; i < 4; i++){
            player1Deck[i] = new PokemonCard(60, "Zorua", zorMoves, 1, attachedEnergies, "Grass");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "Deino", deiMoves, 1, attachedEnergies, "Grass");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "paldean Wooper", woopMoves, 2, attachedEnergies, "Fighting");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "Pawniard", pawnMoves, 2, attachedEnergies, "Grass");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Professors Reasearch", "Discard Your hand and draw 7 cards.");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Potion", "Heal 30 damage from 1 of your pokemon");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Bill", "Draw 2 cards");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Lillie", "Draw cards untill you have 6 cards in hand unless it is the first turn then draw until you have 8");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Super Potion", "Heal 60 damage from one of your pokemon. If you do discard an energy attached from that pokemon if there is one");
            cardPlacement++;
        }
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Penny", "Put one of your basic pokemon and all of it's energies into your hand");
            cardPlacement++;
        }
        for (int i = cardPlacement; i < 60; i++){
            player1Deck[i] = new EnergyCard("Dark");
        }
        return player1Deck;
    }

    //this is the method to make and return the cpu deck
    private Card[] player2Deck(){
        return null;
    }
}
