package Employee_validation;

public class Employee {

    @Validate(
        pattern = "^[A-Za-z ]+$",
        message = "Name must contain only letters"
    )
    private String name;

    @Validate(
        pattern = "^[A-Za-z0-9+_.-]+@(.+)$",
        message = "Invalid email format"
    )
    private String email;

    @Validate(
        pattern = "^[0-9]{10}$",
        message = "Phone must be 10 digits"
    )
    private String phone;

    public Employee(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
