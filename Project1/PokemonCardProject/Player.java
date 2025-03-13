package PokemonCardProject;
import java.util.Random;
import java.util.ArrayList;


public class Player{
    private Card[] hand;
    private PokemonCard[] bench;
    private PokemonCard activeCard;
    private Card[] prizeCards;
    private ArrayList<Card> discardPile;
    private Card[] deck;
    private String name;
    private boolean deckIsEmpty;
    Random r = new Random();


    //this is the constructor for the player
    public Player(String name){
        this.name = name;
    }
    //this is the method used to populate the deck array initially with empty cards
    public Card[] populateDeck(){
        deck = new Card[60];
        for (int i = 0; i < deck.length; i++){
            Card card = new Card();
            deck[i] = card;
        }
        return deck;
    }
    //here is a command to populate the hand
    //we use the update deck method to update the deck after copying the first card from the deck into the hand this is for the montycarlo thing for now
    public Card[] populateHand(Card[] userDeck, int cardsDrawn){
        hand = new Card[7];
        int realCardsDrawn = 0;

        for (int i = 0; i < cardsDrawn; i++){
            if (userDeck.length == 0){
                System.out.println("Cannot draw more cards");
                break;
            }
            hand[realCardsDrawn] = userDeck[0];
            userDeck = updateDeck(userDeck);
            realCardsDrawn++;
        }

        if (realCardsDrawn < 7){
            Card[] trimmedHand = new Card[realCardsDrawn];
            System.arraycopy(hand, 0, trimmedHand, 0, realCardsDrawn);
            hand = trimmedHand;
        }

        return hand;
    }
    
    //this method draws a card and makes the hand an array with 1 larger size
    public void drawCard(Card[] userDeck){
        if(userDeck.length == 0){
            deckIsEmpty = true;
            return;
        }
        Card[] temp = hand;
        hand = new Card[temp.length + 1];
        for(int i = 0; i < temp.length; i++){
            hand[i] = temp[i];
        }
        hand[hand.length-1] = userDeck[0];
        deck = updateDeck(deck);
    }
    //here is the method for filling the prize cards for the rare candy checker
    public void populatePrizeCards(){
        prizeCards = new Card[6];
        for(int i = 0; i < prizeCards.length; i++){
            prizeCards[i] = deck[i];
            deck = updateDeck(deck);
        }
    }

    //this is a command to update the deck whenever a card is removed from it
    public Card[] updateDeck(Card[] oldDeck){
        //here we check if the deck is emptyed or not
        if (oldDeck.length == 0 || oldDeck.length <= 1){
            System.out.println("Deck is now empty");
            return new Card[0];
        }

        Card[] updatedDeck = new Card[oldDeck.length -1];
        for(int i = 0; i < updatedDeck.length; i++){
            updatedDeck[i] = oldDeck[i + 1];
        }
        return updatedDeck;
    }

    //this is a general method to call whenever we need to discard a card fomr the hand
    public void discardCardHand(Card[] discardFromHand, int discardPos){
        discardPile.add(discardFromHand[discardPos]);
        cardPlayed(discardFromHand, discardPos);
    }

    //this is a method to discard a card from the deck
    public void discardCardDeck(){
        discardPile.add(deck[0]);
        deck = updateDeck(deck);
    }
    //this is a method to shuffle the deack because of how I implemented everything I need this so that the deck is random
    public Card[] shuffleDeck(Card[] deckToBeShuffled){
        if(deckToBeShuffled == null|| deckToBeShuffled.length ==0){
            System.out.println("Trying to shuffle and empty deck");
            return new Card[0];
        }

        Card[] shuffledDeck = deckToBeShuffled.clone();

        for(int i = shuffledDeck.length - 1; i > 0; i--){
            int j = r.nextInt(i + 1);
            Card temp = shuffledDeck[i];
            shuffledDeck[i] = shuffledDeck[j];
            shuffledDeck[j] = temp;
        }
        return shuffledDeck;
    }

    //this method takes the amount of pokemoncards you want in the deck and the amomount of energy cards you want in the deck and populates it
    //with empty pokemon and energy cards based on the number you put in
    public Card[] cardMulliganChancePopulate(int pokemonCardCount, int energyCardCount){
        deck = new Card[60];
        for (int i = 0; i < pokemonCardCount; i++){
            deck[i] = new PokemonCard(); 
        }
        for(int i = pokemonCardCount; i < 60; i++){
            deck[i] = new EnergyCard("Normal");
        }
        return deck;
    }

