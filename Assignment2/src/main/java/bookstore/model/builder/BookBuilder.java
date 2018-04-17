package bookstore.model.builder;

import bookstore.model.Book;

public class BookBuilder {

    private Book book;

    public BookBuilder() {
        book = new Book();
    }

    public BookBuilder setId(Long id) {
        book.setId(id);
        return this;
    }

    public BookBuilder setAuthor(String author) {
        book.setAuthor(author);
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        book.setIsbn(isbn);
        return this;
    }

    public BookBuilder setName(String name) {
        book.setName(name);
        return this;
    }

    public Book build() {
        return book;
    }
}
