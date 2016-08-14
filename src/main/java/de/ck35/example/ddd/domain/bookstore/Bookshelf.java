package de.ck35.example.ddd.domain.bookstore;

import java.util.Optional;

import de.ck35.example.ddd.domain.Entity;

/**
 * A place where books can be stored and retrieved. Each shelf belongs to one category and
 * contains many books. Books can be put into a bookshelf space by defining the
 * quantity of books which should be stored inside a space.
 *
 * @author Christian Kaspari
 * @since 1.0.0
 */
public interface Bookshelf extends Entity  {

    /**
     * @return The category for this bookshelf.
     */
    String getCategory();
    
    /**
     * Search for a book with the given name inside this bookshelf.
     * 
     * @param title The title of the book which should be found.
     * @return A book or empty result if not found.
     */
    Optional<Space> findBook(String title);
    
    /**
     * Store a book inside this bookshelf by finding a space for
     * this book and setting the given quantity. A new space will
     * be found if book has not been stored before.
     * 
     * @param book The book which should be stored inside this bookshelf.
     * @param quantity The amount of books which should be stored.
     * @return The space where the book(s) have been stored.
     */
    Space store(Book book, int quantity);
    
    /**
     * A space inside the bookshelf where books are stored.
     *
     * @author Christian Kaspari
     * @since 1.0.0
     */
    public interface Space extends Entity {
        
        /**
         * @return The stored book.
         */
        Book getBook();
        
        /**
         * @return The amount of books which are currently stored inside this space.
         */
        int getQuantity();
        
        /**
         * Removes some books or one book from this space inside the bookshelf.
         * 
         * @param amount The amount of books to take from this space.
         */
        void take(int amount);
    }
    
}