package Employee_validation;

public class Main {

    public static void main(String[] args) {

        Employee emp = new Employee(
                "Ranjitha",
                "ranjitha@gmail.com",
                "9876543210"
        );

        Validator.validate(emp);
    }
}