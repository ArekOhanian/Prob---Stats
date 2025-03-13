package PokemonCardProject;
import java.util.ArrayList;
public class PokemonCard extends Card{
    private int health;

    private Move[] pokemonMoves;
    private ArrayList<EnergyCard> attachedEnergies;
    private int retreatCost;
    private String weakness;
    private int maxHealth;

    PokemonCard(){
        setCardType("Pokemon");
        
    }

    PokemonCard(int health, String name, Move[] pokemonMoves, int retreatCost , ArrayList<EnergyCard> attachedEnergies, String weakness){
        this.health = health;
        maxHealth = health;
        setCardName(name);
        this.pokemonMoves = pokemonMoves;
        this.retreatCost = retreatCost;
        this.attachedEnergies = attachedEnergies;
        this.weakness = weakness;
        setCardType("Pokemon");
    }

    //getters & setters
    public int getHealth(){
        return health;
    }

    public void setHealth(int userHealth){
        health = userHealth;
    }

    public Move[] getPokemonMoves(){
        return pokemonMoves;
    }
    
    public void setPokemonMoves(Move[] userPokemonMoves){
        pokemonMoves = userPokemonMoves;
    }

    public int getRetreatCost(){
        return retreatCost;
    }

    public ArrayList<EnergyCard> getAttachedEnergies(){
        return attachedEnergies;
    }

    public void setAttachedEnergies(ArrayList<EnergyCard> userAttachedEnergies){
        attachedEnergies = userAttachedEnergies;
    }

    public String getWeakness(){
        return weakness;
    }

    public int getMaxHealth(){
        return maxHealth;
    }
}
