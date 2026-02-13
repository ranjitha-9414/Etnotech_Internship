import java.util.UUID;
public class UPIPayment implements Payment{
    @Override
    public void pay(double amount){
        String transactionId=UUID.randomUUID().toString();
        System.out.println("Payment Successful!");
        System.out.println("Method :UPI");
        System.out.println("Amount:"+amount);
        System.out.println("Transaction ID:" + transactionId);
    }
}
