package PokemonCardProject;

public class Move {
    private int damage;
    private int primEnergyCostAmmount;
    private String primEnergyCostType;
    private int totalEnergyCostAmmount;
    private String name;

    //constructor for a move on a card
    public Move(String name, int damage, int primEnergyCostAmmount, String primEnergyCostType, int totalEnergyCostAmmount){
        this.damage = damage;
        this.primEnergyCostAmmount = primEnergyCostAmmount;
        this.primEnergyCostType = primEnergyCostType;
        this.totalEnergyCostAmmount = totalEnergyCostAmmount;
        this.name = name;
    }


    //getters and setter for the variables in this class
    public void setDamage(int userDamage){
        damage = userDamage;
    }

    public int getDamage(){
        return damage;
    }

    public void setPrimEnergyCostAmmount(int userEnergyCostAmmount){
        primEnergyCostAmmount = userEnergyCostAmmount;
    }

    public int getPrimEnergyCostAmmount(){
        return primEnergyCostAmmount;
    }

    public void setPrimEnergyCostType(String userEnergyCostType){
        primEnergyCostType = userEnergyCostType;
    }

    public String getPrimEnergyCostType(){
        return primEnergyCostType;
    }

    public int getTotalEnergyCost(){
        return totalEnergyCostAmmount;
    }

    public void setTotalEnergyCost(int userTotalEnergy){
        totalEnergyCostAmmount = userTotalEnergy;
    }

    public void setName(String userName){
        name = userName;
    }

    public String getName(){
        return name;
    }
}
