package PokemonCardProject;

//here is a Class that defines a card
public class Card {
    private String cardName;
    private String cardType;
    //private String 
    Card(String cardType, String cardName){
        this.cardType = cardType;
        this.cardName = cardName;
    }

    Card(){
        
    }

    public void setCardType(String userCardType){
        cardType = userCardType;
    }

    public String getCardType(){
        return cardType;
    }

    public void setCardName(String userCardName){
        cardName = userCardName;
    }

    public String getCardName(){
        return cardName;
    }
}
