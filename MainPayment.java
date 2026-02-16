import java.util.Scanner;
public class MainPayment {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Select Payment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. UPI");
        System.out.println("3. Net Banking");
        System.out.println("Enter yourt Choice:");
        int choice=sc.nextInt();
        System.out.println("Enter Amount:");
        double amount=sc.nextDouble();
        if(amount <=0){
            System.out.println("Invalid Amount");
            return;
        }
        Payment payment;
        switch (choice) {
            case 1:
                payment=new CreditCardPayment();
                break;
            case 2:
                payment=new UPIPayment();
                break;
            case 3:
                payment=new NetBankingPayment();
                break;  
        
            default:
                System.out.println("Invalid Choice");
                sc.close();
                return;
        }
        
            payment.pay(amount);
        
        sc.close();
    }
    
}
