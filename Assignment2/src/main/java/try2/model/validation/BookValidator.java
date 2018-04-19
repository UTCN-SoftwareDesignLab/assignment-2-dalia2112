package try2.model.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookValidator {

    private static final int MIN_PRICE = 0;
    private static final int MIN_QUANTITY = 1;
    private List<String> GENRE = Arrays.asList("SF", "Romance", "Drama", "Comedy", "Horror");


    private final List<String> errors;

    public BookValidator() {
        this.errors = new ArrayList<>();
    }

    public void validateQuantity(String quantity) {
        if (!quantity.chars().allMatch(Character::isDigit)) {
            errors.add("Quantity must be digits!");
            return;
        }
        if (Integer.parseInt(quantity) < MIN_QUANTITY)
            errors.add("You add at least a book!");

    }

    public void validatePrice(String price) {
        if (!price.chars().allMatch(Character::isDigit)) {
            errors.add("Price must be digits!");
            return;
        }
        if (Integer.parseInt(price) < MIN_PRICE)
            errors.add("Price must be positive!");

    }

    public void validateGenre(String genre) {
        if(!GENRE.contains(genre))
            errors.add("This genre does not exist");
    }

    public void validateOrder(int quantity){
        if(quantity<0)
            errors.add("Not enough stock!");
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
}
