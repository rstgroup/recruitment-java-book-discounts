package pl.com.rst.books.Book;

import pl.com.rst.books.Discount.Discount;

import java.util.ArrayList;
import java.util.List;

public class Book {
    public List<String> usedCodes = new ArrayList<>();

    public float getPrice() {
        return 12;
    }

    public Discount getLargeOrderDiscount() {
        Discount discount = new Discount();
        discount.type = "percent";
        discount.discount = 10;
        return discount;
    }

    public Discount getCodeDiscount(String code) {
        Discount discount = new Discount();
        discount.type = "money";
        discount.discount = 25;
        return discount;
    }

    public boolean isCodeNotUsed(String code) {
        return usedCodes.contains(code);
    }

    public void markDiscountAsUsed(String code) {
        usedCodes.add(code);
    }
}
