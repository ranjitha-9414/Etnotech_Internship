package Email;

public class User {

    @ValidEmail
    private String email;

    public User(String email){
        this.email = email;
    }
}