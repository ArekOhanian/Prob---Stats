package PokemonCardProject;

public class EnergyCard extends Card{
    private String element;

    public EnergyCard(){
        setCardType("Energy");
    }
    //getters & setters
    public String getElement(){
        return element;
    }

    public void setElement(String userElement){
        element = userElement;
    }

}
