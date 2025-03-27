package mg.itu.prom16.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ValueParser {

    public static Object parseStringValue(String value, Class<?> type) {
        if(value == null || value.isEmpty()) return null;
//        if(v)

        String paramType = type.getSimpleName().toLowerCase();

        System.out.println(value);
        switch (paramType) {
            case "localdate" -> {
                return LocalDate.parse(value);
            }
            case "localtime" -> {
                return LocalTime.parse(value);
            }
            case "localdatetime" -> {
                return LocalDateTime.parse(value);
            }
            case "int", "integer" -> {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException | NullPointerException e) {
                    return 0;
                }
            }
            case "double" -> {
                try {
                    return Double.parseDouble(value);
                } catch (NullPointerException | NumberFormatException e) {
                    return 0;
                }
            }
            case "string" -> {
                return value;
            }
//            case "list" -> {
//                value
//            }
            default -> {
                return type.cast(value);
            }
        }
    }

//    public static Object parseStringValues(String[] values, Class<?> type) {
//        if(!type.isArray()) return parseStringValue(values[0], type);

//        ((Class<List>)type).getClass().
//    }
}
