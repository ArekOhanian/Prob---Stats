package PokemonStadium;

public class pokemon {
    //golbal variables
    private int  hp;
    private int atk;
    private int def;
    private int spd;

    //getters for the base stats
    public int getHp(){
        return hp;
    }

    public int getAtk(){
        return atk;
    }

    public int getDef(){
        return def;
    }

    public int getSpd(){
        return spd;
    }

    //setters for the base stats
    public void setHp(int userHp){
        hp = userHp;
    }

    public void setAtk(int userAtk){
        atk = userAtk;
    }

    public void setDef(int userDef){
        def = userDef;
    }

    public void setSpd(int userSpd){
        spd = userSpd;
    }
}
