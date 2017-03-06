package pl.com.rst.books.Discount;

import org.junit.Test;
import org.mockito.Mockito;
import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookRepository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

public class DiscountUtilityTest {
    @Test
    public void test() throws Exception {
        Book book = Mockito.mock(Book.class);
        Mockito.when(book.getPrice()).thenReturn(100f);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        DiscountUtility discountUtility = Mockito.spy(DiscountUtility.class);
        Mockito.when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        Mockito.when(bookRepository.getBook(anyLong())).thenReturn(book);

        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 10, "Abc", new String[]{});
        assertEquals(0, result.discount);


        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        Mockito.when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        Mockito.when(book.getLargeOrderDiscount()).thenReturn(value);
        Mockito.when(book.isCodeNotUsed(any())).thenReturn(true);
        result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code", "large-order"});
        assertEquals(10, result.discount);

        Mockito.doAnswer(invocation -> {
            Mockito.when(book.isCodeNotUsed(any())).thenReturn(false);
            return null;
        }).when(book).markDiscountAsUsed(any());
        result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code"});
        assertEquals(10, result.discount);
        result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code"});
        assertEquals(0, result.discount);


        result = discountUtility.getDiscount("5", 3000, "Abc", new String[]{"large-order"});
        assertEquals(20, result.discount);

        result = discountUtility.getDiscount("5", 100, "Abc", new String[]{"large-order"});
        assertEquals(0, result.discount);

        Mockito.when(bookRepository.getBook(anyLong())).thenReturn(null);
        result = discountUtility.getDiscount("5", 100, "Abc", new String[]{"large-order"});
        assertTrue(result.error);
    }
}