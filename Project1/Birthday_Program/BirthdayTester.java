package Birthday_Program;
import java.util.Scanner;
public class BirthdayTester {
    public static void main (String[] args){
        FindBirthday find = new FindBirthday();
        Scanner input = new Scanner(System.in);

        System.out.print("Input the Amount of Students: ");
        int classSize = input.nextInt();

        System.out.print("Input the ammount of times the program is run: ");
        int runTimes = input.nextInt();

        System.out.println("The Probability of two people sharing the same birthday is: " + find.birthdayProbability(classSize, runTimes) + "%");


        input.close();
    }
}
