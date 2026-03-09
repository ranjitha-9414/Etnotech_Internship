package Employee_validation;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {

    public static void validate(Object obj) {

        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {

            if (field.isAnnotationPresent(Validate.class)) {

                Validate annotation = field.getAnnotation(Validate.class);

                field.setAccessible(true);

                try {
                    String value = (String) field.get(obj);

                    boolean match = Pattern.matches(annotation.pattern(), value);

                    if (!match) {
                        System.out.println(field.getName() + " : " + annotation.message());
                    } else {
                        System.out.println(field.getName() + " : Valid");
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
