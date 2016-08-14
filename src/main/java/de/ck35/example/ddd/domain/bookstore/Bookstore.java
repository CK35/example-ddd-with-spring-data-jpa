package de.ck35.example.ddd.domain.bookstore;

import java.util.Optional;

/**
 * An example of a small bookstore which includes some bookshelves which 
 * contain books categorized by a certain topic. Bookshelves can be added
 * and removed. A bookshelf consists of several spaces where the books
 * are stored. Each space contains one type of book.  
 *
 * @author Christian Kaspari
 * @since 1.0.0
 */
public interface Bookstore {

    /**
     * Register a book inside the bookstore.
     * 
     * @param title The title of the book.
     * @param price The price of the book.
     * @return The book which has been registered inside this bookstore.
     */
    Book register(String title, double price);
    
    /**
     * Add a new bookshelf to this bookstore for the given category.
     * 
     * @param category The category of the bookshelf.
     * @return The created bookshelf.
     */
    Bookshelf addBookshelf(String category);
    
    /**
     * Find a bookshelf for the given category.
     * 
     * @param category The category of the bookshelf.
     * @return The found bookshelf or empty if bookstore does not contain such a shelf.
     */
    Optional<Bookshelf> findShelf(String category);
    
}