//Design a bannking system using object oriented programming concepts. deposit and withdrawl
public class BankAccount {
    private double balance;
    BankAccount(double balance){
        this.balance=balance;
    }
    public double getBalance(){
        return balance;
    }
    public void  deposit(double amount){
        if(amount >0){
            balance+=amount;
        }
        else{
            System.out.println("Invalid Amount");
        }
    }
    public void withdrawl(double amount){
        if(amount >0 && amount <= balance){
            balance-=amount;
        }
        else{
            System.out.println("Invalid Amount or Insufficient Balance");
        }
    }
}
