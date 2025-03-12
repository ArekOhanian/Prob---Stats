package PokemonStadium;

public class TestPokemon {
    public static void main(String [] args){
        charmander charmanderSteve = new charmander();

        pikachu Pikachu = new pikachu();

        charmanderSteve.getHp();

        Stadium stadium = new Stadium();

        stadium.battle(charmanderSteve, Pikachu);
    }
}
