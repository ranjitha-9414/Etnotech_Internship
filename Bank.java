import java.util.*;
public class Bank {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BankAccount account1=new BankAccount(1000);
        System.out.println("Intial Balance: "+ account1.getBalance());
        System.out.println("Enter amount to deposit :");
        
        double deposit=sc.nextDouble();
        account1.deposit(deposit);
        System.out.println("Balance after deposit: "+ account1.getBalance());
        while (true) {
            System.out.println("Do you want to withdrawl amount Y/N:");
            String choice=sc.next().toUpperCase();
            switch (choice) {
                case "Y":
                    System.out.println("Enter amount to withdrawl:");
                    double withdrawl=sc.nextDouble();
                    account1.withdrawl(withdrawl);
                    System.out.println("Balance after withdrawal: "+ account1.getBalance());
                    break;
                case "N":
                    System.out.println("Thank you for using our services.");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid Input");
                
        }
    }
    
       

    }

    
}
