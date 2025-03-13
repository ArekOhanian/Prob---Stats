package PokemonCardProject;

public class EnergyCard extends Card{
    private String element;

    public EnergyCard(String element){
        setCardType("Energy");
        this.element = element;
    }
    //getters & setters
    public String getElement(){
        return element;
    }

    public void setElement(String userElement){
        element = userElement;
    }

}
