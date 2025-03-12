package Birthday_Program;


public class Person{
    private int birthday;

    Person(int birthday){
        this.birthday = birthday;
    }

    Person(){

    }
    
    public int getBirthday(){
        return birthday;
    }

    public void setBirthday(int userInputBirthday){
        birthday = userInputBirthday;
    }
}

