package pl.com.rst.books.Discount;

public class Utils {
    public static float percentToFloat(int percent) {
        return Float.valueOf(String.format("0.%d", percent));
    }
}
