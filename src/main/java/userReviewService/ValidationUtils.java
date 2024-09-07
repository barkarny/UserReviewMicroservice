package userReviewService;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ValidationUtils {



    public static boolean isEmailFormat(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String e = email.replace(" ", "");
        String numeric = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return e.matches(numeric);
    }

    public static String toStringdateFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        return (formattedDate);

    }

    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateString.replace(" ", ""));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
    public static Date dateToStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            // Parse the formatted string back to Date
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.parse(dateStr);
    }
    public static boolean isValidShape(String name) {
        for (Shape shape : Shape.values()) {
            if (shape.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Function to convert a Shape enum constant to its string representation
    public static String shapeToString(Shape shape) {
        return shape.name();
    }
    public static Shape stringToShape(String name) {

        for (Shape shape : Shape.values()) {
            if (shape.name().equalsIgnoreCase(name)) {
                return shape;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
    public static boolean isDouble(String id) {
        try {
            Double.parseDouble(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static LocalDate fromStringToLocalDatte(String dateString) {
        LocalDate parsedDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the String into LocalDate object
        //when we call this function we guess string at format

        parsedDate = LocalDate.parse(dateString, formatter);

        return parsedDate;
    }
}
