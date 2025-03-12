package PokemonCardProject;

public class TrainerCard extends Card{

    private String trainerDesc;
    public TrainerCard(){
        setCardType("Trainer");
    }

    public TrainerCard(String userTrainerName, String userTrainerDesc){
        setCardType("Trainer");
        setCardName(userTrainerName);
        trainerDesc = userTrainerDesc;
    }

    //getters and setters

    public String getTrainerDesc(){
        return trainerDesc;
    }

    public void setTrainerDesc(String userTrainerDesc){
        trainerDesc = userTrainerDesc;
    }
}
