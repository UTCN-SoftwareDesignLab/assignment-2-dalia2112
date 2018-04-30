package bookstore.model.validation;

import bookstore.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookValidator {

    private static final int MIN_PRICE = 0;
    private static final int MIN_QUANTITY = 0;
    private List<String> GENRE = Arrays.asList("SF", "Romance", "Drama", "Comedy", "Horror");


    private final List<String> errors;
    private Book book;

    public BookValidator(Book book) {
        this.errors = new ArrayList<>();
        this.book = book;
    }

    public void validate() {
        validateGenre(book.getGenre());
        validateQuantity(book.getQuantity());
        validatePrice(book.getPrice());
    }

    public void validateQuantity(int quantity) {

        if (quantity < MIN_QUANTITY)
            errors.add("You add at least a book!");

    }

    public void validatePrice(int price) {
        if (price < MIN_PRICE)
            errors.add("Price must be positive!");

    }

    public void validateGenre(String genre) {
        if (!GENRE.contains(genre))
            errors.add("This genre does not exist");
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getFormatedErrors() {
        String err = "";
        for (String error : errors)
            err += error + "\n";
        return err;
    }

    private boolean validateNr(String nr) {
        return nr.chars().allMatch(Character::isDigit);
    }
}
