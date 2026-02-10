//write a java program to check the given number is prime or not.
//input=17 and 
import java.util.Scanner;

public class Prime {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        if (n <= 1) {
            System.out.println("This is neither prime nor composite");
        } else {
            boolean isPrime = true;

            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                System.out.println("This is a prime number");
            } else {
                System.out.println("This is not a prime number");
            }
        }

        sc.close();
    }
}

