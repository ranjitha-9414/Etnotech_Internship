package Email;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class EmailValidator {

    public static void validate(Object obj) throws Exception {

        Field[] fields = obj.getClass().getDeclaredFields();

        for(Field field : fields){

            if(field.isAnnotationPresent(ValidEmail.class)){

                field.setAccessible(true);

                String value = (String) field.get(obj);

                String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

                if(!Pattern.matches(regex, value)){

                    ValidEmail annotation = field.getAnnotation(ValidEmail.class);

                    throw new Exception(annotation.message());
                }
            }
        }
    }
}