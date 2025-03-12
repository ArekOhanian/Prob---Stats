package StatsLibrary;
import java.math.BigInteger;
import java.math.BigDecimal;

public class StatsLibrary{
    //this is a simple method to find the mean of an array of numbers
    public double findMean(int[] userInputNumbers){
        double total = 0;
            for (int singleNumber: userInputNumbers){
            total += singleNumber;
        }
        double mean = total / userInputNumbers.length;
        return mean;
    }
    //this finds the median of an array of numbers
    public double findMedian(int[] userInputNumbers){
        sortArray(userInputNumbers);
        int length = userInputNumbers.length;
        double median;
        if (length % 2 == 0){
            median = ((double)userInputNumbers[length /2] + (double)userInputNumbers[length /2 - 1])  / 2;
        }
        else{
            median = userInputNumbers[length / 2];
        }
        return median;
    }

    //this algorithm finds the mode of an array of numbers
    public int findMode(int[] userInputNumbers){
        sortArray(userInputNumbers);
        int length = userInputNumbers.length;
        int mode = userInputNumbers[0];
        int count = 1;
        int max = 0;
        for (int i = 0; i < length - 1; i++){
            if (userInputNumbers[i] == userInputNumbers[i + 1] ){
                count++;
            }
            else{
                if (count > max){
                    max = count;
                    count = 1;
                    mode = userInputNumbers[i];
                }
            }
        }
        return mode;
    }
    //this is an algorithm for find standard deviation of an array of numbers
    public double findStandDev(int[] userInputNumbers){
        double mean = findMean(userInputNumbers);
        int length = userInputNumbers.length;
        double total = 0;
        for (int singleNumber: userInputNumbers){
            total += Math.pow(singleNumber - mean, 2);
        }
        double variance = total / length;
        double stanDev = Math.sqrt(variance);
        return stanDev;
    }
    // Bubble sort algorithm for the mode and median, and just for the future
    public void sortArray(int[] userInputNumbers){
        int length = userInputNumbers.length;
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length -1; j++){
                if (userInputNumbers[j] > userInputNumbers[j + 1]){
                    int temp = userInputNumbers[j];
                    userInputNumbers[j] = userInputNumbers[j + 1];
                    userInputNumbers[j + 1] = temp;
                }
            }
        }
    }

    //this is for combination
    public BigInteger combination(int n, int k){
        BigInteger nFact = BigInteger.ONE;
        BigInteger kFact = BigInteger.ONE;
        BigInteger nMinkFact = BigInteger.ONE;
        if (n < k){
            return BigInteger.ZERO;
        }
        //this is to find n!
        for (int i = 2; i <= n; i++){
            nFact = nFact.multiply(BigInteger.valueOf(i));
        }

        //This is to find k!
        for(int i = 2; i <= k; i++){
            kFact = kFact.multiply(BigInteger.valueOf(i));
        }

        //this is to find (n-k)!
        for(int i = 2; i <= (n-k); i++){
            nMinkFact = nMinkFact.multiply((BigInteger.valueOf(i)));
        }
        return nFact.divide(kFact.multiply(nMinkFact));
        
    }

    //this is for permutation
    public BigInteger permutation(int n, int k){
        BigInteger nFact = BigInteger.ONE;
        BigInteger nMinkFact = BigInteger.ONE;

        //finding n! like the combination method same with (n-k)!
        for (int i = 2; i <= n; i++){
            nFact = nFact.multiply(BigInteger.valueOf(i));
        }

        for (int i = 2; i <= (n - k); i++){
            nMinkFact = nMinkFact.multiply(BigInteger.valueOf(i));
        }

        return nFact.divide(nMinkFact);

    }

    //this is for Binomial Distributions
    public double biDis (double p, int n, int y){
        //this is for n choose y
        BigInteger combo = combination(n, y);
        //this is to find q or the fail rate
        double q = 1 - p;
        double pToY = Math.pow(p,y);
        double qTonMinY = Math.pow(q, (n -y));

        BigDecimal comboDec = new BigDecimal(combo);
        BigDecimal prob = comboDec.multiply(BigDecimal.valueOf(pToY * qTonMinY));
        

        return prob.doubleValue();
    }

    //This is for geometric distribution


    //this is for hypergeometric distribution

    //this is for negative binomial distribution
    
}