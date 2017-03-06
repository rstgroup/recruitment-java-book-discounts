package pl.com.rst.books.Discount;

import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookRepository;

public class DiscountUtility {

    public DiscountResult getDiscount(String bookId, float orderPrice, String discountCode, String[] availableDiscounts) {
        if (availableDiscounts.length == 0 || availableDiscounts == null) {
            DiscountResult discountResult = new DiscountResult();
            discountResult.discount = 0;
            return discountResult;
        }

        long id;
        Book book;
        boolean discountAlreadyCalculated = false;
        float totalDiscount = 0;

        for (String discount : availableDiscounts) {
            switch (discount) {
                case "large-order":
                    if (!discountAlreadyCalculated) {
                        id = Long.valueOf(bookId);
                        book = getBookRepository().getBook(id);
                        if (book == null) {
                            DiscountResult discountResult = new DiscountResult();
                            discountResult.setError("No such book");
                            return discountResult;
                        }

                        Discount largeOrderDiscount = book.getLargeOrderDiscount();
                        if (orderPrice > 300) {
                            totalDiscount += book.getPrice() * Utils.percentToFloat(largeOrderDiscount.discount);
                        }
                    }

                    break;
                case "code":
                    id = Long.valueOf(bookId);
                    book = getBookRepository().getBook(id);
                    if (book == null) {
                        DiscountResult discountResult = new DiscountResult();
                        discountResult.setError("No such book");
                        return discountResult;
                    }

                    if (book.isCodeNotUsed(discountCode)) {
                        Discount codeDiscount = book.getCodeDiscount(discountCode);
                        if (codeDiscount.getType() == "percent") {
                            totalDiscount += book.getPrice() * Utils.percentToFloat(codeDiscount.discount);
                        } else if (codeDiscount.getType() == "money"){
                            totalDiscount += codeDiscount.getDiscount();
                        } else {
                            throw new IllegalArgumentException("Not recognized discount type");
                        }


                        book.markDiscountAsUsed(discountCode);
                    }

                    discountAlreadyCalculated = true;
                    break;
            }
        }

        DiscountResult discountResult = new DiscountResult();
        discountResult.discount = (long)totalDiscount;
        return discountResult;
    }


    public BookRepository getBookRepository() {
        return new BookRepository();
    }


    public class DiscountResult {
        public long discount;
        public boolean error;
        public String errorMessage;

        public long getDiscount() {
            return discount;
        }

        public boolean isError() {
            return error;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setError(String errorMessage) {
            error = true;
            this.errorMessage = errorMessage;
        }
    }
}
