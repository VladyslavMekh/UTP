package ADS.processing;

import ADS.validation.NotBlank;
import ADS.validation.NotNull;
import ADS.validation.Range;

import java.lang.reflect.Field;

public class Validator {

    public static void validate(Object obj)
            throws ValidationException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Object value = field.get(obj);

            if (field.isAnnotationPresent(NotNull.class) && value == null)
                throw new ValidationException(field.getName() + " is equals null");

            if (field.isAnnotationPresent(NotBlank.class))
                if (!(value instanceof String) || value.toString().trim().isEmpty())
                    throw new ValidationException(field.getName() + " is empty");

            if (field.isAnnotationPresent(Range.class)) {
                if (!(value instanceof Number))
                    throw new ValidationException(field.getName() + " is of type Range");

                Range range = field.getAnnotation(Range.class);
                long longValue = ((Number) value).longValue();
                if (longValue < range.min() || longValue > range.max())
                    throw new ValidationException(field.getName() + " is out of range");
            }
        }
    }
}
