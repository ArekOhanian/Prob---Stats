package StatsLibrary;

import java.math.BigInteger;

public class TestStatsLibrary{
    public static void main(String[] args){
        StatsLibrary tester = new StatsLibrary();
        int[] numbers = {1, 2, 3, 4, 5, 6};
        double mean = tester.findMean(numbers);
        double median = tester.findMedian(numbers);
        int mode = tester.findMode(numbers);
        double stanDev = tester.findStandDev(numbers);
        double biDis = tester.biDis(.8, 10, 7);
        double geoDis = tester.geoDis(.5, 5);
        double hyperGeo = tester.hyperGeoDis(20, 10, 5, 5);
        double negBiDis = tester.negBiDis(5, 3, 0.2);
        BigInteger combination = tester.combination(4, 2);
        BigInteger permutation = tester.permutation(4, 2);
        System.out.println("The mean of the numbers is: " + mean);
        System.out.println("The median of the numbers is: " + median);
        System.out.println("The mode of the numbers is: " + mode);
        System.out.println("The standard deviation of the numbers is: " + stanDev);
        System.out.println("The combination is: " + combination);
        System.out.println("The permutation is: " + permutation);
        System.out.println(biDis);
        System.out.println(geoDis);
        System.out.println(hyperGeo);
        System.out.println(negBiDis);
    }
}
