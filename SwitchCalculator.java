import java.math.BigDecimal;
import java.util.Scanner;

public class SwitchCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first number: ");
        String a = sc.nextLine();

        System.out.print("Enter operator (+ or -): ");
        String op = sc.nextLine();

        System.out.print("Enter second number: ");
        String b = sc.nextLine();

        try {
            BigDecimal num1 = new BigDecimal(a);
            BigDecimal num2 = new BigDecimal(b);
            BigDecimal result;

            switch (op) {
                case "+":
                    result = num1.add(num2);
                    break;
                case "-":
                    result = num1.subtract(num2);
                    break;
                default:
                    System.out.println("Invalid operator!");
                    sc.close();
                    return;
            }

            System.out.println("Result: " + result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input!");
        }

        sc.close();
    }
}
