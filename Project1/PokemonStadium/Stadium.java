package PokemonStadium;

public class Stadium {
  public void battle(pokemon p1, pokemon p2){
    //normally check speed first
    if(p1.getSpd() >= p2.getSpd()){
    //put this into a loop until one is knocked out
    int turns = 1;
    while (turns != 0){
      //p1 attacks p2
      int p1Damage = p1.getAtk() - p2.getDef();
      int p2RemainingHealth = p1Damage - p2.getHp();
      if(p2RemainingHealth <= 0){
        System.out.println("p1 wins");
        break;
      }
      //p2 attack p1
      int p2Damage = p2.getAtk() - p1.getDef();
      int p1RemainingHealth = p2Damage - p1.getHp();
      if(p1RemainingHealth <= 0){
        System.out.println("p2 wins");
        break;
      }
    }
  }
  if(p2.getSpd() > p1.getSpd()){
    //put this into a loop until one is knocked out
    int turns = 1;
    while (turns != 0){
      //p1 attacks p2
      int p2Damage = p1.getAtk() - p1.getDef();
      int p1RemainingHealth = p2Damage - p1.getHp();
      if(p1RemainingHealth <= 0){
        System.out.println("p2 wins");
        break;
      }
      //p2 attack p1
      int p1Damage = p1.getAtk() - p2.getDef();
      int p2RemainingHealth = p1Damage - p2.getHp();
      if(p2RemainingHealth <= 0){
        System.out.println("p1 wins");
        break;
      }
    }
  }
  
  }
}
  

