
import java.util.UUID;//Universally Unique Identifier
public class CreditCardPayment implements Payment{
    @Override
    public void pay(double amount){
        String transactionId=UUID.randomUUID().toString();
        System.out.println("Payment Successful!");
        System.out.println("Method :Credit Card");
        System.out.println("Amount:"+amount);
        System.out.println("Transaction ID:" + transactionId);
    }
    
}
