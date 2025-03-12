package StatsLibrary;
import java.util.ArrayList;
public class SetProTest {
    public static void main(String[] args){
    ArrayList<String> weekday = new ArrayList<String>();
    ArrayList<String> weekend = new ArrayList<String>();
    SetOperations op = new SetOperations(weekday, weekend);
    weekday.add("Monday");
    weekday.add("Tuesday");
    weekday.add("Wednesday");
    weekday.add("Thursday");
    weekend.add("Friday");
    weekend.add("Saturday");
    weekend.add("Sunday");
    ArrayList<String> week = new ArrayList<>();
    week.addAll(op.stringUnion(weekend, weekday));
    System.out.println(week);
    System.out.println(op.stringUnion(weekday, weekend));
    System.out.println(op.stringIntersection(weekday, weekend));
    System.out.println(op.setCompliment(week, weekend));
    }
}