    public void cardMulliganChance(){
        
        for (int i = 1; i <= 60; i++){
            
            Card[] tempDeck = cardMulliganChancePopulate(i, 60-i);

            if(tempDeck == null || tempDeck.length != 60){
                System.out.println("Deck is not 60 cards it is: " + tempDeck.length);
                continue;
            }

            tempDeck = shuffleDeck(tempDeck);

            double count = 0.0;
            for (int j = 0; j < 10000; j++){
                
                
                tempDeck = shuffleDeck(tempDeck);
                hand = populateHand(tempDeck, 7);

                if(checkForPokemonInHand(hand)){
                    count++;
                }
            }
            
            //here we calculate
            double percent = ((double)count/(double)10000.0) * 100;
            System.out.println("With " + i + " Pokemon cards and " + (60-i) + " Energy cards");
            System.out.println("The chance of a mulligan is " + (100.0 - percent)  + "%");
        }
   
    }

    public boolean checkForPokemonInHand(Card[] userHand){
        for(Card card : userHand){
            if(card != null && "Pokemon".equals(card.getCardType())){
                return true;
            }
        }
        return false;
    }

    //this is for the check if the prize cards hold the rare candies

    //first we populate the deck with the specific ammount of rare candies then fill the next with the amomount of pokemon cards
    //then the ammount of non specific trainer cards then fill the rest of the deck with energy cards
    public Card[] populateDeckRare(int rareCandyCount){
        Card[] deck = new Card[60];
        int currentDeckSize = 0;
        for (int i = 0; i < rareCandyCount; i++){
            deck[i] = new TrainerCard("Rare Candy", " ");
            currentDeckSize++;
        }
        for(int i = currentDeckSize; i < 60; i++){
            deck[i] = new EnergyCard("Normal");
            currentDeckSize++;
        }
        return deck;
    }

    public void rareCandyPrizeChance(){
        for(int i = 0; i < 4; i++){
            //here we fill the deck with the ammount of rare candies on this run
            Card[] tempDeck = populateDeckRare(i + 1);

            //here we are shuffling the deck
            tempDeck = shuffleDeck(tempDeck);

            //here we are counting the ammount of times all of our rare candies are in the prize cards or "bricking"
            double count = 0.0;
            for (int j = 0; j < 10000; j++){
                
                tempDeck = shuffleDeck(tempDeck);
                hand = populateHand(tempDeck, 7);
                prizeCards = populatePrizeCardsRare(tempDeck);
                if(isBricked(prizeCards, i)){
                    count++;
                }
            }

            //here we calculate the percent
            double percent = ((double) count/ (double) 10000) * 100;
            System.out.println (count);
            System.out.println("With " + (i + 1) + " Rare Candies");
            System.out.println("You have a " + percent + "% chance of bricking");

        }

    }

    //here is the method for filling the prize cards for the rare candy checker
    public Card[] populatePrizeCardsRare(Card[] userDeck){
        prizeCards = new Card[6];
        System.arraycopy(userDeck, 0, prizeCards, 0, 6);
        return updateDeck(userDeck);
    }

    //this is a method to mulliagan your hand if you don't have a pokemon card in hand whe
    public void mulligan(Card[] hand, Card[] deck){
        System.out.println(hand);
        for (int i = 0; i < hand.length; i++){
            Card temp = deck[i];
            deck[i] = hand[i];
            deck[i+1] = temp;
        }
        deck = shuffleDeck(deck);
        hand = populateHand(deck, 7);
    }

    //this is a method for finding the rare candies in the prize cards
    //how it works is we go trough the prize cards and if they are trainer cards then we check if they are rare candies
    //then if the ammount of rare candies equals the ammount of rare candies in the deck which is inputted then it returns true
    //if not it returns false
    public boolean isBricked(Card[] userprizeCards, int importantCardCount){
        int count = 0;
        //System.out.println("Checking prize cards");
        for(Card card: userprizeCards){
            //System.out.println("Card " + card.getCardType());
           if(card.getCardType().equals("Trainer")){

                //System.out.println("Trainer Card: " + trainerCard.getTrainerName());
                if(card.getCardName().equals("Rare Candy")){
                    count++;
                }
           }
        }

        return count == importantCardCount;
    }
    //method for getting prize cards into your hand
    public Card[] drawPrizeCard(){
    
        if(prizeCards.length == 1){
            return hand;
        }
        Card prizeCard = prizeCards[prizeCards.length];
        Card[] tempPrizeArray = prizeCards;
        prizeCards = new Card[tempPrizeArray.length - 1];
        for(int i = 0; i < prizeCards.length; i++){
            prizeCards[i] = tempPrizeArray[i];
        } 
        Card[] oldHand = hand;
        hand = new Card[oldHand.length + 1];
        for (int i =  0; i < hand.length; i++){
            hand[i] = oldHand[i];
        }
        hand[hand.length - 1] = prizeCard;
        return hand;
    }
    
