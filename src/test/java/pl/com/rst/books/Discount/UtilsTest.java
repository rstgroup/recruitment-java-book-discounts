package pl.com.rst.books.Discount;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void testUtil() throws Exception {
        float result = Utils.percentToFloat(10);
        assertEquals(0.10, result, 1);

        result = Utils.percentToFloat(25);
        assertEquals(0.25, result, 1);

        result = Utils.percentToFloat(99);
        assertEquals(0.99, result, 1);

        result = Utils.percentToFloat(1);
        assertEquals(0.01, result, 1);

        result = Utils.percentToFloat(5);
        assertEquals(0.05, result, 1);
    }
}