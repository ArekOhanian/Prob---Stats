package PokemonCardProject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game{
    private Player player1;
    private Player player2;
    private boolean gameOver;
    private boolean supporterPerTurn;
    private boolean energyCardPerTurn;
    Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    private int turn;


    public void runGame(){
        setup();
        while(!gameOver){
            turn++;
            System.out.println("Turn " + turn);
            playerTurn(player1, player2, false);
            playerTurn(player2, player1, true);
            
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

        player1.setBench();
        player2.setBench();

        player1.setDiscardPile();
        player2.setDiscardPile();

        player1.setDeckIsEmpty(false);
        player2.setDeckIsEmpty(false);
        System.out.println("Decks are set: ");

        player1.setDeck(player1.shuffleDeck(player1.getDeck()));
        player2.setDeck(player2.shuffleDeck(player2.getDeck()));
        System.out.println("Decks are shuffled: ");

        System.out.println("Drawing starting hand: ");
        player1.populateHand(player1.getDeck(), 7);
        player2.populateHand(player2.getDeck(), 7);
        //automatic mulligan if the player has no pokemon cards in hand
        // it also makes the opponent draw a card if the player mulligans
        while(!player1.checkForPokemonInHand(player1.getHand())){
            System.out.println("Player 1 has no pokemon in hand: Mulliganing");
            player1.mulligan(player1.getHand(), player1.getDeck());
            player2.drawCard(player2.getDeck());
        }
        while(!player2.checkForPokemonInHand(player2.getHand())){
            System.out.println("Player 2 has no Pokemon in hand: Mulligaing");
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
        player1.displayHand();
        //player 1 sets down their active pokemon
        boolean p1ActiveSelectionBool = false;
        while(!p1ActiveSelectionBool){
        int p1ActiveSelect = getValidInput("Player 1 please put the number position of the card you want to place in the active position");
        if(p1ActiveSelect >= 0 && p1ActiveSelect < player1.getHandSize()){
            if(player1.getHand()[p1ActiveSelect].getCardType().equals("Pokemon")){
                player1.makeActiveHand(player1.getHand(), p1ActiveSelect);
                player1.setHand(player1.cardPlayed(player1.getHand(), p1ActiveSelect));
                p1ActiveSelectionBool = true;
            }
            else{
                System.out.println("That is not a pokemon try again");
            }
        }
        else{
            System.out.println("That is not a valid index");
        }
    }
        //player 1 will set any more pokemon into the bench if they want to
        while(player1.checkForPokemonInHand(player1.getHand())){
            int moreBench = getValidInput("Do you want to put a pokemon on the bench?(Type 1 for yes, 0 for no): ");
            if(moreBench == 0){
                break;
            }
            if (moreBench == 1){
                player1.displayHand();
                int cardHandSelection = getValidInput("Which card in hand do you want to put onto the bench? (type the card index in your hand)");
                if(cardHandSelection < 0 && cardHandSelection >= player1.getHandSize()){
                    System.out.println("That is not a valid index");
                }
                int cardBenchSelection = pickBench(player1);
                if(player1.getBench()[cardBenchSelection] == null && player1.getHand()[cardHandSelection].getCardType().equals("Pokemon")){
                    player1.benchPokemonHand(player1.getHand(), cardHandSelection, cardBenchSelection);
                    player1.setHand(player1.cardPlayed(player1.getHand(), cardHandSelection));
                }
                else{
                    System.out.println("That bench position is taken or that is not a pokemon card");
                }
            }
            else{
                System.out.println("Not a valid number");
            }
        }
        //player 2 sets down their active pokemon this is the cpu player so it is all automated
        //it will set down the first pokemon in its hand to the active slot and the rest ot the bench from left to right
        System.out.println("Player 2 is putting down their pokemon: ");
        int emptyPos = 0;
        for(int i = 0; i < player2.getHandSize(); i++){
            Card card = player2.getHand()[i];

            if(card.getCardType().equals("Pokemon")){
                if(player2.getActive() == null){
                    System.out.println("Active is set");
                    player2.makeActiveHand(player2.getHand(), i);
                    player2.setHand(player2.cardPlayed(player2.getHand(), i));
                }
                else{
                    player2.benchPokemonHand(player2.getHand(), i, emptyPos);
                    player2.setHand(player2.cardPlayed(player2.getHand(), i));
                }
                emptyPos++;
            }
            //this is to make sure that it doesn't try to put down a cardin a position that does not exist
            if(emptyPos > 5){
                break;
            }
        }
    }

    //this is the code for the first players turn
    public void playerTurn(Player player, Player opponent, boolean isCpu){
        energyCardPerTurn = false;
        supporterPerTurn = false;
        drawPhase(player, opponent, isCpu);
        mainPhase(player, isCpu);
        attackPhase(player, opponent, isCpu);
        endStep(player, isCpu);
    }       

    //this is the method for thr draw phase
    public void drawPhase(Player player, Player opponent, boolean isCpu){
        System.out.println(player.getName() +"'s turn start: Drawing a card");
        //this is a check to see if they don't have any cards left 
        if(player.getDeckIsEmpty()){
            System.out.println(player.getName() +"'s deck is empty and is trying to draw a card");
            gameOver(opponent);
            return;
        }
        player.drawCard(player.getDeck());
        player.displayHand();
        //here we make sure that at the start of the turn the player has a pokemon in the active slot in case their old one was knocked out
        while(player.getActive() == null){
            if(isCpu){
                player.makeActiveBench(player.getBench(), 0);
            }
            else{
            benchToActive(player);
            }
        }

    }

    //this is the method for the mainphase like all we see if it is the player or the cpu
    public void mainPhase(Player player, boolean isCpu){
        boolean stillPlacing = true;
        
        //this is the automated main phase where we look through the hand and play as many cards as possible from left to right
        if(isCpu){
            if(player.getHandSize() == 0){
                return;
            }
            Card playCard = player.getHand()[0];
            if(playCard.getCardType().equals("Energy")){
                attachEnergy(player, isCpu, 0);
            }
            if(playCard.getCardType().equals("Pokemon")){
                pokemonPlayed(player, 0, isCpu);
            }
            if(playCard.getCardType().equals("Trainer")){
                trainerPlayed(player, isCpu, playCard, 0);
            }
        }
        else{
        //so here is a loop where it keeps asking the player if they want to play a card and if they want to they select a card in hand
        //then depending on the card it does a different thing
        //the loop will end when the player chooses it to or their hand is empty
        while(stillPlacing){
            int mainPhase = getValidInput("Player 1 do you want to play a card from your hand, skip your turn, or retreat your active slot? (type 0 to skip your main phase, type 1 to play a card, type 2 to retreat your active card)");
            if(mainPhase == 1){
                player.displayHand();
                stillPlacing = true;
                if(player.getHandSize() == 0){
                    System.out.println("Your hand is empty and you cannot play a card");
                    continue;
                }
                int handPos = getValidInput("What Card do you want to play? (Please put the number corresponding to the position in your hand)");
                if(handPos >= 0 && handPos < player.getHandSize()){
                Card playCard = player.getHand()[handPos];

                //this is if they place down an energy card
                if(playCard.getCardType().equals("Energy")){
                    if(energyCardPerTurn == false){
                        attachEnergy(player, isCpu, handPos);
                    }
                    else{
                        System.out.println("You have already placed an energy card this turn");
                    }
                }

                //this is if they want to place down a pokemon card
                if(playCard.getCardType().equals("Pokemon")){
                    pokemonPlayed(player, handPos, isCpu);
                }

                //this is if they want to place down a trainer card
                if(playCard.getCardType().equals("Trainer")){
                    trainerPlayed(player, isCpu, playCard, handPos);
                }
            }    
            }

            //so here it check if the player wants to continue playing cards or if they have no cards and ends the main phase if either condition are met
            if(mainPhase == 0){
                stillPlacing = false;
            }

            //this is the code to retreat your active mon
            if(mainPhase == 2){
                retreat(player);
            }
        }
        }
    }

    //this is the code for the attacking step
    public void attackPhase(Player player, Player opponent, boolean isCpu){
        //here the cpu attacks if it can if not it does nothing
        if(isCpu && turn != 1){
            int totalCount = 0;
            int primCount = 0;
            for(EnergyCard energy: player.getActive().getAttachedEnergies()){
                if(energy.getElement().equals(player.getActive().getPokemonMoves()[0].getPrimEnergyCostType())){
                    primCount++;
                }
                totalCount++;
            }
            if(totalCount >= player.getActive().getPokemonMoves()[0].getTotalEnergyCost() && primCount >= player.getActive().getPokemonMoves()[0].getPrimEnergyCostAmmount()){
                attack(player.getActive().getPokemonMoves()[0], opponent.getActive(), player, opponent);
            }
        }
        //for the real player you select the move then if you have the required energy you attack if not you are asked if you want to attack again
        //you can only exit if you say you do not want to attack or if you attack
        else{
            boolean hasAttacked = false;
            if(turn == 1){
                System.out.println("You cannot attack on the first turn movng to next step");
                return;
            }
            while(!hasAttacked){
                int attackSelection = getValidInput("Player 1 do you want to attack? (type 1 for yes 0 for no)");
                if(attackSelection == 1){
                    player.displayMoves();
                    int moveSelection = getValidInput("What Move do you want to use? (type the number coresponding to the move 0 for the first 1 for the second if there is one)");
                    if(moveSelection == 0 || moveSelection == 1){
                        int totalCount = 0;
                        int elementCount = 0;
                        for(EnergyCard energy: player.getActive().getAttachedEnergies()){
                            if(energy.getElement().equals(player.getActive().getPokemonMoves()[moveSelection].getPrimEnergyCostType())){
                                elementCount++;
                            }
                            totalCount++;
                        }
                        if(totalCount >= player.getActive().getPokemonMoves()[moveSelection].getTotalEnergyCost() && elementCount >= player.getActive().getPokemonMoves()[moveSelection].getPrimEnergyCostAmmount()){
                            attack(player.getActive().getPokemonMoves()[moveSelection], opponent.getActive(), player, opponent);
                            hasAttacked = true;
                        }
                        else{
                            System.out.println("You do not have the attached energies to perform this move");
                        }
                    }
                    else{
                        System.out.println("That is not a valid selection please select another or do not attack");
                    }
                }
                if(attackSelection == 0){
                    return;
                }
            }
        }
    }

    //this is the method for when the turn ends
    public void endStep(Player player, boolean isCpu){
        if(isCpu){
            while(player.getHandSize() > 7){
                System.out.println("Hand is larger then 7 discarding a card");
                player.discardCardHand(player.getHand(), 0);
                player.setHand(player.cardPlayed(player.getHand(), 0));
            }
        }
        else{
            while(player.getHandSize() > 7){
                player.displayHand();
                int disPos = getValidInput("Your Hand is larger than 7 please select a card to discard");
                if(disPos >= 0 && disPos < player.getHandSize() && player.getHand()[disPos] != null){
                    player.discardCardHand(player.getHand(), disPos);
                    player.setHand(player.cardPlayed(player.getHand(), disPos));
                }
                else{
                    System.out.println("That is not a valid index try again");
                }
            }
        }
    }

    //this is the methods for the main phase

    //here is for attaching an energy
    public void attachEnergy(Player player, boolean isCpu, int handPos){
        if(isCpu){
            player.attachEnergy(player.getHand(), (EnergyCard) player.getHand()[handPos], player.getActive());
            player.setHand(player.cardPlayed(player.getHand(), handPos));
            System.out.println("Attached Energy to: " + player.getActive().getCardName());

        }
        else{
            if(energyCardPerTurn){
                System.out.println("You have already played an energy this turn");
                return;
            }
            int energyDec = getValidInput("Do you want to attach it to your active or your bench 0 for active 1 for bench");
            //attaches the energy card from your hand to the active pokemon
            if(energyDec == 0){
                player.attachEnergy(player.getHand(), (EnergyCard) player.getHand()[handPos], player.getActive());
                player.setHand(player.cardPlayed(player.getHand(), handPos));
                System.out.println("Attached Energy to: " + player.getActive().getCardName());
                energyCardPerTurn = true;
            }
            //now it checks if your bench is empty
            if(energyDec == 1){
                if(player.isBenchEmpty()){
                    System.out.println("Your bench is empty");
                }
                //if it isn't it askes for what position to attach it to
                else{
                    while(true){
                    int benchPos = pickBench(player);
                    if(player.getBench()[benchPos] != null){
                        player.attachEnergy(player.getHand(), (EnergyCard) player.getHand()[handPos], player.getBench()[benchPos]);
                        player.setHand(player.cardPlayed(player.getHand(), handPos));
                        System.out.println("Attached energy to: " + player.getBench()[benchPos].getCardName());
                        energyCardPerTurn = true;
                        break;
                    }
                    else{
                        System.out.println("That index has no pokemon try again");
                    }
                }
                }
            }
        }
    }

    //this method is for handliing when a pokemon is played
    public void pokemonPlayed(Player player, int handPos, boolean isCpu){
        if(isCpu){
            for(int i = 0; i < player.getBench().length; i++){
                if(player.getBench()[i] == null){
                    player.benchPokemonHand(player.getHand(), 0, i);
                    player.setHand(player.cardPlayed(player.getHand(), 0));
                }
            }
            return;
        }
        int benchPos = pickBench(player);
        if(player.getBench()[benchPos] == null){
        player.benchPokemonHand(player.getHand(), handPos, benchPos);
        player.setHand(player.cardPlayed(player.getHand(), handPos));
        System.out.println("Placed " + player.getBench()[benchPos].getCardName() + " on bench position " + benchPos);
        }
        else{
            System.out.println("That index is taken by another pokemon");
        }
    }

    //this is for when a trainer card is played this is mostly a buffer to see what trainer card it is
    public void trainerPlayed(Player player, boolean isCpu, Card playCard, int handPos){
        //this is if you play professors research
        if(playCard.getCardName().equals("Professors Research")){
            profReach(player);
            supporterPerTurn = true;
        }

        //here is if you play a potion
        if(playCard.getCardName().equals("Potion")){
            potion(player, isCpu, handPos);
        }

        //If the bill card is played
        if(playCard.getCardName().equals("Bill")){
            bill(player, handPos);
        }
        //here is if you play lillie
        if(playCard.getCardName().equals("Lillie")){
            lillie(player, handPos);
        }

        //here id if a super potion is played
        if(playCard.getCardName().equals("Super Potion")){
            soup(player, isCpu, handPos);
        }

        //here is if penny is played
        if(playCard.getCardName().equals("Penny")){
            penny(player, isCpu, handPos);
        }

        
    }

    //these are the methods that do what the trainer cards do

    //professors research
    public void profReach(Player player){
        if(supporterPerTurn == true){
            System.out.println("You have already played a supporter this turn");
        }
        else{
            for(int i = player.getHandSize(); i > 0; i--){
                player.discardCardHand(player.getHand(), 0);
                player.setHand(player.cardPlayed(player.getHand(), 0));
            }
            System.out.println("Discarded Hand");
            for(int i = 0; i < 7; i++){
                player.drawCard(player.getDeck());
            }
            System.out.println("Drew 7 cards");
            supporterPerTurn = true;
            player.displayHand();
        }
    }

    //Potion
    public void potion(Player player, boolean isCpu, int handPos){
        //the cpu will automatically use it on their active slot
        if(isCpu){
            player.getActive().setHealth(player.getActive().getHealth() + 30);
            if(player.getActive().getHealth() > player.getActive().getMaxHealth()){
                player.getActive().setHealth(player.getActive().getMaxHealth());
            }
            player.setHand(player.cardPlayed(player.getHand(), handPos));
            System.out.println("Healed " + player.getActive().getCardName() + " to " + player.getActive().getHealth());
        }
        //if not player input is needed
        else{
            while(true){
                int potionPos = getValidInput("Which pokemon do you want to heal (type 0 for active 1 for on on the bench)");
                //so here we add 30 to the health of the active pokemon and then check if it is more than it's max health
                //if it is then we set it's health to its original max health
                if(potionPos == 0){
                    player.getActive().setHealth(player.getActive().getHealth() + 30);
                    if(player.getActive().getHealth() > player.getActive().getMaxHealth()){
                        player.getActive().setHealth(player.getActive().getMaxHealth());
                    }
                    player.setHand(player.cardPlayed(player.getHand(), handPos));
                    System.out.println("Healed " + player.getActive().getCardName() + " to " + player.getActive().getHealth());
                    break;
                }
                if(potionPos == 1){
                    //so first we have the user pick a pokemon on the bench
                    int potPosTwo = pickBench(player);
                    if(player.getBench()[potPosTwo] != null){
                        player.getBench()[potPosTwo].setHealth(player.getBench()[potPosTwo].getHealth() + 30);
                        if(player.getBench()[potPosTwo].getHealth() > player.getBench()[potPosTwo].getMaxHealth()){
                            player.getBench()[potPosTwo].setHealth(player.getBench()[potPosTwo].getMaxHealth());
                        }
                        player.setHand(player.cardPlayed(player.getHand(), handPos));
                        System.out.println("Healed " + player.getBench()[potPosTwo].getCardName() + " to " + player.getBench()[potPosTwo].getHealth());
                        break;
                    }
                    else{
                            System.out.println("That position does not have a pokemon");
                        }
                    }
                else{
                    System.out.println("That is not a valid number please try again");
                }
            }
        }
    }

    //bill
    public void bill(Player player, int handPos){
        //we just draw two cards
        if(supporterPerTurn){
            System.out.println("You have already played a supporter per turn");
        }
        else{
            player.setHand(player.cardPlayed(player.getHand(), handPos));
            player.drawCard(player.getDeck());
            player.drawCard(player.getDeck());
            System.out.println("Drew 2 cards");
            player.displayHand();
            supporterPerTurn = true;
        }
    }
    
    //lillie
    public void lillie(Player player, int handPos){
        if(supporterPerTurn){
            System.out.println("You have already played a supporter per turn");
        }
        else{
            player.setHand(player.cardPlayed(player.getHand(), handPos));
            //this is for the lillie card where you draw up to 6 cards unless it is turn 1 then you draw up to 8
            if (turn == 1){
                for(int i = player.getHand().length; i < 8; i++){
                    player.drawCard(player.getDeck());
                }
                System.out.println("Drew to 8 cards");
                player.displayHand();
                supporterPerTurn = true;
                }
            else{
                for(int i = player.getHand().length; i < 6; i++){
                    player.drawCard(player.getDeck());
                }
                System.out.println("Drew to 6 cards");
                player.displayHand();
                supporterPerTurn = true;
            }
        }
    }
    //Super Potion
    public void soup(Player player, boolean isCpu, int handPos){
        if(isCpu){
            player.getActive().setHealth(player.getActive().getHealth() + 60);
            if(!player.getActive().getAttachedEnergies().isEmpty()){
                player.getDiscardPile().add(player.getActive().getAttachedEnergies().get(0));
                player.getActive().getAttachedEnergies().remove(0);
            }
            System.out.println("Healed " + player.getActive().getCardName() + " to " + player.getActive().getHealth());
            player.setHand(player.cardPlayed(player.getHand(), handPos));
        }
        else{
            while(true){
                int soupPotPos = getValidInput("Which pokemon do you want to heal (type 0 for active 1 for on on the bench)");
                //so here we add 60 to the health of the active pokemon then check if it has any energies attached to it and if they do discard it
                //so the energy is added to the discard pile and then removed from the array list of attached energies
                //then we check if it has gone over it's max health
                //if it is then we set it's health to its original max health
                if(soupPotPos == 0){
                    player.getActive().setHealth(player.getActive().getHealth() + 60);
                    player.setHand(player.cardPlayed(player.getHand(), handPos));
                    while (true){
                    if(!player.getActive().getAttachedEnergies().isEmpty()){
                        int energyDis = getValidInput("Which energy do you want to get rid of please type the index");
                        if(energyDis <= player.getActive().getAttachedEnergies().size()){
                            player.getDiscardPile().add(player.getActive().getAttachedEnergies().get(energyDis));
                            player.getActive().getAttachedEnergies().remove(energyDis);   
                            break;
                        }
                        else{
                            System.out.println("not a valid index try again");
                        }
                    }
                    //if you are over the max health you go to the max health
                    if(player.getActive().getHealth() > player.getActive().getMaxHealth()){
                        player.getActive().setHealth(player.getActive().getMaxHealth());
                    }
                    System.out.println("Healed " + player.getActive().getCardName() + " to " + player.getActive().getHealth());
                }
                break;
                }   
                if(soupPotPos == 1){
                    int soupPotPosTwo = pickBench(player);
                    if(player.getBench()[soupPotPosTwo] != null ){
                        player.getBench()[soupPotPosTwo].setHealth(player.getBench()[soupPotPosTwo].getHealth() + 60);
                        player.setHand(player.cardPlayed(player.getHand(), handPos));
                        if(player.getBench()[soupPotPosTwo].getHealth() > player.getBench()[soupPotPosTwo].getMaxHealth()){
                            player.getBench()[soupPotPosTwo].setHealth(player.getBench()[soupPotPosTwo].getMaxHealth());
                        }
                    player.cardPlayed(player.getHand(), soupPotPosTwo);
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

    //penny
    public void penny(Player player, boolean isCpu, int handPos){
    
    //this is when the penny card is played where we make the player select their pokemon to retrive and then give back the eneriges
    //attached to them as well as heal them to max health
    //we also check to make sure that it wouldn't empty their deck
        if(player.isBenchEmpty() || supporterPerTurn){
            System.out.println("You you cannot play this card");
        }
        else{
            if(isCpu){
                player.setHand(player.cardPlayed(player.getHand(), handPos));
                player.getActive().setHealth(player.getActive().getMaxHealth());
                player.retrieve(player.getActive());
                for(int i = 0; i < player.getActive().getAttachedEnergies().size(); i++){
                    player.retrieve(player.getActive().getAttachedEnergies().get(i));
                }
                player.setActive(player.getBench()[0]);
                System.out.println("Retrieved " + player.getActive().getCardName());
                supporterPerTurn = true;
            }
            else{
                int retreaveCard = getValidInput("Do you want to retreave your active or benched pokemon? 0 for active 1 for benched");
                if(retreaveCard == 0){
                //so here we make the pokemon get their max health since they are going back to their hand
                player.setHand(player.cardPlayed(player.getHand(), handPos));
                player.getActive().setHealth(player.getActive().getMaxHealth());
                player.setHand(player.retrieve(player.getActive()));
                for(int i = 0; i < player.getActive().getAttachedEnergies().size(); i++){
                    player.setHand(player.retrieve(player.getActive().getAttachedEnergies().get(i)));
                }
                System.out.println("Retrieved " + player.getActive().getCardName());
                supporterPerTurn = true;
                System.out.println("Need a new active pokemon");
                //calling a benched pokemon to the active slot
                benchToActive(player);
                }
                if(retreaveCard == 1){
                while(true){
                    int benchPos = pickBench(player);
                    if(player.getBench()[benchPos] != null){
                        player.cardPlayed(player.getHand(), handPos);
                        player.getBench()[benchPos].setHealth(player.getBench()[benchPos].getMaxHealth());
                        //retrieving the selected pokemon from the bench to my hand
                        player.setHand(player.retrieve(player.getBench()[benchPos]));
                        //retreaving the attached energies to my hand
                        for(int i = 0; i < player.getBench()[benchPos].getAttachedEnergies().size(); i++){
                            player.setHand(player.retrieve(player.getBench()[benchPos].getAttachedEnergies().get(i)));
                        }
                        System.out.println("Retrieved " + player.getBench()[benchPos].getCardName());
                        supporterPerTurn = true;
                        break;
                    }
                    else{
                        System.out.println("That is not a valid index");
                    }
                }
                }
            }
        }
    }
    
    //this is the retreat meathod
    public void retreat(Player player){
        //if the bench is empty you cannot retreat so this is the check to make sure you have pokemon on the bench
        if(player.isBenchEmpty()){
            System.out.println("Your bench is empty and you cannot retreat");
            return;
        }
        //then we make sure you have enough energies to retreat
        if(player.getActive().getAttachedEnergies().size() >= player.getActive().getRetreatCost()){
            System.out.println("Reatreating: " + player.getName() + "'s active pokemon");

            while(true){

                //we call pick bench to make the player choose which position on the bench to swap with
                int retreatSwap = pickBench(player);

                //this is a count to keep track of the ammount of energies you actually discarded
                int discardedEnergies = 0;
                if(player.getBench()[retreatSwap] != null){

                    //this loop runs until you discard the active pokemons retreat cost
                    while(discardedEnergies < player.getActive().getRetreatCost()){
                        player.displayEnergies();
                        int disEnPos = getValidInput(" Please select one to discard input the index of the energy card");
                        if(disEnPos >= 0 && disEnPos < player.getActive().getAttachedEnergies().size()){
                            player.getActive().getAttachedEnergies().remove(disEnPos);
                            discardedEnergies++;
                        }
                        else{
                            System.out.println("That is not a valid input try again");
                        }
                    }
                    player.benchActive(retreatSwap);
                    break;
                }
            }

        }
        else{
            System.out.println("You do not have the required ammount of energies to retreat your active pokemon");
            return;
        }
    }
    
    //this is the attack method that is called durring the attack phase
    public void attack(Move attackingMove, PokemonCard toBeAttackedPokemon, Player attackingPlayer, Player damagedPlayer){
        System.out.println(attackingPlayer.getActive().getCardName() + " Uses " + attackingMove.getName() + " against " + toBeAttackedPokemon.getCardName());
        //this is for the deino's move stomp off where it does no damage but discards the top card from the deck
        if (attackingMove.getName().equals("Stomp Off")){
            damagedPlayer.discardCardDeck();
            System.out.println("You have discarded the top card off of the opponents deck");
        }
        //this is to check if the pokemon is weak to the specific move element
        if(attackingMove.getPrimEnergyCostType().equals(toBeAttackedPokemon.getWeakness())){
            toBeAttackedPokemon.setHealth(toBeAttackedPokemon.getHealth() - (attackingMove.getDamage() * 2));
            System.out.println("It did " + (attackingMove.getDamage() * 2) + " Damage");
            System.out.println("It was supper effective!");
        }
        //if not we just subtract the health from the attacking move 
        else{
            toBeAttackedPokemon.setHealth(toBeAttackedPokemon.getHealth() - attackingMove.getDamage());
            System.out.println("It did " +  attackingMove.getDamage() + " Damage");
        }

        System.out.println("The opponent's active pokemon has " + toBeAttackedPokemon.getHealth() + " Health left");
        //here is where we check if the pokemon has feinted if they have then we draw a card from the bench
        if(toBeAttackedPokemon.getHealth() <= 0){
            //here we make sure that the bench is not empty cause if it is then the player who got attacked losses
            damagedPlayer.feintPokemon(toBeAttackedPokemon);
            if(damagedPlayer.isBenchEmpty()){
                System.out.println(damagedPlayer.getName() + " has no pokemon in play");
                gameOver(attackingPlayer);
                return;
            }
            System.out.println(attackingPlayer.getName() + " Draws a prize Card");
            attackingPlayer.setHand(attackingPlayer.drawPrizeCard());
            // here we check if the attacking players prize pool is empty cause if it is then they win
            if(attackingPlayer.getPrizeCards().length == 0){
                System.out.println(attackingPlayer.getName() + " Has collected all prize cards");
                gameOver(attackingPlayer);
                return;
            }
        }
    }

    //this method here is what i will run any input throught to make sure that every input is at number so it doesn't crash
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

    //this is a method to take a pokemon from the bench and put them into the active slot
    public void benchToActive(Player player){
        while(true){
            int benchToActive = pickBench(player);
            if(player1.getBench()[benchToActive] != null){
                player.makeActiveBench(player.getBench(), benchToActive);
                break;
            }
            else{
            System.out.println("That is not a valid index try again");
            }
        }
    }

    //this is a method for slecting a pokemon on the bench
    public int pickBench(Player player){
        player.displayBench();
        int benchSelection = getValidInput(" Please select the pokemon on the bench you want");
        while(true){
            if(benchSelection >= 0 && benchSelection < player.getBench().length){
                return benchSelection;
            }
            else{
                System.out.println("Not a valid bench position");
            }
        }
    }

    //This is a method that populates a deck and returns it so that player 1 has this deck set 
    public Card[] player1Deck(){
        Card[] player1Deck = new Card[60];
        ArrayList<EnergyCard> attachedEnergies = new ArrayList<>();
        int cardPlacement = 0;
        //moves for zorua
        Move[] zorMoves = new Move[2];
        zorMoves[0] = new Move("Ram", 10, 0, "Dark", 1);
        zorMoves[1] = new Move("Rear Kick", 20, 1, "Dark", 2);
        //moves for deino
        Move[] deiMoves = new Move[2];
        deiMoves[0] = new Move("Stomp Off", 0, 1, "Dark", 1);
        deiMoves[1] = new Move("Bite", 20, 1, "Dark", 2);
        //moves for wooper
        Move[] woopMoves = new Move[1];
        woopMoves[0] = new Move("Flop",30, 1, "Dark", 2);
        //moves for pawniard
        Move[] pawnMoves = new Move[1];
        pawnMoves[0] = new Move("Pierce", 10, 1, "Dark", 1);
        //adding 4 zoruas 
        for(int i = 0; i < 4; i++){
            player1Deck[i] = new PokemonCard(60, "Zorua", zorMoves, 1, attachedEnergies, "Grass");
            cardPlacement++;
        }
        //adding 4 deino's 
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "Deino", deiMoves, 1, attachedEnergies, "Grass");
            cardPlacement++;
        }
        //adding 4 paldean woopers
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "paldean Wooper", woopMoves, 2, attachedEnergies, "Fighting");
            cardPlacement++;
        }
        //here we are aading 4 pawnards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new PokemonCard(70, "Pawniard", pawnMoves, 2, attachedEnergies, "Grass");
            cardPlacement++;
        }
        //here we are adding 4 professors research cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Professors Research", "Discard Your hand and draw 7 cards.");
            cardPlacement++;
        }
        //here we are adding 4 potion cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Potion", "Heal 30 damage from 1 of your pokemon");
            cardPlacement++;
        }
        //here we are adding 4 bill cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Bill", "Draw 2 cards");
            cardPlacement++;
        }
        //here we are adding 4 Lillie cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Lillie", "Draw cards untill you have 6 cards in hand unless it is the first turn then draw until you have 8");
            cardPlacement++;
        }
        //here we are adding 4 super potion cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Super Potion", "Heal 60 damage from one of your pokemon. If you do discard an energy attached from that pokemon if there is one");
            cardPlacement++;
        }
        //here we are adding 4 penny cards
        for (int i = 0; i < 4; i++){
            player1Deck[cardPlacement] = new TrainerCard("Penny", "Put one of your basic pokemon and all of it's energies into your hand");
            cardPlacement++;
        }
        for (int i = cardPlacement; i < 60; i++){
            player1Deck[i] = new EnergyCard("Dark");
        }
        return player1Deck;
    }

    //this is the method to make and return the cpu deck im makinng it weak to dark cause it's my game and I want to win (also to test out weaknesses)
    //very similar to making player 1's deck
    private Card[] player2Deck(){
        Card[] player2Deck = new Card[60];
        ArrayList<EnergyCard> attachedEnergies = new ArrayList<>();
        int cardPlacement = 0;

        //pokemon moves
        //sandygast moves
        Move[] sandMove = new Move[1];
        sandMove[0] = new Move("Sand Spray", 50, 3, "Normal", 3);

        //yamask moves
        Move[] maskMove = new Move[2];
        maskMove[0] = new Move("Mumble", 10, 1, "Psychic", 1);
        maskMove[1] = new Move("Petty Gudge", 20, 1, "Psychic", 2);

        //flittle moves
        Move[] fliMove = new Move[1];
        fliMove[0] = new Move("peck", 10, 1, "Psychic", 1);

        //ralts moves
        Move[] raltMove = new Move[1];
        raltMove[0] = new Move("Psyshot", 30, 1, "Psychic", 2);

        //4 sandygasts
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new PokemonCard(90, "Sandygast", sandMove, 3, attachedEnergies, "Dark");
            cardPlacement++;
        }

        //4 yamasks
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new PokemonCard(70, "Yamask", maskMove, 2, attachedEnergies, "Dark");
            cardPlacement++;
        }

        //4 ralts
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new PokemonCard(70, "Ralts", raltMove, 1, attachedEnergies, "Dark");
            cardPlacement++;
        }

        //4 flittles
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new PokemonCard(50, "Flittle", sandMove, 1, attachedEnergies, "Dark");
            cardPlacement++;
        }
        //4 professors research cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Professors Research", "Discard Your hand and draw 7 cards.");
            cardPlacement++;
        }

        //here we are adding 4 potion cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Potion", "Heal 30 damage from 1 of your pokemon");
            cardPlacement++;
        }

        //here we are adding 4 bill cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Bill", "Draw 2 cards");
            cardPlacement++;
        }

        //here we are adding 4 Lillie cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Lillie", "Draw cards untill you have 6 cards in hand unless it is the first turn then draw until you have 8");
            cardPlacement++;
        }

        //here we are adding 4 super potion cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Super Potion", "Heal 60 damage from one of your pokemon. If you do discard an energy attached from that pokemon if there is one");
            cardPlacement++;
        }

        //here we are adding 4 penny cards
        for (int i = 0; i < 4; i++){
            player2Deck[cardPlacement] = new TrainerCard("Penny", "Put one of your basic pokemon and all of it's energies into your hand");
            cardPlacement++;
        }

        //fill the rest with energy cards
        for (int i = cardPlacement; i < 60; i++){
            player2Deck[cardPlacement] = new EnergyCard("Psychic");
            cardPlacement++;
        }
        return player2Deck;
    }
}
