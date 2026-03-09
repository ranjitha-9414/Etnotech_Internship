package Email;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface ValidEmail {
    String message() default "Invalid Email Format";
}