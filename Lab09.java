/*************************************************************************
  *  File: Lab09.java
  *  Names: Kevin Miao & Robert Churchill
  *  Author: Wayne Snyder
  *  Date: 10/17/2013
  *  Class: CS 237
  *  Purpose: This is the starter code for Lab09 
  * 
  *************************************************************************/
import java.util.*;
import java.io.*;

public class Lab09 {
   
   
   // return phi(x) = standard Gaussian pdf
   public static double phi(double x) {
      return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
   }
   
   // return phi(x, mu, sigma) = Gaussian pdf with mean mu and stddev sigma
   public static double phi(double x, double mu, double sigma) {
      return phi((x - mu) / sigma) / sigma;
   }
   
   // return Phi(z) = standard Gaussian cdf using Taylor approximation
   // this gives the probability that a random variable distributed according to the
   // standard normal distribution (mean = 0 and stdev = 1) produces a value less than z
   public static double Phi(double z) {
      if (z < -8.0) return 0.0;
      if (z >  8.0) return 1.0;
      double sum = 0.0, term = z;
      for (int i = 3; sum + term != sum; i += 2) {
         sum  = sum + term;
         term = term * z * z / i;
      }
      return 0.5 + sum * phi(z);
   }
   
   // return Phi(z, mu, sigma) = Gaussian cdf with mean mu and stddev sigma
   // This gives the probability that a random variable X distributed normally with
   // mean mu and stdev sigma produces a value less than z
   public static double Phi(double z, double mu, double sigma) {
      return Phi((z - mu) / sigma);
   } 
   
   // Compute z for standard normal such that Phi(z) = y via bisection search
   public static double PhiInverse(double y) {
      return PhiInverseHelper(y, .00000001, -8, 8);
   } 
   
   private static double PhiInverseHelper(double y, double delta, double lo, double hi) {
      double mid = lo + (hi - lo) / 2;
      if (hi - lo < delta) return mid;
      if (Phi(mid) > y) return PhiInverseHelper(y, delta, lo, mid);
      else              return PhiInverseHelper(y, delta, mid, hi);
   }
   
   // Same as previous for arbitrary normal random variables
   public static double PhiInverse(double y, double mu, double sigma) {
      return PhiInverseHelper2(y, mu, sigma, .00000001, (mu - 8*sigma), (mu + 8*sigma));
   } 
   
   private static double PhiInverseHelper2(double y, double mu, double sigma, double delta, double lo, double hi) {
      double mid = lo + (hi - lo) / 2;
      if (hi - lo < delta) return mid;
      if (Phi(mid,mu,sigma) > y) return PhiInverseHelper2(y, mu, sigma, delta, lo, mid);
      else              return PhiInverseHelper2(y, mu, sigma, delta, mid, hi);
   }
   
   
   public static void main(String[] args) {
      
      // The following is a list of the number of Olympic medals won since 2000,
      // indexed by age, i.e., medalsWon[k] = medals won by individuals of age k.
      // Reference: http://www.tableausoftware.com/public/community/sample-data-sets
      
      int[] medalsWon = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 
         80, 104, 155, 252, 382, 520, 720, 757, 807, 763, 765, 724, 
         701, 572, 486, 377, 345, 231, 193, 133, 116, 56, 55, 43, 34, 
         21, 22, 18, 16, 14, 17, 6, 1, 4, 2, 3, 2, 0, 1, 1, 1, 0, 0, 0, 0, 1 };
      
      
      System.out.println("\nProblem One: Let X = age of someone who won a medal; give");
      System.out.println("\t\t mean and standard deviation."); 
      
      double sum = 0.0;
      int sum2 = 0;
      
      for (int i = 0; i < medalsWon.length; i++) {
          sum += medalsWon[i] * i;
          sum2 += medalsWon[i];
      }
      
      double mean = sum/sum2;
      
      double stdev = 0.0;
      
      for (int i = 0; i < medalsWon.length; i++) {
          stdev += Math.abs(i - mean) * medalsWon[i];
      }
      
      stdev = stdev/sum2;
      
      System.out.println();
      System.out.println("Mean: " + mean);
      System.out.println("Standard Deviation: " + stdev);
      
      System.out.println("\nProblem Two: What percentage within one, two, & three stdevs of mean?");  
      
      double low = mean - stdev;
      double high = mean + stdev;
      int count = 0;
      
      for (int i = 0; i < medalsWon.length; i++) {
          if (low < i && high > i)
              count += medalsWon[i];
      }
      
      System.out.println("One Standard Deviation (Predicted): 68% (" + 0.68 * sum2 + ")");
      System.out.println("One Standard Deviation (Actual): " + count);
      
      low = mean - 2 *  stdev;
      high = mean + 2 *  stdev;
      count = 0;
      
      for (int i = 0; i < medalsWon.length; i++) {
          if (low < i && high > i)
              count += medalsWon[i];
      }
      
      System.out.println("Two Standard Deviation (Predicted): 95% (" + 0.95 * sum2 + ")");
      System.out.println("Two Standard Deviation (Actual): " + count);
      
      low = mean - 3 * stdev;
      high = mean + 3 *  stdev;
      count = 0;
      
      for (int i = 0; i < medalsWon.length; i++) {
          if (low < i && high > i)
              count += medalsWon[i];
      }
      
      System.out.println("Three Standard Deviation (Predicted): 99.7% (" + 0.997 * sum2 + ")");
      System.out.println("Three Standard Deviation (Actual): " + count);
      
      System.out.println("\nProblem Three (A): What percentage of medals were won by individuals <= 35 years old?");
      
      System.out.println("Predicted: " + Phi(35, mean, stdev));
      
      count = 0;
      
      for (int i = 0; i < 36; i++) {
          count += medalsWon[i];
      }
      
      double ans = count/(double)sum2;
      
      System.out.println("Actual: " + ans);
      
      System.out.println("\nProblem Three (B): What percentage of medals were won by individuals >= 24 years old?"); 
      
      System.out.println("Predicted: " + (1 - Phi(23, mean, stdev)));
      
      count = 0;
      
      for (int i = 0; i < 23; i++) {
          count += medalsWon[i];
      }
      
      ans = count/(double)sum2;
      
      System.out.println("Actual: " + (1 - ans));
      
   }
   
}