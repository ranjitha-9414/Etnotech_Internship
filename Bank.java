public class Bank {
    public static void main(String[] args) {
        BankAccount account1=new BankAccount(1000);
        System.out.println("Intial Balance: "+ account1.getBalance());
        account1.deposit(2000);
        System.out.println("Balance after deposit: "+ account1.getBalance());
        account1.withdrawl(500);
        System.out.println("Balance after withdrawal: "+ account1.getBalance());
    }
}
