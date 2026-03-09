package Email;

public class Main {

    public static void main(String[] args) {

        User user = new User("ranjitha.gmail.com");

        try{

            EmailValidator.validate(user);

            System.out.println("Email is valid");

        }catch(Exception e){

            System.out.println(e.getMessage());
        }
    }
}
