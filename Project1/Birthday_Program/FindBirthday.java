package Birthday_Program;
import java.util.ArrayList;
import java.util.Random;

public class FindBirthday{

    private ArrayList<Person> classBirthdayList = new ArrayList<>();
    private double shareProbability;
    Random rand = new Random();

    //here is a method to populate the array list with a random birthdays with the class size being wha the use inputted
    public ArrayList<Person> birthdayFiller(int classSize){
        classBirthdayList.clear();
        for (int i = 0; i < classSize; i++){
            int birthday = rand.nextInt(365) + 1;
            Person person = new Person(birthday);
            classBirthdayList.add(person);
        }
        return classBirthdayList;
    }

    //This method checks the array list for any matching numbers then returns the amount of times a birhtday is shared
    public boolean birthdayFinder(int classSize){
        birthdayFiller(classSize);

            for (int i = 0; i < classBirthdayList.size(); i++) {
                for (int j = i + 1; j < classBirthdayList.size(); j++) {
                    if(classBirthdayList.get(i).getBirthday() == classBirthdayList.get(j).getBirthday()){
                        return true;
                    }
                }   
            }
        return false;
    }

    public Double birthdayProbability(int classSize, int runTimes){
        int count = 0;
        for (int i = 0; i < runTimes; i++){
            if(birthdayFinder(classSize)){
                count++;
            }
        }
        shareProbability = (double) count/runTimes;
        return shareProbability * 100;
    }
}