    //method for making a card the active card from the hand
    public void makeActiveHand(Card[] Hand, int cardPos){
        activeCard = (PokemonCard) Hand[cardPos];
    }

    //method for making a card the active card from the bench where we swap the benched and active pokemon
    public void makeActiveBench(Card[] bench, int cardPos){
        Card temp = activeCard;
        activeCard = (PokemonCard) bench[cardPos];
        bench[cardPos] = temp; 
    }

    //method for getting a pokemon card from the hand to the bench
    public void benchPokemonHand(Card[] hand, int cardPos, int benchPos){
        if(bench[benchPos] == null){
            bench[benchPos] = (PokemonCard) hand[cardPos];
            cardPlayed(hand, cardPos);
        }
        else{
            System.out.println("That position is not empty sending back to playing a card");
        }
    }   

    //this is a method for formatting the hand array when a card is removed from it
    //basically I make a temp array of the old hand then I make hand a new array with one size less
    //then i copy it from the old hand but skip over the card you played
    public void cardPlayed(Card[] hand, int cardPlayedpos){
        Card[] oldHand = hand;
        hand = new Card[oldHand.length - 1];
        for(int i = 0; i < hand.length; i++){
            if(i == cardPlayedpos){
                continue;
            }
            if(i < cardPlayedpos){
                hand[i] = oldHand[i];
            }
            if(i > cardPlayedpos){
                hand[i] = oldHand[i + 1];
            }
        }
    }

    //this is a method to atach energies to a pokemon in play
    //where we add to the array list and play the energy card from our hand
    public void attachEnergy(Card[] hand, EnergyCard energyCard, PokemonCard pokemonToAttachTo, int energyCardPos){
        pokemonToAttachTo.getAttachedEnergies().add(energyCard);
        cardPlayed(hand, energyCardPos);
    }

    //I have this method to display the players hand to them in the terminal
    //It would display their position in the list then card type then theor names in a list 
    //ie [0 Pokemon: pikachu, 1 Energy: Electric, 2 Trainer: bill, 3 Energy: Electric]
    public void displayHand(){
        int count = 0;
        for(Card card: hand){
            System.out.print("[" + count + " " + card.getCardType() + ": " + card.getCardName() + ", ");
            count++;
        }
        System.out.print("]");
    }

    //this is a method to display the bench
    public void displayBench(){
        int count = 0;
        for(Card card: bench){
            System.out.print("[" + count + " " + card.getCardName() + ", ");
            count++;
        }
        System.out.print("]");
    }

    //this is a method for displaying the active
    public void displayActive(){
        System.out.println("Name: " + activeCard.getCardName() + ", Health: " + activeCard.getHealth() + ", Attacks: " + activeCard.getPokemonMoves() + ", Attached Energies: " + activeCard.getAttachedEnergies() + ", Retreat Cost: " + activeCard.getRetreatCost());
    }
    
    
    //this method is to check if the bench is empty for win condition reasons where it checks every position in the bench
    //if there is a pokemon then it returns false but if there are none it returns true
    public boolean isBenchEmpty(){
        for(int i = 0; i < bench.length; i++){
            if (bench[i] != null){
                return false;
            }
        }
        return true;
    }

    //this method is for the penny card and it is to retreave a card and put it into your hand
    public void retrieve(Card card){
        if (hand.length == 0){
            hand = new Card[1];
            hand[0] = card;
            return;
        }
        Card[] tempHand = hand;
        hand = new Card[tempHand.length + 1];
        for (int i = 0; i < tempHand.length; i++){
            hand[i] = tempHand[i];
        }
        hand[hand.length - 1] = card;
    }
    //this is a method to feint a pokemon when they have 0 hp
    public void feintPokemon(){
        discardPile.add(activeCard);
        discardPile.addAll(activeCard.getAttachedEnergies());
        activeCard = null;
    }
    //method to get the hand size
    public int getHandSize(){
        return hand.length;
    }
    //getters and setters
    public Card[] getDeck(){
        return deck;
    }

    public void setDeck(Card[] userDeck){
        deck = userDeck;
    }

    public Card[] getHand(){
        return hand;
    }

    public PokemonCard[] getBench(){
        return bench;
    }

    public PokemonCard getActive(){
        return activeCard;
    }

    public void setActive(PokemonCard userActiveCard){
        activeCard = userActiveCard;
    }

    public Card[] getPrizeCards(){
        return prizeCards;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Card> getDiscardPile(){
        return discardPile;
    }

    public void setDiscardPile(ArrayList<Card> userDiscardPile){
        discardPile = userDiscardPile;
    }

    public boolean getDeckIsEmpty(){
        return deckIsEmpty;
    }

    public void setDeckIsEmpty(boolean userdeckIsEmpty){
        deckIsEmpty = userdeckIsEmpty;
    }
}

